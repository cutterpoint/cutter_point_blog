package com.moxi.mogublog.web.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moxi.mogublog.web.config.FeignConfiguration;


/**
 * mogu_picture相关接口
 * @author xuzhixiang
 *
 */

@FeignClient(name = "mogu-picture", url = "http://localhost:8602/", configuration = FeignConfiguration.class)
public interface PictureFeignClient {
	

	/**
	 * 获取文件的信息接口
	   @ApiImplicitParam(name = "fileIds", value = "fileIds", required = false, dataType = "String"),
	   @ApiImplicitParam(name = "code", value = "分割符", required = false, dataType = "String")
	   
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/file/getPicture", method = RequestMethod.GET)
	public String getPicture(@RequestParam("fileIds") String fileIds, @RequestParam("code") String code);
	
	@RequestMapping(value = "/file/hello", method = RequestMethod.GET)
	public String hello();
}
