package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.UserDTO;
import com.example.jobtracker_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers() throws Exception {
        UserDTO user1 = new UserDTO(1L, "John", "Doe", "john@example.com");
        UserDTO user2 = new UserDTO(2L, "Jane", "Smith", "jane@example.com");

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[1].firstname").value("Jane"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_UserExists() throws Exception {
        UserDTO user = new UserDTO(1L, "John", "Doe", "john@example.com");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new EntityNotFoundException("User not found with id 1"));

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id 1"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO inputDto = new UserDTO(null, "John", "Doe", "john@example.com");
        UserDTO savedDto = new UserDTO(1L, "John", "Doe", "john@example.com");

        when(userService.createUser(inputDto)).thenReturn(savedDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.firstname").value("John"));

        verify(userService, times(1)).createUser(inputDto);
    }

    @Test
    void testUpdateUser_UserExists() throws Exception {
        UserDTO inputDto = new UserDTO(null, "JohnUpdated", "DoeUpdated", "john.updated@example.com");
        UserDTO updatedDto = new UserDTO(1L, "JohnUpdated", "DoeUpdated", "john.updated@example.com");

        when(userService.updateUser(1L, inputDto)).thenReturn(updatedDto);

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("JohnUpdated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));

        verify(userService, times(1)).updateUser(1L, inputDto);
    }

    @Test
    void testDeleteUser_UserExists() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        doThrow(new EntityNotFoundException("User not found with id 1")).when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id 1"));

        verify(userService, times(1)).deleteUser(1L);
    }
}