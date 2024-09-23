package com.example.demo.dto;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Permissions;
import com.example.demo.entity.Role;

import jakarta.validation.constraints.NotEmpty;

public class RolesDto {
 
	private Long role_id;
	@NotEmpty(message = "Roll Name Should Not Be Empty")
	private String name;
	private List<Permissions> permission=new ArrayList<>();
	private String permission_name[];
	
	public String[] getPermission_name() {
		return permission_name;
	}
	public void setPermission_name(String[] permission_name) {
		this.permission_name = permission_name;
	}
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Permissions> getPermission() {
		return permission;
	}
	public void setPermission(List<Permissions> permission) {
		this.permission = permission;
	}
	public RolesDto(Long role_id, @NotEmpty(message = "Roll Name Should Not Be Empty") String name,
			List<Permissions> permission) {
		super();
		this.role_id = role_id;
		this.name = name;
		this.permission = permission;
	}
	
	public RolesDto(){}
	
	
}
  
