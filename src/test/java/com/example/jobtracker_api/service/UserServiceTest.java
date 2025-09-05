package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.UserDTO;
import com.example.jobtracker_api.model.entity.User;
import com.example.jobtracker_api.model.mapper.UserMapper;
import com.example.jobtracker_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setFirstname("John");
        user1.setLastname("Doe");
        user1.setEmail("john@example.com");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setFirstname("Jane");
        user2.setLastname("Smith");
        user2.setEmail("jane@example.com");

        UserDTO dto1 = new UserDTO();
        dto1.setId(1L);
        dto1.setFirstname("John");
        dto1.setLastname("Doe");
        dto1.setEmail("john@example.com");

        UserDTO dto2 = new UserDTO();
        dto2.setId(2L);
        dto2.setFirstname("Jane");
        dto2.setLastname("Smith");
        dto2.setEmail("jane@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toDTO(user1)).thenReturn(dto1);
        when(userMapper.toDTO(user2)).thenReturn(dto2);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstname());
        assertEquals("Jane", result.get(1).getFirstname());

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toDTO(user1);
        verify(userMapper, times(1)).toDTO(user2);
    }

    @Test
    void testGetUserById_UserExists() {
        User user = new User();
        user.setUserId(1L);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john@example.com");

        UserDTO dto = new UserDTO();
        dto.setId(1L);
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setEmail("john@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(dto);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.getUserById(1L));

        assertEquals("User not found with id 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        UserDTO dto = new UserDTO();
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setEmail("john@example.com");

        User user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john@example.com");

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setFirstname("John");
        savedUser.setLastname("Doe");
        savedUser.setEmail("john@example.com");

        UserDTO savedDto = new UserDTO();
        savedDto.setId(1L);
        savedDto.setFirstname("John");
        savedDto.setLastname("Doe");
        savedDto.setEmail("john@example.com");

        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDTO(savedUser)).thenReturn(savedDto);

        UserDTO result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userMapper, times(1)).toEntity(dto);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(savedUser);
    }

    @Test
    void testUpdateUser_UserExists() {
        UserDTO dto = new UserDTO();
        dto.setFirstname("JohnUpdated");
        dto.setLastname("DoeUpdated");
        dto.setEmail("john.updated@example.com");

        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setFirstname("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john@example.com");

        User updatedUser = new User();
        updatedUser.setUserId(1L);
        updatedUser.setFirstname("JohnUpdated");
        updatedUser.setLastname("DoeUpdated");
        updatedUser.setEmail("john.updated@example.com");

        UserDTO updatedDto = new UserDTO();
        updatedDto.setId(1L);
        updatedDto.setFirstname("JohnUpdated");
        updatedDto.setLastname("DoeUpdated");
        updatedDto.setEmail("john.updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toDTO(updatedUser)).thenReturn(updatedDto);

        UserDTO result = userService.updateUser(1L, dto);

        assertNotNull(result);
        assertEquals("JohnUpdated", result.getFirstname());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
        verify(userMapper, times(1)).toDTO(updatedUser);
    }

    @Test
    void testDeleteUser_UserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.deleteUser(1L));

        assertEquals("User not found with id 1", exception.getMessage());
        verify(userRepository, times(1)).existsById(1L);
    }

}