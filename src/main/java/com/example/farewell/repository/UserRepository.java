package com.example.farewell.repository;

import com.example.farewell.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    public User findByKakaoId(Long kakaoId);
    Optional<User> findById(Long id);

}
