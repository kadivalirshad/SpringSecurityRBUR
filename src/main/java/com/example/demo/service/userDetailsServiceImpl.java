package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDetailsDaoImpl;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RolesDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;

@Service
public class userDetailsServiceImpl {
   
	@Autowired
	UserDetailsDaoImpl userDao;
	
	public boolean register(UserDto user) {
		return userDao.register(user);
	}

	public List<Users> findAllUsers() {
		return userDao.findAllUsers();
	}

	public void deteleUser(UUID id) {
	  userDao.deleteUser(id);
	}

	public Optional<Users> getUserDetailsById(UUID id) {
		return userDao.getUserDetailsById(id);
	}

	public boolean getUserName(String userName) {
		return userDao.getUserName(userName);
	}

	public String login(LoginDto loginDto) {
		return userDao.login(loginDto);
	}

	public List<Role> findAllRoles() {
		return userDao.findAllRoles();
	}

	public boolean registerUserRolesAndPermission(RolesDto dto) {
		return userDao.registerUserRolesAndPermission(dto);
	}

	public boolean deleteUserPermission(Long id) {
		return userDao.deleteUserPermission(id);
	}

	public Role findPermissionByid(Long id) {
		return userDao.findPermissionByid(id);
	}

	public boolean getRoleName(String name) {
		return userDao.getRoleName(name);
	}

}
