package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Role;

public interface AdminDetailsDao {

	boolean registerUserRolesAndPermission(RolesDto dto);

	boolean deleteUserPermission(Long id);

	Role findPermissionByid(Long id);

	List<Role> findAllRoles();

}
