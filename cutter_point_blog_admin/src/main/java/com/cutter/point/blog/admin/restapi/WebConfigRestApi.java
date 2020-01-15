package com.cutter.point.blog.admin.restapi;


import javax.servlet.http.HttpServletRequest;

import com.cutter.point.blog.admin.constrant.BlogWebYmlConstrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cutter.point.blog.admin.feign.PictureFeignClient;
import com.cutter.point.blog.admin.global.SQLConf;
import com.cutter.point.blog.admin.global.SysConf;
import com.cutter.point.blog.admin.log.OperationLogger;
import com.cutter.point.blog.utils.ResultUtil;
import com.cutter.point.blog.utils.StringUtils;
import com.cutter.point.blog.utils.WebUtils;
import com.cutter.point.blog.xo.entity.WebConfig;
import com.cutter.point.blog.xo.service.WebConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Arrays;

/**
 * <p>
 * 网站配置表 RestApi
 * </p>
 *
 * @author xzx19950624@qq.com
 * @since 2018年11月11日15:19:28
 */
//@PreAuthorize("hasRole('Administrator')")
@Api(value="系统配置RestApi",tags={"WebConfigRestApi"})
@RestController
@RequestMapping("/webConfig")
public class WebConfigRestApi {
		
	@Autowired
	WebConfigService webConfigService;
	
	@Autowired
	private PictureFeignClient pictureFeignClient;
	
	@ApiOperation(value="获取网站配置", notes="获取网站配置")
	@GetMapping("/getWebConfig")
	public String getWebConfig(HttpServletRequest request) {

		QueryWrapper<WebConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
		WebConfig webConfig = webConfigService.getOne(queryWrapper);
		
		//获取图片
		if(webConfig !=null && StringUtils.isNotEmpty(webConfig.getLogo())) {
			String pictureList = BlogWebYmlConstrant.imageUrl + "?uid=" + webConfig.getLogo();
			webConfig.setPhotoList(Arrays.asList(pictureList));
		}
		
		//获取支付宝收款二维码
		if(webConfig != null && StringUtils.isNotEmpty(webConfig.getAliPay())) {
			String pictureList = BlogWebYmlConstrant.imageUrl + "?uid=" + webConfig.getAliPay();
			if(WebUtils.getPicture(pictureList).size() > 0) {
				webConfig.setAliPayPhoto(pictureList);
			}
			
		}
		//获取微信收款二维码
		if(webConfig != null && StringUtils.isNotEmpty(webConfig.getWeixinPay())) {
			String pictureList = BlogWebYmlConstrant.imageUrl + "?uid=" + webConfig.getWeixinPay();
			if(WebUtils.getPicture(pictureList).size() > 0) {
				webConfig.setWeixinPayPhoto(pictureList);
			}
			
		}
		
		return ResultUtil.result(SysConf.SUCCESS, webConfig);		
	}
	
	@OperationLogger(value="修改网站配置")
	@ApiOperation(value="修改网站配置", notes="修改网站配置")
	@PostMapping("/editWebConfig")
	public String editWebConfig(HttpServletRequest request, @RequestBody WebConfig webConfig) {
		
		if(StringUtils.isEmpty(webConfig.getUid())) {
			webConfigService.save(webConfig);
		} else {
			webConfigService.updateById(webConfig);	
		}				
		return ResultUtil.result(SysConf.SUCCESS, "更新成功");		
	}
}

