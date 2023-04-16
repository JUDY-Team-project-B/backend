package com.hangout.hangout.global;

import com.hangout.hangout.global.error.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/health-check")
@RestController
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.successResponse("스프링부트 실행");
    }
}