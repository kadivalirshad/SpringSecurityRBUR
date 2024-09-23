package com.example.demo.controller;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.service.UserDetailsServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserDetailsServices userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        LoginDto loginDto = new LoginDto();
        String token = "dummy-token";
        when(userService.login(loginDto)).thenReturn(token);

        String viewName = userController.login(loginDto);

        assertEquals("/user/index", viewName);
        verify(userService).login(loginDto);
    }

    @Test
    void testRegister() {
        UserDto userDto = new UserDto();
        List<Role> roles = Collections.singletonList(new Role(null, "ROLE_USER", null, null));
        when(userService.findAllRoles()).thenReturn(roles);

        String viewName = userController.register(model);

        assertEquals("register", viewName);
        verify(model).addAttribute(eq("user"), any(UserDto.class));
        verify(model).addAttribute(eq("allrole"), any(List.class));
    }

    @Test
    void testEditUserDetails() {
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        user.setUser_id(userId);
        user.setRoles(Collections.singletonList(new Role(null, "ROLE_USER", null, null)));
        when(userService.getUserDetailsById(userId)).thenReturn(user);
        when(userService.findAllRoles()).thenReturn(Collections.singletonList(new Role(null, "ROLE_USER", null, null)));

        String viewName = userController.editUserDetails(userId, model);

        assertEquals("register", viewName);
        verify(model).addAttribute(eq("user"), eq(user));
    }

    @Test
    void testViewUserProfile() {
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        user.setUser_id(userId);
        user.setRoles(Collections.singletonList(new Role(null, "ROLE_USER", null, null)));
        when(userService.getUserDetailsById(userId)).thenReturn(user);

        String viewName = userController.viewUserProfile(userId, model);

        assertEquals("user", viewName);
        verify(model).addAttribute(eq("user"), eq(user));
    }

    @Test
    void testUserList() {
        List<Users> users = Collections.singletonList(new Users());
        when(userService.findAllUsers()).thenReturn(users);

        String viewName = userController.userList(model);

        assertEquals("UsersList", viewName);
        verify(model).addAttribute(eq("users"), eq(users));
    }

    @Test
    void testRegistration_Success() {
        UserDto userDto = new UserDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.register(userDto)).thenReturn(true);

        String viewName = userController.registration(userDto, bindingResult);

        assertEquals("redirect:/user/register?success", viewName);
    }

    @Test
    void testRegistration_Failure() {
        UserDto userDto = new UserDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.register(userDto)).thenReturn(false);

        String viewName = userController.registration(userDto, bindingResult);

        assertEquals("redirect:/user/register?error", viewName);
    }

    @Test
    void testDeleteUser() {
        UUID userId = UUID.randomUUID();
        String viewName = userController.deleteUser(userId);

        assertEquals("redirect:/user/users", viewName);
        verify(userService).deleteUser(userId);
    }

    @Test
    void testCheckUserName() {
        String username = "testUser";
        when(userService.getUserName(username)).thenReturn(true);

        ResponseEntity<Boolean> response = userController.checkUserName(username);

        assertEquals(ResponseEntity.ok(true), response);
    }

    @Test
    void testCheckRoleName() {
        String roleName = "ROLE_USER";
        when(userService.getRoleName(roleName)).thenReturn(true);

        ResponseEntity<Boolean> response = userController.checkRoleName(roleName);

        assertEquals(ResponseEntity.ok(true), response);
    }
}

