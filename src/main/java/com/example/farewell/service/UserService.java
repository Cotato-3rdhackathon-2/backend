package com.example.farewell.service;

import com.example.farewell.domain.dto.oauth.KaKaoProfile;
import com.example.farewell.domain.dto.oauth.KaKaoRequestDto;
import com.example.farewell.domain.dto.oauth.KaKaoResponseDto;
import com.example.farewell.domain.dto.oauth.OauthToken;
import com.example.farewell.domain.entity.User;
import com.example.farewell.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public OauthToken getAccessToken(KaKaoRequestDto kaKaoRequestDto) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "{74c210af74246db4c3b56ee7b5c471cf}");
        params.add("redirect_uri", "{}");


        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return oauthToken;

    }

    public String saveUser(KaKaoRequestDto kaKaoRequestDto) {
        KaKaoProfile kakaoProfile = findUser(kaKaoRequestDto.getAccessToken());

        User user = userRepository.findByEmail(kakaoProfile.getKakao_account().getEmail());

        if (user == null) {
            user = User.builder()
                    .kakaoId(kakaoProfile.getId())
                    .kakaoNickname(kakaoProfile.kakao_account.getProfile().getNickname())
                    .email(kakaoProfile.getKakao_account().getEmail())
                    .build();


            userRepository.save(user);
        }

        return "hi";


    }

    public KaKaoProfile findUser(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KaKaoProfile kakaoProfile = null;
        try {

            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KaKaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;


    }
}
