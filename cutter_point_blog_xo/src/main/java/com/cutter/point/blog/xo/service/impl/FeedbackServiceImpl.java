package com.cutter.point.blog.xo.service.impl;

import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.entity.Feedback;
import com.cutter.point.blog.xo.mapper.FeedbackMapper;
import com.cutter.point.blog.xo.service.FeedbackService;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 反馈表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-08
 */
@Service
public class FeedbackServiceImpl extends SuperServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

}
