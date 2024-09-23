package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;

public interface UserDetailsServices {

	String login(LoginDto loginDto);

	Users getUserDetailsById(UUID id);

	List<Users> findAllUsers();

	boolean register(UserDto user);

	boolean getUserName(String username);

	boolean getRoleName(String name);

	public void deleteUser(UUID id);

	List<Role> findAllRoles();

}
