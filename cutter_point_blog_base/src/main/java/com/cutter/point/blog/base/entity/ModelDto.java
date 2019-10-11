package com.cutter.point.blog.base.entity;

import com.cutter.point.blog.base.annotation.Column;
import com.cutter.point.blog.utils.NameChangeUtil;
import com.cutter.point.blog.utils.SpringContextUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

/**
 * @ProjectName: 湖北移动智慧装维支撑系统
 * @Package: com.ztesoft.interfaces.supplyChain.dto
 * @ClassName: ModelDto
 * @Author: xiaof
 * @Description: ${description}
 * @Date: 2019/1/30 15:11
 * @Version: 1.0
 */
public abstract class ModelDto {

    private final static Logger logger = LoggerFactory.getLogger(ModelDto.class);

    private String sequenceName = "";

    private static final String[] types1 = { "int", "java.lang.String", "boolean", "char",
            "float", "double", "long", "short", "byte", "java.util.Date", "java.math.BigDecimal" };
    private static final String[] types2 = { "Integer", "java.lang.String", "java.lang.Boolean",
            "java.lang.Character", "java.lang.Float", "java.lang.Double",
            "java.lang.Long", "java.lang.Short", "java.lang.Byte", "java.util.Date", "java.math.BigDecimal" };

    private JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextUtil.getBean("jdbcTemplate");

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = (NamedParameterJdbcTemplate) SpringContextUtil.getBean("namedParameterJdbcTemplate");

//    private JdbcTemplate jdbcTemplate;
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(JdbcUtil.getZhcxDataSource());
    /**
     * 新增
     */
    public void add() throws Exception {
        //插入当前对象
        //使用反射机制获取当前类的所有字段
        Field[] fields = this.getClass().getDeclaredFields();
        int fieldSize = fields.length;
        //获取要插入的表名,去掉末尾model这5个字符
        int nameLength = this.getClass().getSimpleName().length();
        String tableName = this.getClass().getSimpleName();
        tableName = Character.toLowerCase(tableName.charAt(0)) + tableName.substring(1);
        //转下划线
        tableName = NameChangeUtil.camelToUnderLine(tableName);
        //匹配基础类型
        Column column = this.getClass().getAnnotation(Column.class);
        if(column != null) {
            tableName = column.name();
        }

        //组装sql
        StringBuffer sql = new StringBuffer("insert into " + tableName + " values(");
        for(int i = 0; i < fieldSize; ++i) {
            Field field = fields[i];
            Column fieldColumn = field.getAnnotation(Column.class);
            if(fieldColumn == null) {
                sql.append(" :" + fields[i].getName() + ",");
            } else {
                String fieldName = fieldColumn.name() == null ? field.getName() : fieldColumn.name();
                sql.append(" :" + fieldName + ",");
            }
        }
        //去除最后的逗号
        sql.deleteCharAt(sql.length() - 1).append(")");

        //进行数据插入
        Map paramMap = new HashMap();
        //设置字段
        for(int i = 0; i < fieldSize; ++i) {
            //注意sql中变量参数是从1开始的
            //在反射对象中设置 accessible 标志允许具有足够特权的复杂应用程序
            //（比如 Java Object Serialization 或其他持久性机制）
            //以某种通常禁止使用的方式来操作对象
            fields[i].setAccessible(true);
            Field field = fields[i];
            Column fieldColumn = field.getAnnotation(Column.class);
            for(int j = 0; j < types1.length; ++j) {
                if(fieldColumn == null) {
                    if(fields[i].getType().getName().equalsIgnoreCase(types1[j])
                            || fields[i].getType().getName().equalsIgnoreCase(types2[j])) {
                        //如果匹配到以上几个类型，获取数据不为空
                        if(fields[i].get(this) != null
                                && !"".equals(fields[i].get(this))
                                && !"null".equals(fields[i].get(this))) {
//							System.out.println(fields[i].getName() + "》值：" + fields[i].get(this));
                            if(types1[j].equals("java.util.Date")) {
                                Date date = (Date) fields[i].get(this);
                                paramMap.put(fields[i].getName(), new java.sql.Date(date.getTime()));
                            } else {
                                paramMap.put(fields[i].getName(), fields[i].get(this));
                            }
                        } else {
                            //如果是空
                            paramMap.put(fields[i].getName(), null);
                        }
                    }
                } else {
                    //如果不为空，根据注解填写对应的信息
                    String fieldName = fieldColumn.name() == null ? field.getName() : fieldColumn.name();
                    if(fields[i].getType().getName().equalsIgnoreCase(types1[j])
                            || fields[i].getType().getName().equalsIgnoreCase(types2[j])) {
                        //如果匹配到以上几个类型，获取数据不为空
                        if(fields[i].get(this) != null
                                && !"".equals(fields[i].get(this))
                                && !"null".equals(fields[i].get(this))) {
//							System.out.println(fields[i].getName() + "》值：" + fields[i].get(this));
                            if(types1[j].equals("java.util.Date")) {
                                Date date = (Date) fields[i].get(this);
                                paramMap.put(fieldName, new java.sql.Date(date.getTime()));
                            } else {
                                paramMap.put(fieldName, fields[i].get(this));
                            }
                        } else {
                            //如果是空
                            //判断主键是否为空，如果为空，那么要根据对应的值查序列
                            if(fieldColumn.isPrimary()) {
                                //如果是主键，并且为空
                                String seqName = "seq_" + tableName;
                                String seqSql = "SELECT " + seqName + ".Nextval as seqValue FROM dual ";
                                Map retValue = namedParameterJdbcTemplate.queryForMap(seqSql, new HashMap<>());
                                paramMap.put(fieldName, MapUtils.getString(retValue, "seqValue"));
                            } else {

                                paramMap.put(fieldName, null);
                            }
                        }
                    }
                }
            }
        }

        //执行sql，提交事务
        namedParameterJdbcTemplate.update(sql.toString(), paramMap);
    }

