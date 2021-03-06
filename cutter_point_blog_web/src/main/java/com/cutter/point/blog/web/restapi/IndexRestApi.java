package com.cutter.point.blog.web.restapi;


import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.cutter.point.blog.web.constrant.BlogWebYmlConstrant;
import com.cutter.point.blog.web.util.UrlUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cutter.point.blog.utils.IpUtils;
import com.cutter.point.blog.utils.JsonUtils;
import com.cutter.point.blog.utils.ResultUtil;
import com.cutter.point.blog.utils.StringUtils;
import com.cutter.point.blog.web.feign.PictureFeignClient;
import com.cutter.point.blog.web.global.SQLConf;
import com.cutter.point.blog.web.global.SysConf;
import com.cutter.point.blog.xo.entity.Blog;
import com.cutter.point.blog.xo.entity.BlogSort;
import com.cutter.point.blog.xo.entity.Link;
import com.cutter.point.blog.xo.entity.Tag;
import com.cutter.point.blog.xo.entity.WebConfig;
import com.cutter.point.blog.xo.service.BlogService;
import com.cutter.point.blog.xo.service.BlogSortService;
import com.cutter.point.blog.xo.service.LinkService;
import com.cutter.point.blog.xo.service.TagService;
import com.cutter.point.blog.xo.service.WebConfigService;
import com.cutter.point.blog.xo.service.WebVisitService;
import com.cutter.point.blog.base.enums.EBehavior;
import com.cutter.point.blog.base.enums.ELevel;
import com.cutter.point.blog.base.enums.EPublish;
import com.cutter.point.blog.base.enums.EStatus;
import com.cutter.point.blog.base.global.BaseSQLConf;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * <p>
 * 首页 RestApi
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-04
 */
@RestController
@RequestMapping("/index")
@Api(value="首页RestApi",tags={"IndexRestApi"})
public class IndexRestApi {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	TagService tagService;
	
	@Autowired
	BlogSortService blogSortService;
	
	@Autowired
	LinkService linkService;
		
	@Autowired
	private PictureFeignClient pictureFeignClient;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private WebConfigService webConfigService;
	
	@Autowired
	private WebVisitService webVisitService;
	
	@Value(value="${BLOG.HOT_COUNT}")
	private Integer BLOG_HOT_COUNT;
	
	@Value(value="${BLOG.HOT_TAG_COUNT}")
	private Integer BLOG_HOT_TAG_COUNT;
	
	@Value(value="${BLOG.NEW_COUNT}")
	private Integer BLOG_NEW_COUNT;
	
	@Value(value="${BLOG.FIRST_COUNT}")
	private Integer BLOG_FIRST_COUNT;
	
	@Value(value="${BLOG.SECOND_COUNT}")
	private Integer BLOG_SECOND_COUNT;
	
	@Value(value="${BLOG.THIRD_COUNT}")
	private Integer BLOG_THIRD_COUNT;
	
	@Value(value="${BLOG.FOURTH_COUNT}")
	private Integer BLOG_FOURTH_COUNT;
	
	private static Logger log = LogManager.getLogger(IndexRestApi.class);

    @ApiOperation(value="测试api", notes="测试api")
    @RequestMapping("/FeignClienthello")
    @ResponseBody
    public String FeignClienthello() {
        return this.pictureFeignClient.hello();
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value="通过推荐等级获取博客列表", notes="通过推荐等级获取博客列表")
	@GetMapping("/getBlogByLevel")
	public String getBlogByLevel (HttpServletRequest request,
			@ApiParam(name = "level", value = "推荐等级",required = false) @RequestParam(name = "level", required = false, defaultValue = "0") Integer level,
			@ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage) {
		
		//从Redis中获取内容
		String jsonResult = stringRedisTemplate.opsForValue().get("BOLG_LEVEL:" + level);
		
		//判断redis中是否有文章
		if(StringUtils.isNotEmpty(jsonResult)) {
			List list = JsonUtils.jsonArrayToArrayList(jsonResult);
			IPage pageList = new Page();
			pageList.setRecords(list);
			return ResultUtil.result(SysConf.SUCCESS, pageList);
		}		
		Page<Blog> page = new Page<>();
		page.setCurrent(currentPage);
		switch(level) {
			case ELevel.NORMAL: { page.setSize(BLOG_NEW_COUNT);} break;
			case ELevel.FIRST: { page.setSize(BLOG_FIRST_COUNT);} break;
			case ELevel.SECOND: { page.setSize(BLOG_SECOND_COUNT);} break;
			case ELevel.THIRD: { page.setSize(BLOG_THIRD_COUNT);} break;
			case ELevel.FOURTH: { page.setSize(BLOG_FOURTH_COUNT);} break;
		}
		IPage<Blog> pageList = blogService.getBlogPageByLevel(page, level);
		List<Blog> list = pageList.getRecords();		

		list = setBlog(list);
		
		pageList.setRecords(list);

	    //将从数据库查询的数据缓存到redis中		
		stringRedisTemplate.opsForValue().set("BOLG_LEVEL:" + level, JsonUtils.objectToJson(list).toString());
		
		return ResultUtil.result(SysConf.SUCCESS, pageList);
	}
	
