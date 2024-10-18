package com.example.weatherbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
public class UserSettings {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "default_city")
    private String defaultCity;
}
