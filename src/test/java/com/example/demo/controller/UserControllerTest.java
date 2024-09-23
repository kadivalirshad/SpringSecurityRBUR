package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RolesDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.service.userDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private userDetailsServiceImpl userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        LoginDto loginDto = new LoginDto();
        String token = "dummyToken";
        when(userService.login(loginDto)).thenReturn(token);
        
        String result = userController.login(loginDto);
        
        assertEquals("/index", result);
        verify(userService).login(loginDto);
    }

    @Test
    public void testGetLoginPage() {
        String result = userController.login();
        assertEquals("login", result);
    }

    @Test
    public void testIndexPage() {
        String result = userController.index();
        assertEquals("index", result);
    }

    @Test
    public void testInvalidAccess() {
        String result = userController.invalidAccess();
        assertEquals("invalidAccess", result);
    }

    @Test
    public void testRegister() {
        when(userService.findAllRoles()).thenReturn(Collections.singletonList(new Role(null, "ROLE_USER", null)));
        
        String result = userController.register(model);
        
        verify(model).addAttribute(eq("user"), any(UserDto.class));
        verify(model).addAttribute(eq("allrole"), anyList());
        assertEquals("register", result);
    }

    @Test
    public void testEditUserDetails() {
        UUID id = UUID.randomUUID();
        Users user = new Users();
        when(userService.getUserDetailsById(id)).thenReturn(Optional.of(user));
        when(userService.findAllRoles()).thenReturn(Collections.singletonList(new Role(null, "ROLE_USER", null)));
        
        String result = userController.editUserDetails(id, model);
        
        verify(model).addAttribute(eq("user"), any(Users.class));
        assertEquals("register", result);
    }

    @Test
    public void testViewUserProfile() {
        UUID id = UUID.randomUUID();
        Users user = new Users();
        when(userService.getUserDetailsById(id)).thenReturn(Optional.of(user));
        
        String result = userController.viewUserProfile(id, model);
        
        verify(model).addAttribute(eq("user"), any(Users.class));
        assertEquals("user", result);
    }

    @Test
    public void testUserList() {
        when(userService.findAllUsers()).thenReturn(Collections.singletonList(new Users()));
        
        String result = userController.userList(model);
        
        verify(model).addAttribute(eq("users"), anyList());
        assertEquals("UsersList", result);
    }

    @Test
    public void testRegistration() {
        UserDto userDto = new UserDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.register(userDto)).thenReturn(true);
        
        String result = userController.registration(userDto, bindingResult);
        
        assertEquals("redirect:/register?success", result);
    }

    @Test
    public void testRegistrationWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        
        String result = userController.registration(new UserDto(), bindingResult);
        
        assertEquals("register", result);
    }

    @Test
    public void testDeleteUser() {
        UUID id = UUID.randomUUID();
        String result = userController.deleteUser(id);
        assertEquals("redirect:/users", result);
        verify(userService).deteleUser(id);
    }

    @Test
    public void testRegisterUserRolesAndPermission() {
        RolesDto rolesDto = new RolesDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.registerUserRolesAndPermission(rolesDto)).thenReturn(true);
        
        String result = userController.registerUserRolesAndPermission(rolesDto, bindingResult);
        
        assertEquals("redirect:/userPermission?success", result);
    }

    @Test
    public void testRegisterUserRolesAndPermissionWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        
        String result = userController.registerUserRolesAndPermission(new RolesDto(), bindingResult);
        
        assertEquals("userPermission", result);
    }

    @Test
    public void testCheckUserName() {
        String username = "testUser";
        when(userService.getUserName(username)).thenReturn(true);
        
        ResponseEntity<Boolean> response = userController.checkUserName(username);
        
        assertTrue(response.getBody());
    }

    @Test
    public void testCheckRoleName() {
        String roleName = "testRole";
        when(userService.getRoleName(roleName)).thenReturn(true);
        
        ResponseEntity<Boolean> response = userController.checkRoleName(roleName);
        
        assertTrue(response.getBody());
    }

    @Test
    public void testEditPermission() {
        Long id = 1L;
        Role role = new Role(id, "ROLE_USER", null);
        when(userService.findPermissionByid(id)).thenReturn(role);
        
        String result = userController.editPermission(id, model);
        
        verify(model).addAttribute(eq("rolesDto"), any(Role.class));
        assertEquals("editUserPermission", result);
    }

    @Test
    public void testDeleteUserPermission() {
        Long id = 1L;
        when(userService.deleteUserPermission(id)).thenReturn(true);
        
        String result = userController.deleteUserPermission(id);
        
        assertEquals("redirect:/userPermission?deleteSuccess", result);
    }

    @Test
    public void testDeleteUserPermissionFailure() {
        Long id = 1L;
        when(userService.deleteUserPermission(id)).thenReturn(false);
        
        String result = userController.deleteUserPermission(id);
        
        assertEquals("redirect:/userPermission?error", result);
    }

    @Test
    public void testUserPermission() {
        when(userService.findAllRoles()).thenReturn(Collections.singletonList(new Role(null, "ROLE_USER", null)));
        
        String result = userController.userPermission(model);
        
        verify(model).addAttribute(eq("rolesDto"), any(RolesDto.class));
        assertEquals("userPermission", result);
    }
}

