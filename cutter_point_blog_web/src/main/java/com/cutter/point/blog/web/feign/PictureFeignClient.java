package com.cutter.point.blog.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cutter.point.blog.web.config.FeignConfiguration;


/**
 * mogu_picture相关接口
 *
 */
@FeignClient(name = "cutter-point-blog-picture")
public interface PictureFeignClient {
	

	/**
	 * 获取文件的信息接口
	   @ApiImplicitParam(name = "fileIds", value = "fileIds", required = false, dataType = "String"),
	   @ApiImplicitParam(name = "code", value = "分割符", required = false, dataType = "String")
	 * @return
	 */
	@RequestMapping(value = "/cutter-point-blog-picture/file/getPicture", method = RequestMethod.GET)
	public String getPicture(@RequestParam("fileIds") String fileIds, @RequestParam("code") String code);
	
	@RequestMapping(value = "/cutter-point-blog-picture/file/hello", method = RequestMethod.GET)
	public String hello();
}
