package com.cutter.point.blog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cutter.point.blog.base.entity.SuperEntity;

/**
 * <p>
 * 收藏表
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-08
 */
@TableName("t_collect")
public class Collect extends SuperEntity<Collect> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的uid
     */
    private String userUid;

    /**
     * 博客的uid
     */
    private String blogUid;

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getBlogUid() {
		return blogUid;
	}

	public void setBlogUid(String blogUid) {
		this.blogUid = blogUid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
