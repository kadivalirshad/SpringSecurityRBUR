package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Role;
import com.example.demo.service.AdminService;

@Controller
@RequestMapping("/Admin")
public class AdminController {

	@Autowired
	private AdminService service;

	@PostMapping("/userPermission/save")
	@PreAuthorize("hasAuthority('CREATE')")
	public String registerUserRolesAndPermission(@ModelAttribute("rolesDto") RolesDto dto, BindingResult result) {
		if (result.hasErrors()) {
			return "userPermission";
		}
		boolean userRoles = service.registerUserRolesAndPermission(dto);
		if (!userRoles) {
			return "redirect:/Admin/userPermission?error";
		}
		return "redirect:/Admin/userPermission?success";
	}

	@GetMapping("/userPermission/editRole/{id}")
	@PreAuthorize("hasAuthority('EDIT')")
	public String editPermission(@PathVariable Long id, Model model) {
		Role role = service.findPermissionByid(id);
		model.addAttribute("rolesDto", role);
		return "editUserPermission";
	}

	@GetMapping("/userPermission/deleteRole/{id}")
	@PreAuthorize("hasAuthority('DELETE')")
	public String deleteUserPermission(@PathVariable Long id) {
		boolean status = service.deleteUserPermission(id);
		if (!status) {
			return "redirect:/Admin/userPermission?error";
		}
		return "redirect:/Admin/userPermission?deleteSuccess";

	}

	@GetMapping("/userPermission")
	@PreAuthorize("hasAuthority('CREATE')")
	public String userPermission(Model model) {
		RolesDto roles = new RolesDto();
		List<Role> role = service.findAllRoles();
		model.addAttribute("rolesDto", roles);
		model.addAttribute("role", role);
		return "userPermission";
	}
}