    private String getTableName() {
        int nameLength = this.getClass().getSimpleName().length();
        String tableName = this.getClass().getSimpleName().substring(0, nameLength - 3);
        tableName = Character.toLowerCase(tableName.charAt(0)) + tableName.substring(1);
        //转下划线
        tableName = NameChangeUtil.camelToUnderLine(tableName);

        return tableName;
    }

    private void setParams(Field field, List paramsUpdate) throws IllegalArgumentException, IllegalAccessException, SQLException {
        for(int j = 0; j < types1.length; ++j) {
            if(field.getType().getName().equalsIgnoreCase(types1[j])
                    || field.getType().getName().equalsIgnoreCase(types2[j])) {
                //如果匹配到以上几个类型，获取数据不为空
                if(field.get(this) != null
                        && !"".equals(field.get(this))
                        && !"null".equals(field.get(this))) {
//					System.out.println(field.getName() + "》值：" + field.get(this));
                    if(types1[j].equals("java.util.Date")) {
                        Date date = (Date) field.get(this);
                        paramsUpdate.add(new java.sql.Date(date.getTime()));
                    } else {
                        paramsUpdate.add(field.get(this));
                    }
                } else {
                    //如果是空
                    paramsUpdate.add(null);
                }
            }
        }
    }

    private void setParams(Field field, Map paramMap) throws IllegalArgumentException, IllegalAccessException, SQLException {
        for(int j = 0; j < types1.length; ++j) {
            if(field.getType().getName().equalsIgnoreCase(types1[j])
                    || field.getType().getName().equalsIgnoreCase(types2[j])) {
                //如果匹配到以上几个类型，获取数据不为空
                if(field.get(this) != null
                        && !"".equals(field.get(this))
                        && !"null".equals(field.get(this))) {
//					System.out.println(field.getName() + "》值：" + field.get(this));
                    if(types1[j].equals("java.util.Date")) {
                        Date date = (Date) field.get(this);
                        paramMap.put(field.getName(), new java.sql.Date(date.getTime()));
                    } else {
                        paramMap.put(field.getName(), field.get(this));
                    }
                } else {
                    //如果是空
                    paramMap.put(field.getName(), null);
                }
            }
        }
    }

    private void setParamsByColumn(Field field, List params, List paramsUpdate) throws IllegalArgumentException, IllegalAccessException, SQLException {
        //1、字段不为空
        if(field.get(this) == null
                && "".equals(field.get(this))
                && "null".equals(field.get(this))) {
            return;
        }
        //2、判断是否是主键
        Column column = field.getAnnotation(Column.class);
        //3、其余字段按照一般情况进行加载
        if(column.isPrimary()) {
            //如果是主键
            params.add(field.get(this));
        } else {
            paramsUpdate.add(field.get(this));
        }
    }

    private void setParamsByColumn(Field field, Map params, Map paramsUpdate) throws IllegalArgumentException, IllegalAccessException, SQLException {
        //1、字段不为空
        if(field.get(this) == null
                && "".equals(field.get(this))
                && "null".equals(field.get(this))) {
            return;
        }
        //2、判断是否是主键
        Column column = field.getAnnotation(Column.class);
        //3、其余字段按照一般情况进行加载
        if(column.isPrimary()) {
            //如果是主键
            params.put(field.getName(), field.get(this));
        } else {
            paramsUpdate.put(field.getName(), field.get(this));
        }
    }

