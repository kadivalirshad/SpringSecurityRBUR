package com.example.demo.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,UUID>{  
	@Query("SELECT u FROM Users u WHERE u.username = :username")
	Users findByUserName(String username);
    
	@Query("Select u.username from Users u where u.username = :userName")
	String getUserName(String userName);
    
	@Query("SELECT u FROM Users u WHERE u.username = :username")
	List<Users> findByMutipleUser(String username);

}
