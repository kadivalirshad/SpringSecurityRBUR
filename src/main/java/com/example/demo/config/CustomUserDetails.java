package com.example.demo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Service
public class CustomUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HttpSession session;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUserName(username);
		if (user != null) {
//	        	 session.setAttribute("userId",user.getUser_id());
//	        	 List<String> roll=user.getRoles().stream().map((role)-> role.getName()).collect(Collectors.toList());
//	        	 session.setAttribute("roll",roll);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					mapRolesAndPermissionsToAuthorities(user.getRoles()));

		} else {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
	}

//	private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
//        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
//                .collect(Collectors.toList());
//        return mapRoles;
//    }

	private Collection<? extends GrantedAuthority> mapRolesAndPermissionsToAuthorities(Collection<Role> roles) {
		return roles.stream().flatMap(role -> {
			// Map role to authorities
			List<GrantedAuthority> roleAuthorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.getName()));

			// Map permissions to authorities
			List<GrantedAuthority> permissionAuthorities = role.getPermission().stream()
					.map(permission -> new SimpleGrantedAuthority(permission.getName())).collect(Collectors.toList());

			// Combine both role and permission authorities
			List<GrantedAuthority> combinedAuthorities = new ArrayList<>(roleAuthorities);
			combinedAuthorities.addAll(permissionAuthorities);
			return combinedAuthorities.stream();
		}).collect(Collectors.toList());
	}

//	 return new org.springframework.security.core.userdetails.User(
//	            user.getUsername(),
//	            user.getPassword(),
//	            user.getRoles().stream()
//	                .flatMap(role -> role.getPermissions().stream()
//	                    .map(permission -> new SimpleGrantedAuthority(permission.getName())))
//	                .collect(Collectors.toSet())
//	        );

}
