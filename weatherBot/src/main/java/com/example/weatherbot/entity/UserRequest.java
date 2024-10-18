package com.example.weatherbot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "userId должен быть буквенно-цифровым")
    private String userId;

    @Column(name = "command", nullable = false)
    private String command;

    @Column(name = "timestamp", nullable = false)
    private Instant requestTime;

    @Column(name = "payload", nullable = false, length = 1000)
    private String payload;

}
