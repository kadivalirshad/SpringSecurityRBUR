package com.example.demo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Permissions;
import com.example.demo.entity.Role;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;

@Component
public class AdminDetailsDaoImpl implements AdminDetailsDao {
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private RoleRepository rolerepository;

	public boolean registerUserRolesAndPermission(RolesDto dto) {
		boolean flag = false;
		try {
			ModelMapper modelMapper = new ModelMapper();
			List<Permissions> PermissionList = new ArrayList<>();
			Role mapRole = modelMapper.map(dto, Role.class);
			mapRole.setName(dto.getName().toUpperCase());
			List<String> selectedPermission = Arrays.asList(dto.getPermission_name());
			for (String permissionName : selectedPermission) {
				Permissions permission = permissionRepository.findByName(permissionName);
				if (permission == null) {
					permission = checkPermissionExist(permissionName);
				}
				PermissionList.add(permission);
			}
			mapRole.setPermission(PermissionList);
			rolerepository.save(mapRole);
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private Permissions checkPermissionExist(String permissionName) {
		Permissions permission = new Permissions();
		permission.setName(permissionName);
		return permissionRepository.save(permission);
	}

	public List<Role> findAllRoles() {
		return rolerepository.findAll();
	}

	public boolean deleteUserPermission(Long id) {
		boolean flag = false;
		try {
			rolerepository.deleteById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public Role findPermissionByid(Long id) {
		return rolerepository.findById(id).orElseGet(() -> new Role());
	}

}
