package com.cutter.point.blog.admin.restapi;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.cutter.point.blog.admin.constrant.BlogWebYmlConstrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cutter.point.blog.admin.feign.PictureFeignClient;
import com.cutter.point.blog.admin.global.SysConf;
import com.cutter.point.blog.admin.log.OperationLogger;
import com.cutter.point.blog.utils.ResultUtil;
import com.cutter.point.blog.utils.StringUtils;
import com.cutter.point.blog.utils.WebUtils;
import com.cutter.point.blog.xo.entity.Admin;
import com.cutter.point.blog.xo.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统设置RestApi
 * @author xzx19950624@qq.com
 * @date 2018年11月6日下午8:23:36
 */

@RestController
@RequestMapping("/system")
@Api(value="系统设置RestApi",tags={"SystemRestApi"})
public class SystemRestApi {
	
	@Autowired
	AdminService adminService;
		
	@Autowired
	private PictureFeignClient pictureFeignClient;
	
	/**
	 * 获取关于我的信息
	 * @date 2020年1月13日22:12:55
	 */
	
	@ApiOperation(value="获取我的信息", notes="获取我的信息")
	@GetMapping("/getMe")
	public String getMe(HttpServletRequest request) {
		
		if(request.getAttribute(SysConf.ADMIN_UID) == null || request.getAttribute(SysConf.ADMIN_UID) == "") {
			return ResultUtil.result(SysConf.ERROR, "登录失效，请重新登录");
		}
		
		Admin admin = adminService.getById(request.getAttribute(SysConf.ADMIN_UID).toString());
		admin.setPassWord(null); //清空密码，防止泄露
		
		//获取图片
		if(StringUtils.isNotEmpty(admin.getAvatar())) {
			String pictureList = BlogWebYmlConstrant.imageUrl + "?uid=" + admin.getAvatar();
			admin.setPhotoList(Arrays.asList(pictureList));
//			admin.setPhotoList(WebUtils.getPicture(pictureList));
		}
		
		return ResultUtil.result(SysConf.SUCCESS, admin);
	}
	
	@OperationLogger(value="编辑我的信息")
	@ApiOperation(value="编辑我的信息", notes="获取我的信息")
	@PostMapping("/editMe")
	public String editMe(HttpServletRequest request, @RequestBody Admin admin) {
		
		Boolean save = adminService.updateById(admin);
		
		return ResultUtil.result(SysConf.SUCCESS, save);
	}
		
	@ApiOperation(value="修改密码", notes="修改密码")
	@PostMapping("/changePwd")
	public String changePwd(HttpServletRequest request,
			@ApiParam(name = "oldPwd", value = "旧密码",required = false) @RequestParam(name = "oldPwd", required = false) String oldPwd,
			@ApiParam(name = "newPwd", value = "新密码",required = false) @RequestParam(name = "newPwd", required = false) String newPwd) throws NoSuchAlgorithmException {
		
		if(request.getAttribute(SysConf.ADMIN_UID) == null || request.getAttribute(SysConf.ADMIN_UID) == "") {
			return ResultUtil.result(SysConf.ERROR, "登录失效，请重新登录");
		}
		if(StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
			return ResultUtil.result(SysConf.ERROR, "必填项不能为空");
		}

		Admin admin = adminService.getById(request.getAttribute(SysConf.ADMIN_UID).toString());
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
	    boolean isPassword = encoder.matches(oldPwd, admin.getPassWord());
	    
		if(isPassword) {
			admin.setPassWord(encoder.encode(newPwd));
			admin.updateById();
			return ResultUtil.result(SysConf.SUCCESS, "修改成功");
		} else {
			return ResultUtil.result(SysConf.ERROR, "输入密码错误");
		}
		
	}
	
}
