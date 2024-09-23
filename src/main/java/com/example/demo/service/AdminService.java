package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Role;

public interface AdminService {
	boolean registerUserRolesAndPermission(RolesDto dto);
	Role findPermissionByid(Long id);
	boolean deleteUserPermission(Long id);
	List<Role> findAllRoles();
}
