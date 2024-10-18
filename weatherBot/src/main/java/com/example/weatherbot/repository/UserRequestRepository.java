package com.example.weatherbot.repository;

import com.example.weatherbot.entity.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.Instant;

public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {

    Page<UserRequest> findAllByRequestTimeBetween(Instant startDate, Instant endDate, Pageable pageable);

    Page<UserRequest> findByUserIdAndRequestTimeBetween(String userId, Instant startDate, Instant endDate, Pageable pageable);
}
