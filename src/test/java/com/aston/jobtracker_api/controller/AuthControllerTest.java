package com.aston.jobtracker_api.controller;

import com.aston.jobtracker_api.config.JwtProvider;
import com.aston.jobtracker_api.dto.auth.LoginRequest;
import com.aston.jobtracker_api.dto.auth.SignupRequest;
import com.aston.jobtracker_api.exception.TokenRefreshException;
import com.aston.jobtracker_api.model.RefreshToken;
import com.aston.jobtracker_api.model.User;
import com.aston.jobtracker_api.service.RefreshTokenService;
import com.aston.jobtracker_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserService userService;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private JwtProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAuthenticateUser() throws Exception {
        LoginRequest login = new LoginRequest();
        login.setEmail("user@example.com");
        login.setPassword("password123");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtProvider.createToken("user@example.com")).thenReturn("mock-jwt");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("mock-jwt"));
    }

    @Test
    void shouldRegisterUser() throws Exception {
        SignupRequest signup = new SignupRequest();
        signup.setEmail("user@example.com");
        signup.setPassword("password123");
        signup.setFirstName("John");
        signup.setLastName("Doe");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signup)))
                .andExpect(status().isOk());

        verify(userService, times(1)).registerNewUser(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void shouldLogoutUser() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                        .header("Authorization", "Bearer mock-token"))
                .andExpect(status().isOk());

        verify(jwtProvider).invalidate("mock-token");
    }

    @Test
    public void testRefreshToken_Success() throws Exception {
        String validToken = "valid-refresh-token";
        String newAccessToken = "new-access-token";
        String newRefreshToken = "new-refresh-token";

        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(validToken);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(3600));

        when(refreshTokenService.findByToken(validToken)).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
        when(jwtTokenProvider.createToken(user.getEmail())).thenReturn(newAccessToken);
        when(refreshTokenService.createRefreshToken(user)).thenReturn(refreshToken);

        String requestBody = "{\"refreshToken\":\"" + validToken + "\"}";

        mockMvc.perform(post("/api/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(newAccessToken))
                .andExpect(jsonPath("$.refreshToken").value(refreshToken.getToken()))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    public void testRefreshToken_InvalidToken() throws Exception {
        String invalidToken = "invalid-refresh-token";

        when(refreshTokenService.findByToken(invalidToken)).thenReturn(Optional.empty());

        String requestBody = "{\"refreshToken\":\"" + invalidToken + "\"}";

        mockMvc.perform(post("/api/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Refresh Token Error"))
                .andExpect(jsonPath("$.message").value("Refresh token is not in database!"));
    }

    @Test
    public void testRefreshToken_ExpiredToken() throws Exception {
        String expiredToken = "expired-refresh-token";

        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(expiredToken);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().minusSeconds(3600));

        when(refreshTokenService.findByToken(expiredToken)).thenReturn(Optional.of(refreshToken));
        // verifyExpiration throws exception
        Mockito.doThrow(new TokenRefreshException(expiredToken, "Refresh token was expired. Please make a new login request"))
                .when(refreshTokenService).verifyExpiration(refreshToken);

        String requestBody = "{\"refreshToken\":\"" + expiredToken + "\"}";

        mockMvc.perform(post("/api/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Refresh Token Error"))
                .andExpect(jsonPath("$.message").value("Refresh token was expired. Please make a new login request"));
    }
}