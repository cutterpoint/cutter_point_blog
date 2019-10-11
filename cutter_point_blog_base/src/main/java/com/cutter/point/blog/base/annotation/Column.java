package com.cutter.point.blog.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解model对象的字段属性
 * @author xiaof
 *
 */
@Retention(RetentionPolicy.RUNTIME) //记录注解要保留的时间，运行的时候就保留
@Target(value=ElementType.FIELD) //注解作用域限制，字段声明（包括枚举常量）
public @interface Column {

	/**
	 * 字段名称
	 * @return
	 */
	String name();
	/**
	 * 字段类型
	 * @return
	 */
	Class<?> type();
	/**
	 * 字段长度
	 * @return
	 */
	int len() default 11;
	/**
	 * 是否是主键
	 * @return
	 */
	boolean isPrimary() default false;
	/**
	 * 是否可为空
	 * @return
	 */
	boolean isNull() default true;
	/**
	 * 是否自增长
	 * @return
	 */
	boolean isAutoIncrement() default false;
	/**
	 * 是否可重复
	 * @return
	 */
	boolean isUnique() default true;

	/**
	 *
	 * @program: com.ztesoft.interfaces.supplyChain.annotation.Column
	 * @description: 是否可改动数据
	 * @auther: xiaof
	 * @date: 2019/1/30 15:48
	 */
	boolean canChange() default true;
}
