package com.cutter.point.blog.admin.restapi;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cutter.point.blog.admin.constrant.BlogWebYmlConstrant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cutter.point.blog.admin.feign.PictureFeignClient;
import com.cutter.point.blog.admin.global.SQLConf;
import com.cutter.point.blog.admin.global.SysConf;
import com.cutter.point.blog.admin.log.OperationLogger;
import com.cutter.point.blog.utils.ResultUtil;
import com.cutter.point.blog.utils.StringUtils;
import com.cutter.point.blog.utils.WebUtils;
import com.cutter.point.blog.xo.entity.ResourceSort;
import com.cutter.point.blog.xo.entity.StudyVideo;
import com.cutter.point.blog.xo.service.ResourceSortService;
import com.cutter.point.blog.xo.service.StudyVideoService;
import com.cutter.point.blog.base.enums.EStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 视频表 RestApi
 * </p>
 *
 * @author xzx19950624@qq.com
 * @since 2018年10月19日21:35:55
 */
//@PreAuthorize("hasRole('Administrator')")
@RestController
@RequestMapping("/studyVideo")
@Api(value="视频RestApi", tags={"StudyVideoRestApi"})
public class StudyVideoRestApi {
	
	@Autowired
	StudyVideoService studyVideoService;
	
	@Autowired
	ResourceSortService resourceSortService;
	
	@Autowired
	PictureFeignClient pictureFeignClient;
	
	private static Logger log = LogManager.getLogger(StudyVideoRestApi.class);
	
	@ApiOperation(value="获取学习视频列表", notes="获取学习视频列表", response = String.class)	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public String getList(HttpServletRequest request,
			@ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
			@ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
			@ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {
		
		QueryWrapper<StudyVideo> queryWrapper = new QueryWrapper<StudyVideo>();
		if(StringUtils.isNotEmpty(keyword) && !StringUtils.isEmpty(keyword.trim())) {
			queryWrapper.like(SQLConf.NAME, keyword.trim());
		}
		
		Page<StudyVideo> page = new Page<StudyVideo>();
		page.setCurrent(currentPage);
		page.setSize(pageSize);		
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);		
		queryWrapper.orderByDesc(SQLConf.CREATE_TIME);		
		IPage<StudyVideo> pageList = studyVideoService.page(page, queryWrapper);
		List<StudyVideo> list = pageList.getRecords();
		
		final StringBuffer fileUids = new StringBuffer();
		list.forEach( item -> {
			if(StringUtils.isNotEmpty(item.getFileUid())) {
				fileUids.append(item.getFileUid() + ",");
			}
		});		
		String pictureResult = null;
		Map<String, String> pictureMap = new HashMap<String, String>();
		
		if(fileUids != null) {
			pictureResult = BlogWebYmlConstrant.imageUrl + "?uid=" + fileUids.toString();
		}
		List<Map<String, Object>> picList = WebUtils.getPictureMap(pictureResult);
		
		picList.forEach(item -> {
			pictureMap.put(item.get("uid").toString(), item.get("url").toString());
		});
		
		for(StudyVideo item : list) {	
			//获取分类资源
			if(StringUtils.isNotEmpty(item.getFileUid())) {
				List<String> pictureUidsTemp = StringUtils.changeStringToString(item.getFileUid(), ",");
				List<String> pictureListTemp = new ArrayList<String>();				
				pictureUidsTemp.forEach(picture -> {
					pictureListTemp.add(pictureMap.get(picture));
				});
				item.setPhotoList(pictureListTemp);
			}	
			
			if(StringUtils.isNotEmpty(item.getResourceSortUid())) {
				ResourceSort resourceSort = resourceSortService.getById(item.getResourceSortUid());
				item.setResourceSort(resourceSort);
			}
		}
		log.info("返回结果");
		return ResultUtil.result(SysConf.SUCCESS, pageList);
	}
	
	@OperationLogger(value="增加学习视频")
	@ApiOperation(value="增加学习视频", notes="增加学习视频", response = String.class)	
	@PostMapping("/add")
	public String add(HttpServletRequest request, @RequestBody StudyVideo studyVideo) {
		
		if(StringUtils.isEmpty(studyVideo.getName()) || StringUtils.isEmpty(studyVideo.getResourceSortUid())) {
			return ResultUtil.result(SysConf.ERROR, "必填项不能为空");
		}
		studyVideo.insert();
		return ResultUtil.result(SysConf.SUCCESS, "添加成功");
	}
	
	@OperationLogger(value="编辑学习视频")
	@ApiOperation(value="编辑学习视频", notes="编辑学习视频", response = String.class)
	@PostMapping("/edit")
	public String edit(HttpServletRequest request, @RequestBody StudyVideo studyVideo) {
		
		if(StringUtils.isEmpty(studyVideo.getName()) || StringUtils.isEmpty(studyVideo.getResourceSortUid())) {
			return ResultUtil.result(SysConf.ERROR, "必填项不能为空");
		}
		studyVideo.updateById();
		return ResultUtil.result(SysConf.SUCCESS, "编辑成功");
	}
	
	@OperationLogger(value="删除学习视频")
	@ApiOperation(value="删除学习视频", notes="删除学习视频", response = String.class)
	@PostMapping("/delete")
	public String delete(HttpServletRequest request,
			@ApiParam(name = "uid", value = "唯一UID",required = true) @RequestParam(name = "uid", required = true) String uid			) {
		
		if(StringUtils.isEmpty(uid)) {
			return ResultUtil.result(SysConf.ERROR, "数据错误");
		}		
		StudyVideo studyVideo = studyVideoService.getById(uid);
		studyVideo.setStatus(EStatus.DISABLED);		
		studyVideo.updateById();
		return ResultUtil.result(SysConf.SUCCESS, "删除成功");
	}
}

