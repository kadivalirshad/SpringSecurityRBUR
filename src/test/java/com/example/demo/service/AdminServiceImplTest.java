package com.example.demo.service;

import com.example.demo.dao.AdminDetailsDao;
import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminDetailsDao adminDetailsDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserRolesAndPermission() {
        RolesDto rolesDto = new RolesDto();
        when(adminDetailsDao.registerUserRolesAndPermission(rolesDto)).thenReturn(true);

        boolean result = adminService.registerUserRolesAndPermission(rolesDto);

        assertTrue(result);
        verify(adminDetailsDao).registerUserRolesAndPermission(rolesDto);
    }

    @Test
    void testDeleteUserPermission_Success() {
        Long roleId = 1L;
        when(adminDetailsDao.deleteUserPermission(roleId)).thenReturn(true);

        boolean result = adminService.deleteUserPermission(roleId);

        assertTrue(result);
        verify(adminDetailsDao).deleteUserPermission(roleId);
    }

    @Test
    void testDeleteUserPermission_Failure() {
        Long roleId = 1L;
        when(adminDetailsDao.deleteUserPermission(roleId)).thenReturn(false);

        boolean result = adminService.deleteUserPermission(roleId);

        assertFalse(result);
        verify(adminDetailsDao).deleteUserPermission(roleId);
    }

    @Test
    void testFindPermissionById() {
        Long roleId = 1L;
        Role role = new Role();
        when(adminDetailsDao.findPermissionByid(roleId)).thenReturn(role);

        Role result = adminService.findPermissionByid(roleId);

        assertEquals(role, result);
        verify(adminDetailsDao).findPermissionByid(roleId);
    }

    @Test
    void testFindAllRoles() {
        List<Role> roles = Collections.singletonList(new Role());
        when(adminDetailsDao.findAllRoles()).thenReturn(roles);

        List<Role> result = adminService.findAllRoles();

        assertEquals(roles, result);
        verify(adminDetailsDao).findAllRoles();
    }
}
