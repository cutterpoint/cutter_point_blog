package com.cutter.point.blog.web.restapi;


import javax.servlet.http.HttpServletRequest;

import com.cutter.point.blog.web.util.UrlUtil;
import com.cutter.point.blog.xo.entity.TFileStore;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cutter.point.blog.utils.ResultUtil;
import com.cutter.point.blog.utils.StringUtils;
import com.cutter.point.blog.utils.WebUtils;
import com.cutter.point.blog.web.feign.PictureFeignClient;
import com.cutter.point.blog.web.global.SQLConf;
import com.cutter.point.blog.web.global.SysConf;
import com.cutter.point.blog.xo.entity.Admin;
import com.cutter.point.blog.xo.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Arrays;
import java.util.List;

/**
 * 关于我 RestApi
 * @author xzx19950624@qq.com
 * @date 2018年11月12日14:51:54
 */
@RestController
@RequestMapping("/about")
@Api(value="关于我 RestApi",tags={"AboutMeRestApi"})
public class AboutMeRestApi {
	
	@Autowired
	private PictureFeignClient pictureFeignClient;
	
	@Autowired
	@Qualifier("adminServiceImpl")
	AdminService adminService;
	
	private static Logger log = LogManager.getLogger(AboutMeRestApi.class);
	
	/**
	 * 获取关于我的信息
	 * @author xzx19950624@qq.com
	 * @date 2018年11月6日下午8:57:48
	 */
	
	@ApiOperation(value="关于我", notes="关于我")
	@GetMapping("/getMe")
	public String getMe(HttpServletRequest request) {
				
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(SQLConf.USER_NAME, SysConf.ADMIN);
		Admin admin = adminService.getOne(queryWrapper);		
		admin.setPassWord(null); //清空密码，防止泄露
		//获取图片
		if(StringUtils.isNotEmpty(admin.getAvatar())) {
			//这里查询照片信息表，然后通过参数页面上获取read读取图片
//			String pictureList = this.pictureFeignClient.getPicture(admin.getAvatar(), ",");
			admin.setPhotoList(Arrays.asList(UrlUtil.getImageUrl(admin.getAvatar())));
		}
		log.info("获取用户信息");
		Admin result = new Admin();		
		result.setNickName(admin.getNickName());
		result.setOccupation(admin.getOccupation());
		result.setSummary(admin.getSummary());
		result.setWeChat(admin.getWeChat());
		result.setQqNumber(admin.getQqNumber());
		result.setEmail(admin.getEmail());
		result.setMobile(admin.getMobile());
		result.setAvatar(admin.getAvatar());
		result.setPhotoList(admin.getPhotoList());
		return ResultUtil.result(SysConf.SUCCESS, result);
	}
	
	@ApiOperation(value="获取联系方式", notes="获取联系方式")
	@GetMapping("/getContact")
	public String getContact(HttpServletRequest request) {
				
		Admin admin = adminService.getById("1f01cd1d2f474743b241d74008b12333");		
		
		if(admin != null) {

			Admin result = new Admin();
			result.setWeChat(admin.getWeChat());
			result.setQqNumber(admin.getQqNumber());
			result.setEmail(admin.getEmail());
			result.setMobile(admin.getMobile());
			return ResultUtil.result(SysConf.SUCCESS, result);
		} else {
			return ResultUtil.result(SysConf.ERROR, "获取失败");
		}
		
	}
	
	
}

