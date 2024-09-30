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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	private String provider;
	private List<Role> roles = new ArrayList<>();
	@NotEmpty(message = "At Least One Role Is Must")
	private String role_name[];
	private String plain_pass;
}
