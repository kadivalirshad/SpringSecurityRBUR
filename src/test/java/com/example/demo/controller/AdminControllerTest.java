package com.example.demo.controller;

import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Role;
import com.example.demo.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService service;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserRolesAndPermission_Success() {
        RolesDto rolesDto = new RolesDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(service.registerUserRolesAndPermission(rolesDto)).thenReturn(true);

        String viewName = adminController.registerUserRolesAndPermission(rolesDto, bindingResult);

        assertEquals("redirect:/Admin/userPermission?success", viewName);
    }

    @Test
    void testRegisterUserRolesAndPermission_Failure() {
        RolesDto rolesDto = new RolesDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(service.registerUserRolesAndPermission(rolesDto)).thenReturn(false);

        String viewName = adminController.registerUserRolesAndPermission(rolesDto, bindingResult);

        assertEquals("redirect:/Admin/userPermission?error", viewName);
    }

    @Test
    void testUserPermission() {
        RolesDto rolesDto = new RolesDto();
        List<Role> roles = Collections.singletonList(new Role());
        when(service.findAllRoles()).thenReturn(roles);

        String viewName = adminController.userPermission(model);

        assertEquals("userPermission", viewName);
        verify(model).addAttribute(eq("rolesDto"), any(RolesDto.class));
        verify(model).addAttribute(eq("role"), eq(roles));
    }

    @Test
    void testEditPermission() {
        Long roleId = 1L;
        Role role = new Role();
        when(service.findPermissionByid(roleId)).thenReturn(role);

        String viewName = adminController.editPermission(roleId, model);

        assertEquals("editUserPermission", viewName);
        verify(model).addAttribute(eq("rolesDto"), eq(role));
    }

    @Test
    void testDeleteUserPermission_Success() {
        Long roleId = 1L;
        when(service.deleteUserPermission(roleId)).thenReturn(true);

        String viewName = adminController.deleteUserPermission(roleId);

        assertEquals("redirect:/Admin/userPermission?deleteSuccess", viewName);
        verify(service).deleteUserPermission(roleId);
    }

    @Test
    void testDeleteUserPermission_Failure() {
        Long roleId = 1L;
        when(service.deleteUserPermission(roleId)).thenReturn(false);

        String viewName = adminController.deleteUserPermission(roleId);

        assertEquals("redirect:/Admin/userPermission?error", viewName);
        verify(service).deleteUserPermission(roleId);
    }
}
