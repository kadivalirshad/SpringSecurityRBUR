package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.UserDetailsDoa;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;

@Service
public class userDetailsServiceImpl implements UserDetailsServices{
 
	@Autowired
	UserDetailsDoa userDao;

	@Override
	public boolean register(UserDto user) {
		return userDao.register(user);
	}
    
	@Override
	public List<Users> findAllUsers() {
		return userDao.findAllUsers();
	}

	@Override
	public void deleteUser(UUID id) {
		userDao.deleteUser(id);
	}
   
	@Override
	public Users getUserDetailsById(UUID id) {
		return userDao.getUserDetailsById(id);
	}
	@Override
	public boolean getUserName(String userName) {
		return userDao.getUserName(userName);
	}
	@Override
	public String login(LoginDto loginDto) {
		return userDao.login(loginDto);
	}
	@Override
	public boolean getRoleName(String name) {
		return userDao.getRoleName(name);
	}
	@Override
	public List<Role> findAllRoles() {
		return userDao.findAllRoles();
	}

}
