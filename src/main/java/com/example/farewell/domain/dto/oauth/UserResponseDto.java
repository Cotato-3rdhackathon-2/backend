package com.example.farewell.domain.dto.oauth;

import com.example.farewell.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String accessToken;
    private String refreshToken;
    private int exprTime;

    User user;
}
