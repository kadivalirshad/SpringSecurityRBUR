package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Permissions")
public class Permissions {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PERMISSION_ID") private Long permission_id;
	@Column(name="NAME") private String name;
	
	public Long getPermission_id() {
		return permission_id;
	}
	public void setPermission_id(Long permission_id) {
		this.permission_id = permission_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
