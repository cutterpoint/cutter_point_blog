package com.cutter.point.blog.xo.service.impl;

import com.cutter.point.blog.xo.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cutter.point.blog.xo.mapper.CommentMapper;
import com.cutter.point.blog.xo.service.CommentService;
import com.cutter.point.blog.base.global.BaseSQLConf;
import com.cutter.point.blog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-08
 */
@Service
public class CommentServiceImpl extends SuperServiceImpl<CommentMapper, Comment> implements CommentService {
	
	@Autowired
	CommentMapper commentMapper;
	
	@Override
	public Integer getCommentCount(int status) {
		QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(BaseSQLConf.STATUS, status);
		return commentMapper.selectCount(queryWrapper);
	}

}
