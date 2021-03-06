package com.cutter.point.blog.admin.restapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cutter.point.blog.admin.global.SysConf;
import com.cutter.point.blog.utils.ResultUtil;
import com.cutter.point.blog.xo.service.BlogService;
import com.cutter.point.blog.xo.service.CommentService;
import com.cutter.point.blog.xo.service.WebVisitService;
import com.cutter.point.blog.base.enums.EStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 首页RestApi
 * @author xzx19950624@qq.com
 * @date 2018年10月22日下午3:27:24
 */
@RestController
@RequestMapping("/index")
@Api(value="首页RestApi", tags={"IndexRestApi"})
public class IndexRestApi {
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	WebVisitService webVisitService;

	@ApiOperation(value="首页初始化数据", notes="首页初始化数据", response = String.class)	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init() {
		Map<String, Object> map = new HashMap<String, Object>();
		//博客数
		Integer blogCount = blogService.getBlogCount(EStatus.ENABLE);
		//评论数
		Integer commentCount = commentService.getCommentCount(EStatus.ENABLE);
		//每日的访问量
		Integer visitCount = webVisitService.getWebVisitCount();
		
		map.put(SysConf.BLOG_COUNT, blogCount);
		map.put(SysConf.COMMENT_COUNT, commentCount);
		map.put(SysConf.VISIT_COUNT, visitCount);

		return ResultUtil.result(SysConf.SUCCESS, map);
	}
	
	@ApiOperation(value="获取最近一周用户独立IP数和访问量", notes="获取最近一周用户独立IP数和访问量", response = String.class)	
	@RequestMapping(value = "/getVisitByWeek", method = RequestMethod.GET)
	public String getVisitByWeek() {		
		
		Map<String,Object> visitByWeek =  webVisitService.getVisitByWeek();
		
		return ResultUtil.result(SysConf.SUCCESS, visitByWeek);
	}
	
	@ApiOperation(value="获取每个标签下文章数目", notes="获取每个标签下文章数目", response = String.class)	
	@RequestMapping(value = "/getBlogCountByTag", method = RequestMethod.GET)
	public String getBlogCountByTag() {		
		
		List<Map<String, Object>> blogCountByTag = blogService.getBlogCountByTag();
		
		return ResultUtil.result(SysConf.SUCCESS, blogCountByTag);
	}
	
}
