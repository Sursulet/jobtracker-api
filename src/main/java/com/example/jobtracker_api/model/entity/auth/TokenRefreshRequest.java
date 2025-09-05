package com.example.jobtracker_api.model.entity.auth;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
