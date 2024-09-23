package com.example.demo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RolesDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Permissions;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Component
public class UserDetailsDaoImpl {
   
	@Autowired
	UserRepository userRepo;
	@Autowired RoleRepository rolerepository;
    @Autowired PasswordEncoder encoder;
    @Autowired HttpSession session;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private PermissionRepository permissionRepository;
	public boolean register(UserDto user) {
		boolean flag=false;
		try {
			ModelMapper modelMapper = new ModelMapper();
			List<Role> rolesList=new ArrayList<>(); 
			Users mapUser=modelMapper.map(user,Users.class);
			mapUser.setPlain_pass(user.getPassword());
			mapUser.setPassword(encoder.encode(user.getPassword()));
			List<String> selectedRoles=Arrays.asList(user.getRole_name());
			for(String roleName:selectedRoles) {
			Role role = rolerepository.findByName(roleName);
			if(role ==null) {
				role = checkRoleExist(roleName);
			}
			rolesList.add(role);
		   }
			mapUser.setRoles(rolesList);
			userRepo.save(mapUser);
			flag=true;
			
		}
		catch(Exception e) {
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
	        List<String> userRoles =authentication.getAuthorities().stream().map((a)->a.getAuthority()).toList();
	        
		    var Username=authentication.getName();
//		List<String> rollList = Arrays.asList(userRoles.toString());
		
		return userRoles.contains("ROLE_USER") && !userRoles.contains("ROLE_ADMIN") ? userRepo.findByMutipleUser(Username).stream().toList(): userRepo.findAll();
	}

	public void deleteUser(UUID id) {
		try {
		 userRepo.deleteById(id);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public Optional<Users> getUserDetailsById(UUID id) {
		return userRepo.findById(id);
	}

	public boolean getUserName(String userName) {
		return userRepo.getUserName(userName) != null ? true : false;
	}

    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
        Users user=userRepo.findByUserName(loginDto.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication,user);
        
        return token;
    }

	public List<Role> findAllRoles() {
		return rolerepository.findAll();
	}

	public boolean registerUserRolesAndPermission(RolesDto dto) {
		boolean flag=false;
		try {
			ModelMapper modelMapper = new ModelMapper();
			List<Permissions> PermissionList=new ArrayList<>(); 
			Role mapRole=modelMapper.map(dto,Role.class);
			mapRole.setName(dto.getName().toUpperCase());
			List<String> selectedPermission=Arrays.asList(dto.getPermission_name());
			for(String permissionName:selectedPermission) {
			Permissions permission = permissionRepository.findByName(permissionName);
			if(permission ==null) {
				permission = checkPermissionExist(permissionName);
			}
			PermissionList.add(permission);
		   }
			mapRole.setPermission(PermissionList);
			rolerepository.save(mapRole);
			flag=true;
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	private Permissions checkPermissionExist(String permissionName) {
		Permissions permission = new Permissions();
		permission.setName(permissionName);
		return permissionRepository.save(permission);
	}

	public boolean deleteUserPermission(Long id) {
	    boolean flag=false;
	    try {
	    	rolerepository.deleteById(id);
	    	flag=true;
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
		return flag;
	}

	public Role findPermissionByid(Long id) {
		return rolerepository.findById(id).orElseGet(()->new Role());
	}

	public boolean getRoleName(String name) {
		return rolerepository.getRoleName(name) != null ? true : false;
	}

}
