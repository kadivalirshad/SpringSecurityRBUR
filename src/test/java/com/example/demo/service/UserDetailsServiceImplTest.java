package com.example.demo.service;

import com.example.demo.dao.UserDetailsDoa;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @InjectMocks
    private userDetailsServiceImpl userDetailsService;

    @Mock
    private UserDetailsDoa userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        UserDto userDto = new UserDto();
        when(userDao.register(userDto)).thenReturn(true);

        boolean result = userDetailsService.register(userDto);

        assertTrue(result);
        verify(userDao).register(userDto);
    }

    @Test
    void testFindAllUsers() {
        List<Users> users = Collections.singletonList(new Users());
        when(userDao.findAllUsers()).thenReturn(users);

        List<Users> result = userDetailsService.findAllUsers();

        assertEquals(users, result);
        verify(userDao).findAllUsers();
    }

    @Test
    void testDeleteUser() {
        UUID userId = UUID.randomUUID();

        userDetailsService.deleteUser(userId);

        verify(userDao).deleteUser(userId);
    }

    @Test
    void testGetUserDetailsById() {
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        when(userDao.getUserDetailsById(userId)).thenReturn(user);

        Users result = userDetailsService.getUserDetailsById(userId);

        assertEquals(user, result);
        verify(userDao).getUserDetailsById(userId);
    }

    @Test
    void testGetUserName_Exists() {
        String userName = "existingUser";
        when(userDao.getUserName(userName)).thenReturn(true);

        boolean result = userDetailsService.getUserName(userName);

        assertTrue(result);
        verify(userDao).getUserName(userName);
    }

    @Test
    void testGetUserName_NotExists() {
        String userName = "nonExistingUser";
        when(userDao.getUserName(userName)).thenReturn(false);

        boolean result = userDetailsService.getUserName(userName);

        assertFalse(result);
        verify(userDao).getUserName(userName);
    }

    @Test
    void testLogin() {
        LoginDto loginDto = new LoginDto();
        String token = "dummy-token";
        when(userDao.login(loginDto)).thenReturn(token);

        String result = userDetailsService.login(loginDto);

        assertEquals(token, result);
        verify(userDao).login(loginDto);
    }

    @Test
    void testGetRoleName_Exists() {
        String roleName = "ROLE_USER";
        when(userDao.getRoleName(roleName)).thenReturn(true);

        boolean result = userDetailsService.getRoleName(roleName);

        assertTrue(result);
        verify(userDao).getRoleName(roleName);
    }

    @Test
    void testGetRoleName_NotExists() {
        String roleName = "ROLE_NON_EXISTING";
        when(userDao.getRoleName(roleName)).thenReturn(false);

        boolean result = userDetailsService.getRoleName(roleName);

        assertFalse(result);
        verify(userDao).getRoleName(roleName);
    }

    @Test
    void testFindAllRoles() {
        List<Role> roles = Collections.singletonList(new Role());
        when(userDao.findAllRoles()).thenReturn(roles);

        List<Role> result = userDetailsService.findAllRoles();

        assertEquals(roles, result);
        verify(userDao).findAllRoles();
    }
}
