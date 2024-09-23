package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="mst_role")
public class Role {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROLE_ID") private Long role_id;
	@Column(name="NAME") private String name;
	@Transient private String permission_name[];
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "roles_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<Permissions> permission = new ArrayList<>();
	
	
	
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	
	public Long getId() {
		return role_id;
	}
	public void setId(Long role_id) {
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
	
	
	public Role(Long role_id, String name, List<Permissions> permission) {
		super();
		this.role_id = role_id;
		this.name = name;
		this.permission = permission;
	}
	public Role(){}
	
	
	
}
