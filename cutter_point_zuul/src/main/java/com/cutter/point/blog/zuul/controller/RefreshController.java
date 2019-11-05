package com.cutter.point.blog.zuul.controller;

import com.cutter.point.blog.zuul.config.RefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RefreshController
 * @Description 路由web访问刷新
 * @Author xiaof
 * @Date 2019/11/5 22:08
 * @Version 1.0
 **/
@RestController
public class RefreshController {
    @Autowired
    RefreshRouteService refreshRouteService;

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @GetMapping("/refreshRoute")
    public String refresh() {
        refreshRouteService.refreshRoute();
        return "refresh success";
    }

    @RequestMapping("/watchRoute")
    public Object watchNowRoute() {
        //可以用debug模式看里面具体是什么
        return zuulHandlerMapping.getHandlerMap();
    }
}
