package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role,Long>{
	 
	Role findByName(String name);
	 
	 @Query("Select u.name from Role u where u.name = :name")
	 Object getRoleName(String name);

}
