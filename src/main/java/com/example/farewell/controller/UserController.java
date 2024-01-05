package com.example.farewell.controller;


import com.example.farewell.domain.dto.ResponseDto;
import com.example.farewell.domain.dto.oauth.KaKaoRequestDto;
import com.example.farewell.domain.dto.oauth.OauthToken;
import com.example.farewell.domain.dto.oauth.UserResponseDto;
import com.example.farewell.domain.entity.User;
import com.example.farewell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @PostMapping("/oauth/token")
    public ResponseDto<?> getLogin(@RequestBody KaKaoRequestDto kaKaoRequestDto){

        return ResponseDto.success("Success!",userService.saveUser(kaKaoRequestDto));

    }
}
