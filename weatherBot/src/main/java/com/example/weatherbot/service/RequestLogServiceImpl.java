package com.example.weatherbot.service;

import com.example.weatherbot.entity.UserRequest;
import com.example.weatherbot.repository.UserRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class RequestLogServiceImpl implements RequestLogService {

    private final UserRequestRepository userRequestRepository;

    @Override
    public void logRequest(String userId, String command, Instant requestTime, String payload) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(userId);
        userRequest.setCommand(command);
        userRequest.setRequestTime(requestTime);
        userRequest.setPayload(payload);

        userRequestRepository.save(userRequest);
    }

    @Override
    public Page<UserRequest> getAllLogs(Pageable pageable, String startDate, String endDate) {
        Instant start = startDate != null ? parseDateTime(startDate, true) : null;
        Instant end = endDate != null ? parseDateTime(endDate, false) : null;
        return userRequestRepository.findAllByRequestTimeBetween(start, end, pageable);
    }

    @Override
    public Page<UserRequest> getUserLogs(String userId, Pageable pageable, String startDate, String endDate) {
        Instant start = startDate != null ? parseDateTime(startDate, true) : null;
        Instant end = endDate != null ? parseDateTime(endDate, false) : null;
        return userRequestRepository.findByUserIdAndRequestTimeBetween(userId, start, end, pageable);
    }

    @Override
    public boolean findUserId(String userId) {
        return userRequestRepository.existsById(Long.valueOf(userId));
    }

    private Instant parseDateTime(String dateTime, boolean isStartOfDay) {
        dateTime = dateTime.replace("\"", "");

        if (dateTime.length() == 10) {
            dateTime += isStartOfDay ? "T00:00:00" : "T23:59:59";
        }
        return LocalDateTime.parse(dateTime).atZone(ZoneId.systemDefault()).toInstant();
    }
}
