package com.example.demo.Dao;
import com.example.demo.dao.UserDetailsDoa;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.service.userDetailsServiceImpl;

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
        userDto.setPassword("password");
        userDto.setRole_name(new String[]{"ROLE_USER"});

        when(userDao.register(any(UserDto.class))).thenReturn(true);

        boolean result = userDetailsService.register(userDto);

        assertTrue(result);
        verify(userDao).register(userDto);
    }

    @Test
    void testFindAllUsers() {
        Users user = new Users();
        user.setUsername("testUser");

        when(userDao.findAllUsers()).thenReturn(Collections.singletonList(user));

        List<Users> result = userDetailsService.findAllUsers();

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
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
    }

    @Test
    void testGetUserName_Exists() {
        String userName = "existingUser";
        when(userDao.getUserName(userName)).thenReturn(true);

        boolean result = userDetailsService.getUserName(userName);

        assertTrue(result);
    }

    @Test
    void testGetUserName_NotExists() {
        String userName = "nonExistingUser";
        when(userDao.getUserName(userName)).thenReturn(false);

        boolean result = userDetailsService.getUserName(userName);

        assertFalse(result);
    }

    @Test
    void testLogin() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("user");
        loginDto.setPassword("password");

        when(userDao.login(loginDto)).thenReturn("token");

        String token = userDetailsService.login(loginDto);

        assertEquals("token", token);
    }

    @Test
    void testGetRoleName_Exists() {
        String roleName = "ROLE_USER";
        when(userDao.getRoleName(roleName)).thenReturn(true);

        boolean result = userDetailsService.getRoleName(roleName);

        assertTrue(result);
    }

    @Test
    void testGetRoleName_NotExists() {
        String roleName = "ROLE_NON_EXISTING";
        when(userDao.getRoleName(roleName)).thenReturn(false);

        boolean result = userDetailsService.getRoleName(roleName);

        assertFalse(result);
    }

    @Test
    void testFindAllRoles() {
        Role role = new Role();
        role.setName("ROLE_USER");

        when(userDao.findAllRoles()).thenReturn(Collections.singletonList(role));

        List<Role> result = userDetailsService.findAllRoles();

        assertEquals(1, result.size());
        assertEquals("ROLE_USER", result.get(0).getName());
    }
}
