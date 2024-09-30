package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Permissions;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permissions,Long>{

	Permissions findByName(String permissionName);

}
