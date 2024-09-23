package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Permissions;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolesDto {

	private Long role_id;
	@NotEmpty(message = "Roll Name Should Not Be Empty")
	private String name;
	private List<Permissions> permission = new ArrayList<>();
	private String permission_name[];

}
