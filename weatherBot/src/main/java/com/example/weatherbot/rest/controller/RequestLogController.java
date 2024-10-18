package com.example.weatherbot.rest.controller;

import com.example.weatherbot.entity.UserRequest;
import com.example.weatherbot.service.RequestLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("api/v1/bot/logs")
@RequiredArgsConstructor
public class RequestLogController {

    private final RequestLogService requestLogService;

    @GetMapping
    public Page<UserRequest> getAllLogs(Pageable pageable,
                                        @RequestParam(required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
                                        @RequestParam(required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {
        return requestLogService.getAllLogs(pageable, startDate, endDate);
    }

    @GetMapping("/{userId}")
    public Page<UserRequest> getUserLogs(@PathVariable @Valid String userId,
                                         Pageable pageable,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {
        if (!requestLogService.findUserId(userId)) {
            throw new UserNotFoundException("Пользователь с ID " + userId + " не найден");
        }
        return requestLogService.getUserLogs(userId, pageable, startDate, endDate);
    }
}