	@ApiOperation(value="获取首页排行博客", notes="获取首页排行博客")
	@GetMapping("/getHotBlog")
	public String getHotBlog (HttpServletRequest request) {
		
		QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
		Page<Blog> page = new Page<>();
		page.setCurrent(0);
		page.setSize(BLOG_HOT_COUNT);
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
		queryWrapper.eq(SQLConf.IS_PUBLISH, EPublish.PUBLISH);
		queryWrapper.orderByDesc(SQLConf.CLICK_COUNT);
		
		//因为首页并不需要显示内容，所以需要排除掉内容字段		
//		queryWrapper.excludeColumns(Blog.class, SysConf.CONTENT);
		queryWrapper.select(Blog.class, i-> !i.getProperty().equals("content"));
		
		IPage<Blog> pageList = blogService.page(page, queryWrapper);
		List<Blog> list = pageList.getRecords();
		
		list = setBlog(list);
		
		log.info("返回结果");
		pageList.setRecords(list);
		return ResultUtil.result(SysConf.SUCCESS, pageList);
	}

	@ApiOperation(value="获取首页最新的博客", notes="获取首页最新的博客")
	@GetMapping("/getNewBlog")
	public String getNewBlog (HttpServletRequest request,
			@ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
			@ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {
		
		QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
		Page<Blog> page = new Page<>();
		page.setCurrent(currentPage);
		page.setSize(pageSize);
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
		queryWrapper.eq(BaseSQLConf.IS_PUBLISH, EPublish.PUBLISH);
		queryWrapper.orderByDesc(SQLConf.CREATE_TIME);

		//因为首页并不需要显示内容，所以需要排除掉内容字段
		queryWrapper.select(Blog.class, i-> !i.getProperty().equals("content"));
		
		IPage<Blog> pageList = blogService.page(page, queryWrapper);
		List<Blog> list = pageList.getRecords();
		
		if(list.size() <= 0) {
			return ResultUtil.result(SysConf.SUCCESS, pageList);
		}
		
		list = setBlog(list);
		
		log.info("返回结果");
		pageList.setRecords(list);
		return ResultUtil.result(SysConf.SUCCESS, pageList);
	}
	
	@ApiOperation(value="按时间戳获取博客", notes="按时间戳获取博客")
	@GetMapping("/getBlogByTime")
	public String getBlogByTime (HttpServletRequest request,
			@ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
			@ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {
		
		QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
		Page<Blog> page = new Page<>();
		page.setCurrent(currentPage);
		page.setSize(pageSize);
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
		queryWrapper.eq(BaseSQLConf.IS_PUBLISH, EPublish.PUBLISH);
		queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
		
		//因为首页并不需要显示内容，所以需要排除掉内容字段
		queryWrapper.select(Blog.class, i-> !i.getProperty().equals("content"));
		
		IPage<Blog> pageList = blogService.page(page, queryWrapper);
		List<Blog> list = pageList.getRecords();
		
		list = setBlog(list);
		
		log.info("返回结果");
		pageList.setRecords(list);
		return ResultUtil.result(SysConf.SUCCESS, pageList);
	}
	
	@ApiOperation(value="获取最热标签", notes="获取最热标签")
	@GetMapping("/getHotTag")
	public String getHotTag (HttpServletRequest request) {
		
		QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
		Page<Tag> page = new Page<>();
		page.setCurrent(0);
		page.setSize(BLOG_HOT_TAG_COUNT);
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
		queryWrapper.orderByDesc(SQLConf.SORT);
		queryWrapper.orderByDesc(SQLConf.CLICK_COUNT);
		IPage<Tag> pageList = tagService.page(page, queryWrapper);
		log.info("返回结果");
		return ResultUtil.result(SysConf.SUCCESS, pageList);
	}
	
	@ApiOperation(value="获取友情链接", notes="获取友情链接")
	@GetMapping("/getLink")
	public String getLink (HttpServletRequest request,
			@ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
			@ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {
		
		QueryWrapper<Link> queryWrapper = new QueryWrapper<Link>();
		Page<Link> page = new Page<>();
		page.setCurrent(currentPage);
		page.setSize(pageSize);
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
		queryWrapper.orderByDesc(SQLConf.SORT);
		IPage<Link> pageList = linkService.page(page, queryWrapper);
		log.info("返回结果");
		return ResultUtil.result(SysConf.SUCCESS, pageList);
	}
	
	@ApiOperation(value="友情链接点击数", notes="获取友情链接")
	@GetMapping("/addLinkCount")
	public String addLinkCount (HttpServletRequest request,
			@ApiParam(name = "uid", value = "友情链接UID",required = false) @RequestParam(name = "uid", required = false) String uid) {
		
		if(StringUtils.isEmpty(uid)) {
			return ResultUtil.result(SysConf.ERROR, "数据错误");
		}
		Link link = linkService.getById(uid);
		if(link != null) {
			
			//增加记录（可以考虑使用AOP）
	        webVisitService.addWebVisit(null, IpUtils.getIpAddr(request), EBehavior.FRIENDSHIP_LINK.getBehavior(), uid, null);
	        
			int count = link.getClickCount() + 1;
			link.setClickCount(count);
			link.updateById();	
		}  else {
			return ResultUtil.result(SysConf.ERROR, "数据错误");
		}
				
		return ResultUtil.result(SysConf.SUCCESS, "更新点击数成功");
	}
	
	
	@ApiOperation(value="获取网站配置", notes="获取友情链接")
	@GetMapping("/getWebConfig")
	public String getWebConfig (HttpServletRequest request) {
		
		QueryWrapper<WebConfig> queryWrapper = new QueryWrapper<>();		
		queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
		WebConfig webConfig = webConfigService.getOne(queryWrapper);
		
		if(StringUtils.isNotEmpty(webConfig.getLogo())) {
			String pictureList = BlogWebYmlConstrant.imageUrl + "?uid=" + webConfig.getLogo();
			webConfig.setPhotoList(Arrays.asList(pictureList));
		}
		
		//获取支付宝收款二维码
		if(webConfig != null && StringUtils.isNotEmpty(webConfig.getAliPay())) {
			String pictureList = UrlUtil.getImageUrl(webConfig.getAliPay());
			webConfig.setAliPayPhoto(pictureList);
			
		}
		//获取微信收款二维码
		if(webConfig != null && StringUtils.isNotEmpty(webConfig.getWeixinPay())) {
			String pictureList = UrlUtil.getImageUrl(webConfig.getWeixinPay());
			webConfig.setWeixinPayPhoto(pictureList);
		}

		return ResultUtil.result(SysConf.SUCCESS, webConfig);
	}
	
	@ApiOperation(value="记录访问页面", notes="记录访问页面")
	@GetMapping("/recorderVisitPage")
	public String recorderVisitPage (HttpServletRequest request,
			@ApiParam(name = "pageName", value = "页面名称",required = false) @RequestParam(name = "pageName", required = true) String pageName) {
		
		if(StringUtils.isEmpty(pageName)) {
			return ResultUtil.result(SysConf.SUCCESS, "页面名称不能为空");	
		}
		
        webVisitService.addWebVisit(null, IpUtils.getIpAddr(request), EBehavior.VISIT_PAGE.getBehavior(), null, pageName);
		
		return ResultUtil.result(SysConf.SUCCESS, "记录成功");
	}
	
	
	/**
	 * 设置博客的分类标签和内容
	 * @param list
	 * @return
	 */
	private List<Blog> setBlog(List<Blog> list) {
		List<String> fileUids = new ArrayList();
		List<String> sortUids = new ArrayList<String>();
		List<String> tagUids = new ArrayList<String>();

		list.forEach( item -> {
			if(StringUtils.isNotEmpty(item.getFileUid())) {
//				fileUids.append(item.getFileUid() + ",");
				fileUids.add(item.getFileUid());
			}
			if(StringUtils.isNotEmpty(item.getBlogSortUid())) {
				sortUids.add(item.getBlogSortUid());
			}
			if(StringUtils.isNotEmpty(item.getTagUid())) {
				tagUids.add(item.getTagUid());
			}
		});

		Collection<BlogSort> sortList = new ArrayList<>();
		Collection<Tag> tagList = new ArrayList<>();
		if (sortUids.size() > 0) {
			sortList = blogSortService.listByIds(sortUids);
		}
		if (tagUids.size() > 0) {
			tagList = tagService.listByIds(tagUids);
		}				
		
		
		Map<String, BlogSort> sortMap = new HashMap<String, BlogSort> ();
		Map<String, Tag> tagMap = new HashMap<String, Tag>();
		Map<String, String> pictureMap = new HashMap<String, String>();
		
		sortList.forEach(item -> {
			sortMap.put(item.getUid(), item);
		});
		
		tagList.forEach(item -> {
			tagMap.put(item.getUid(), item);
		});

		fileUids.forEach(item -> {
			//获取每一个uid对应的图片读取路径
			pictureMap.put(item, UrlUtil.getImageUrl(item));
		});
		
		
		for(Blog item : list) {
			
			//设置分类			
			if(StringUtils.isNotEmpty(item.getBlogSortUid())) {
				item.setBlogSort(sortMap.get(item.getBlogSortUid()));	
			}
						
			//获取标签
			if(StringUtils.isNotEmpty(item.getTagUid())) {
				List<String> tagUidsTemp = StringUtils.changeStringToString(item.getTagUid(), ",");
				List<Tag> tagListTemp = new ArrayList<Tag>();
				
				tagUidsTemp.forEach(tag -> {
					tagListTemp.add(tagMap.get(tag));
				});
				item.setTagList(tagListTemp);	
			}
			
			//获取图片
			if(StringUtils.isNotEmpty(item.getFileUid())) {
				List<String> pictureUidsTemp = StringUtils.changeStringToString(item.getFileUid(), ",");
				List<String> pictureListTemp = new ArrayList<String>();
				
				pictureUidsTemp.forEach(picture -> {
					pictureListTemp.add(pictureMap.get(picture));
				});
				item.setPhotoList(pictureListTemp);
			}		
		}
		return list;
	}

}

