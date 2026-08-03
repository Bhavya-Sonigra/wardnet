package com.example.control_panel.controller;

import com.example.control_panel.dto.HealthReportRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class HealthReportController {

    private static final Logger log = LoggerFactory.getLogger(HealthReportController.class);

    @PostMapping("/reports")
    public ResponseEntity<Void> receiveReport(@RequestBody HealthReportRequest report) {
        log.info("Received health report: serverId={}, cpu={}%, mem={}%, disk={}%, alive={}, reportedAt={}",
                report.serverId(), report.cpuPct(), report.memPct(), report.diskPct(),
                report.processAlive(), report.reportedAt());
        return ResponseEntity.ok().build();
    }
}