package com.example.demo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.sql.ast.spi.AliasCollector;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class UserDetailsDaoImpl implements UserDetailsDoa {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository rolerepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public boolean register(UserDto user) {
		boolean flag = false;
		try {
			ModelMapper modelMapper = new ModelMapper();
//			List<Role> rolesList = new ArrayList<>();
			Users mapUser = modelMapper.map(user, Users.class);
			mapUser.setPlain_pass(user.getPassword());
			mapUser.setPassword(encoder.encode(user.getPassword()));
			List<String> selectedRoles = Arrays.asList(user.getRole_name());
//			for (String roleName : selectedRoles) {
//				Role role = rolerepository.findByName(roleName);
//				if (role == null) {
//					role = checkRoleExist(roleName);
//				}
//				rolesList.add(role);
//			}
			
			List<Role> rolesList = selectedRoles.stream()
				    .map(roleName -> {
				        Role role = rolerepository.findByName(roleName);
				        return (role != null) ? role : checkRoleExist(roleName);
				    })
				    .collect(Collectors.toList());
			
			
			mapUser.setRoles(rolesList);
			userRepo.save(mapUser);
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	private Role checkRoleExist(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return rolerepository.save(role);
	}

	public List<Users> findAllUsers() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> userRoles = authentication.getAuthorities().stream().map((a) -> a.getAuthority()).toList();

		var Username = authentication.getName();
//		List<String> rollList = Arrays.asList(userRoles.toString());

		return  !userRoles.contains("ROLE_ADMIN")
				? userRepo.findByMutipleUser(Username).stream().toList()
				: userRepo.findAll();
	}

	public void deleteUser(UUID id) {
		try {
			userRepo.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Users getUserDetailsById(UUID id) {
		return userRepo.findById(id).get();
	}

	public boolean getUserName(String userName) {
		return userRepo.getUserName(userName) != null ? true : false;
	}

	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		Users user = userRepo.findByUserName(loginDto.getUsername());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication, user);

		return token;
	}

	public boolean getRoleName(String name) {
		return rolerepository.getRoleName(name) != null ? true : false;
	}

	public List<Role> findAllRoles() {
		return rolerepository.findAll();
	}

}
