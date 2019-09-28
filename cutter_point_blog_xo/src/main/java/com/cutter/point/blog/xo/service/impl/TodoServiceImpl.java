package com.cutter.point.blog.xo.service.impl;

import com.cutter.point.blog.xo.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.mapper.TodoMapper;
import com.cutter.point.blog.xo.service.TodoService;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 待办事项表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2019年6月29日10:31:44
 */
@Service
public class TodoServiceImpl extends SuperServiceImpl<TodoMapper, Todo> implements TodoService {
	
	@Autowired
	TodoMapper  todoMapper;
	
	@Override
	public void toggleAll(Integer done, String adminUid) {
		todoMapper.toggleAll(done, adminUid);
	}

}
