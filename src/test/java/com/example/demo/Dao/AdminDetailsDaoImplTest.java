package com.example.demo.Dao;

import com.example.demo.dao.AdminDetailsDaoImpl;
import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Permissions;
import com.example.demo.entity.Role;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminDetailsDaoImplTest {

    @InjectMocks
    private AdminDetailsDaoImpl adminDetailsDao;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private RoleRepository rolerepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserRolesAndPermission() {
        RolesDto rolesDto = new RolesDto();
        rolesDto.setName("ROLE_ADMIN");
        rolesDto.setPermission_name(new String[]{"PERMISSION_1", "PERMISSION_2"});

        Permissions permission1 = new Permissions();
        permission1.setName("PERMISSION_1");

        Permissions permission2 = new Permissions();
        permission2.setName("PERMISSION_2");

        when(permissionRepository.findByName("PERMISSION_1")).thenReturn(null);
        when(permissionRepository.findByName("PERMISSION_2")).thenReturn(permission2);
        when(rolerepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = adminDetailsDao.registerUserRolesAndPermission(rolesDto);

        assertTrue(result);
        verify(permissionRepository, times(1)).save(any(Permissions.class));
        verify(rolerepository).save(any(Role.class));
    }

    @Test
    void testFindAllRoles() {
        Role role = new Role();
        role.setName("ROLE_USER");

        when(rolerepository.findAll()).thenReturn(Collections.singletonList(role));

        var result = adminDetailsDao.findAllRoles();

        assertEquals(1, result.size());
        assertEquals("ROLE_USER", result.get(0).getName());
    }

    @Test
    void testDeleteUserPermission() {
        Long roleId = 1L;

        doNothing().when(rolerepository).deleteById(roleId);

        boolean result = adminDetailsDao.deleteUserPermission(roleId);

        assertTrue(result);
        verify(rolerepository).deleteById(roleId);
    }

    @Test
    void testFindPermissionById_Found() {
        Long roleId = 1L;
        Role role = new Role();
        role.setRole_id(roleId);
        when(rolerepository.findById(roleId)).thenReturn(Optional.of(role));

        Role result = adminDetailsDao.findPermissionByid(roleId);

        assertEquals(roleId, result.getRole_id());
    }

    @Test
    void testFindPermissionById_NotFound() {
        Long roleId = 1L;
        when(rolerepository.findById(roleId)).thenReturn(Optional.empty());

        Role result = adminDetailsDao.findPermissionByid(roleId);

        assertNotNull(result);
        assertEquals(0, result.getRole_id()); // Assuming that a new Role has id = 0 by default
    }
}
