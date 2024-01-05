package com.example.farewell.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Farewell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farewell_id")
    private Long farewellId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "farewell_date")
    private LocalDateTime farewellDate;

    @Column(name = "target_name")
    private String targetName;

    @Column(name = "category")
    private String category;

}
