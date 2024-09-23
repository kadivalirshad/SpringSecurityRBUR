package com.example.demo.entity;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

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
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "USER_ID")
	private UUID user_id;
	@Column(name = "USERNAME", nullable = false, length = 50)
	private String username;
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	@Column(name = "NAME")
	private String name;
	@Column(name = "EMAIL", nullable = false)
	private String email;
	@Column(name = "MOBILE", nullable = false)
	private String mobile;
	@Column(name = "GENDER", nullable = false)
	private String gender;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();
	@Column(name = "PLAIN_PASS")
	private String plain_pass;
	@Transient
	private String role_name[];

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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