    /**
     * 根据id更新model
     */
    public void update() {
        try {
            //1、组装update基础语句
            Map paramMap = new HashMap();
            Map paramsUpdateMap = new HashMap();
            StringBuffer sql = new StringBuffer("update ");
            StringBuffer sqlw = new StringBuffer(" where 1 = 1 ");

            //获取当前对象表名
            String tableName = this.getTableName();
            sql.append(tableName).append(" set ");
            //2、根据反射获取所有字段
            Field[] fields = this.getClass().getDeclaredFields();
            //3、遍历所有根据注解判断是否是主键，保留主键信息，留到最后作为参数组装
            //4、其余字段，驼峰转下划线进行组装
            for(int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                field.setAccessible(true); //解放操作权限
                Column column = field.getAnnotation(Column.class);
                if(column == null) {
                    //如果为空，那么就按照原来的名字组装
                    if(field.get(this) != null
                            && !"".equals(field.get(this))
                            && !"null".equals(field.get(this))) {
                        sql.append(NameChangeUtil.camelToUnderLine(field.getName())).append("=:" + field.getName() + ",");
                        this.setParams(field, paramsUpdateMap);
                    }

                } else {
                    //如果不为空，根据注解填写对应的信息
                    if(column.canChange()) {
                        if(column.isPrimary()) {
                            sqlw.append(" and ").append(column.name()).append(" = :" + field.getName() + " ");
                        } else {
                            sql.append(NameChangeUtil.camelToUnderLine(field.getName())).append("=:" + field.getName() + ",");
                        }
                        this.setParamsByColumn(field, paramMap, paramsUpdateMap);
                    }
                }
            }

            //5、执行连接数据库操作
            //去除拼接set的最后一个逗号
            sql.deleteCharAt(sql.length() - 1);
            //设置参数,先是待更新对象，然后是条件参数
            List paramList = new ArrayList();
            Map params = new HashMap();
            params.putAll(paramsUpdateMap);
            params.putAll(paramMap);

            //执行操作
            logger.info("update：" + tableName + " sql============" + sql.toString() + sqlw.toString() + "=================" + paramList);
//            jdbcTemplate.update(sql.toString() + sqlw.toString(), paramList);
            namedParameterJdbcTemplate.update(sql.toString() + sqlw.toString(), params);
        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 对这个model数据进行查询,并对自己赋值
     */
    public List<ModelDto> qry() {

        //1、组装select基础语句
        List<ModelDto> res = null;
        List paramList = new ArrayList();
        Map params = new HashMap();
        StringBuffer sql = new StringBuffer("select * ");

        try {
            //获取当前对象表名
            String tableName = this.getTableName();
            sql.append(" FROM " + tableName).append(" where 1 = 1 ");
            //2、根据反射获取所有字段
            Field[] fields = this.getClass().getDeclaredFields();
            //3、遍历所有根据注解判断是否是主键，保留主键信息，留到最后作为参数组装
            //4、其余字段，驼峰转下划线进行组装
            for(int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                field.setAccessible(true); //解放操作权限
                Column column = field.getAnnotation(Column.class);
                if(column == null) {
                    //如果为空，那么就按照原来的名字组装
                    if(field.get(this) != null
                            && !"".equals(field.get(this))
                            && !"null".equals(field.get(this))) {
//                        sql.append( "and " + NameChangeUtil.camelToUnderLine(field.getName())).append("=:" + field.getName() + ",");
                        sql.append( "and " + NameChangeUtil.camelToUnderLine(field.getName())).append(" = ? ");
                        this.setParams(field, paramList);
                    }

                } else {
                    //如果不为空，根据注解填写对应的信息
                    if(column.canChange()) {
                        if(column.isPrimary()) {
                            sql.append(" and ").append(column.name()).append(" = ? ");
                        } else {
                            sql.append(" and ").append(NameChangeUtil.camelToUnderLine(field.getName())).append(" = ? ");
                        }
                        this.setParamsByColumn(field, paramList, paramList);
                    }
                }
            }

            //5、执行连接数据库操作
            //去除拼接set的最后一个逗号
            sql.deleteCharAt(sql.length() - 1);
            //设置参数,先是待更新对象，然后是条件参数
            //获取结果
            res = jdbcTemplate.query(sql.toString(), paramList.toArray(), new BeanPropertyRowMapper(this.getClass()));

        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return res;
    }

}
