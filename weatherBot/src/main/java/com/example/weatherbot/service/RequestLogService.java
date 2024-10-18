package com.example.weatherbot.service;

import com.example.weatherbot.entity.UserRequest;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.time.Instant;

public interface RequestLogService {

    void logRequest(String userId, String command, Instant requestTime, String payload);

    Page<UserRequest> getAllLogs(Pageable pageable, String startDate, String endDate);

    Page<UserRequest> getUserLogs(String userId, Pageable pageable, String startDate, String endDate);

    boolean findUserId(String userId);
}
