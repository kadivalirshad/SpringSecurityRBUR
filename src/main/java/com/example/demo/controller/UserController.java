package com.example.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.JwtAuthResponse;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RolesDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.service.userDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class UserController {
	
    @Autowired
    private userDetailsServiceImpl userService;
    

    @PostMapping("/login")
    public String login(@ModelAttribute("login") LoginDto loginDto){
        String token = userService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return "/index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/403")
    public String invalidAccess() {
        return "invalidAccess";
    }

    @GetMapping("/register") 
    @PreAuthorize("hasAuthority('CREATE')")
    public String register(Model model) {
        UserDto user = new UserDto();
        List<String> role=userService.findAllRoles().stream().map(Role::getName).collect(Collectors.toList());
        model.addAttribute("user", user);
        model.addAttribute("allrole",role);
        model.addAttribute("roles",null);
        return "register";
    }
    
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT')")
    public String editUserDetails(@PathVariable UUID id,Model model) {
    	Users user=userService.getUserDetailsById(id).get();
    	List<String> selectedRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    	List<String> allrole=userService.findAllRoles().stream().map(Role::getName).collect(Collectors.toList());
    	model.addAttribute("user",user);
    	model.addAttribute("roles",selectedRoles);
    	model.addAttribute("allrole",allrole);
    	return "register";
    }
    
    @GetMapping("/view/{id}")
    @PreAuthorize("hasAuthority('VIEW')")
    public String viewUserProfile(@PathVariable UUID id, Model model) {
    	Users user=userService.getUserDetailsById(id).get();
    	List<String> selectedRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    	model.addAttribute("user",user);
    	model.addAttribute("roles",selectedRoles);
        return "user";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('VIEW')")
    public String userList(Model model) {
        List<Users> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "UsersList"; 
    }

    @PostMapping("/register/save") 
    @PreAuthorize("hasAuthority('CREATE')")
    public String registration(@ModelAttribute("user") UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            return "register"; 
        }
        boolean register = userService.register(user);
        if (!register) {
            return "redirect:/register?error"; 
        }
        return "redirect:/register?success"; 
    }
    
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public String deleteUser(@PathVariable UUID id) {
    	userService.deteleUser(id);
    	return "redirect:/users";
    }
    
    @PostMapping("/userPermission/save")
    @PreAuthorize("hasAuthority('CREATE')")
    public String registerUserRolesAndPermission(@ModelAttribute("rolesDto") RolesDto dto,BindingResult result) {
    	 if (result.hasErrors()) {
             return "userPermission"; 
         }
    	 boolean userRoles=userService.registerUserRolesAndPermission(dto);
    	 if (!userRoles) {
             return "redirect:/userPermission?error"; 
         }
         return "redirect:/userPermission?success"; 
    }
    
    
    @PostMapping("/checkUserName")
    public ResponseEntity<Boolean> checkUserName(@RequestParam String username ) {
        boolean exists = userService.getUserName(username);
        return ResponseEntity.ok(exists);
    }
    
    @PostMapping("/checkRoleName")
    public ResponseEntity<Boolean> checkRoleName(@RequestParam String name ) {
        boolean exists = userService.getRoleName(name);
        return ResponseEntity.ok(exists);
    }
    
    
    
    @GetMapping("/userPermission/editRole/{id}")
    @PreAuthorize("hasAuthority('EDIT')")
    public String editPermission(@PathVariable Long id,Model model) {
    	Role role=userService.findPermissionByid(id);
    	model.addAttribute("rolesDto",role);
    	return "editUserPermission";
    }
    
    @GetMapping("/userPermission/deleteRole/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public String deleteUserPermission(@PathVariable Long id) {
    	boolean status=userService.deleteUserPermission(id);
    	if (!status) {
            return "redirect:/userPermission?error"; 
        }
        return "redirect:/userPermission?deleteSuccess"; 
        
    }
    
    
    @GetMapping("/userPermission")
    @PreAuthorize("hasAuthority('CREATE')")
    public String userPermission(Model model) {
    	RolesDto roles=new RolesDto();
    	List<Role> role=userService.findAllRoles();
    	model.addAttribute("rolesDto",roles);
    	model.addAttribute("role",role);
    	return "userPermission";
    }

    
    
}
