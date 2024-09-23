package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AdminDetailsDao;
import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Role;

@Service
public class AdminServiceImpl implements AdminService{
   
	@Autowired AdminDetailsDao adminDetailsDao;
	
	public boolean registerUserRolesAndPermission(RolesDto dto) {
		return adminDetailsDao.registerUserRolesAndPermission(dto);
	}

	public boolean deleteUserPermission(Long id) {
		return adminDetailsDao.deleteUserPermission(id);
	}

	public Role findPermissionByid(Long id) {
		return adminDetailsDao.findPermissionByid(id);
	}

	public List<Role> findAllRoles() {
		return adminDetailsDao.findAllRoles();
	}

}
