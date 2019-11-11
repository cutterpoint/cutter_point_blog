package com.cutter.point.blog.admin.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cutter.point.blog.admin.global.SQLConf;
import com.cutter.point.blog.xo.entity.Admin;
import com.cutter.point.blog.xo.entity.AdminRole;
import com.cutter.point.blog.xo.entity.Role;
import com.cutter.point.blog.xo.service.AdminRoleService;
import com.cutter.point.blog.xo.service.AdminService;
import com.cutter.point.blog.xo.service.RoleService;

@Service
public class SecurityUserDetailsService implements UserDetailsService{
	
	@Autowired
    private AdminService adminService;
	
	@Autowired
    private AdminRoleService adminRoleService;
	
	@Autowired
    private RoleService roleService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
		queryWrapper.eq(SQLConf.USER_NAME, username);
		Admin admin = adminService.getOne(queryWrapper);
		
		if(admin == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}else {
			//查询出角色信息封装到admin中
		    QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
		    wrapper.eq(SQLConf.ADMINUID,admin.getUid());
		    List<AdminRole> adminRoleList = adminRoleService.list(wrapper);
		    List<String> roleUids = new ArrayList<>();
		    for (AdminRole adminRole : adminRoleList) {
				String roleUid = adminRole.getRoleUid();
				roleUids.add(roleUid);
		    }
		    List<Role> roles = (List<Role>) roleService.listByIds(roleUids);
			List<String> roleNames = new ArrayList<String>();
			for (Role role : roles) {
				roleNames.add(role.getRoleName());
			}
			admin.setRoleNames(roleNames);
			 
			return SecurityUserFactory.create(admin);
		}
	}
}
