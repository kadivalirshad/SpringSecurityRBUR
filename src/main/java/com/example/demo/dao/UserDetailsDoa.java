package com.example.demo.dao;

import java.util.List;
import java.util.UUID;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;

public interface UserDetailsDoa {

	boolean register(UserDto user);

	List<Users> findAllUsers();

	void deleteUser(UUID id);

	Users getUserDetailsById(UUID id);

	boolean getUserName(String userName);

	String login(LoginDto loginDto);

	boolean getRoleName(String name);

	List<Role> findAllRoles();

}
