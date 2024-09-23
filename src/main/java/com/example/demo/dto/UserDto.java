package com.example.demo.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.validator.constraints.UniqueElements;

import com.example.demo.entity.Role;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;


public class UserDto {

	 private UUID user_id;
	 @NotEmpty(message = "Username Should Not Be Empty")
	 @UniqueElements
	 private String username;
	 @NotEmpty(message = "password Should Not Be Empty")
	 private String password;
	 @NotEmpty(message = "Name Should Not Be Empty")
	 private String name;
	 @NotEmpty(message = "Email Should Not Be Empty")
	 private String email;
	 @NotEmpty(message = "Mobile NUmber Should Not Be Empty")
	 private String mobile;
	 @NotEmpty(message = "Gender Should Not Be Empty")
	 private String gender;
	 private List<Role> roles = new ArrayList<>();
	 private String role_name[];
	 private String plain_pass;
	 
	 
	public String getPlain_pass() {
		return plain_pass;
	}
	public void setPlain_pass(String plain_pass) {
		this.plain_pass = plain_pass;
	}
	public String[] getRole_name() {
		return role_name;
	}
	public void setRole_name(String[] role_name) {
		this.role_name = role_name;
	}
	
	public UUID getUser_id() {
		return user_id;
	}
	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public UserDto(){}
	public UserDto(UUID user_id, String username, String password, String name, String email, String mobile,
			String gender, List<Role> roles) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.roles = roles;
	}	
	 
	 
}


