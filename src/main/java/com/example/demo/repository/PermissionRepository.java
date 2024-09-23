package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Permissions;

public interface PermissionRepository extends JpaRepository<Permissions,Long>{

	Permissions findByName(String permissionName);

}
