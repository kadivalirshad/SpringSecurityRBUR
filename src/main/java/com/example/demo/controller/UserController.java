package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.JwtAuthResponse;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.service.UserDetailsServices;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDetailsServices userService;

	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginDto loginDto) {
		String token = userService.login(loginDto);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		return "/user/index";
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
		List<String> role = userService.findAllRoles().stream().map(Role::getName).collect(Collectors.toList());
		model.addAttribute("user", user);
		model.addAttribute("allrole", role);
		model.addAttribute("roles", null);
		return "register";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('EDIT')")
	public String editUserDetails(@PathVariable UUID id, Model model) {
		Users user = userService.getUserDetailsById(id);
		List<String> selectedRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		List<String> allrole = userService.findAllRoles().stream().map(Role::getName).collect(Collectors.toList());
		model.addAttribute("user", user);
		model.addAttribute("roles", selectedRoles);
		model.addAttribute("allrole", allrole);
		return "register";
	}

	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('VIEW')")
	public String viewUserProfile(@PathVariable UUID id, Model model) {
		Users user = userService.getUserDetailsById(id);
		List<String> selectedRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		model.addAttribute("user", user);
		model.addAttribute("roles", selectedRoles);
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
			return "redirect:/user/register?error";
		}
		return "redirect:/user/register?success";
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('DELETE')")
	public String deleteUser(@PathVariable UUID id) {
		userService.deleteUser(id);
		return "redirect:/user/users";
	}

	@PostMapping("/checkUserName")
	public ResponseEntity<Boolean> checkUserName(@RequestParam String username) {
		boolean exists = userService.getUserName(username);
		return ResponseEntity.ok(exists);
	}

	@PostMapping("/checkRoleName")
	public ResponseEntity<Boolean> checkRoleName(@RequestParam String name) {
		boolean exists = userService.getRoleName(name);
		return ResponseEntity.ok(exists);
	}

}
