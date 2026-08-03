package com.example.control_panel.controller;

import com.example.control_panel.dto.HealthReportRequest;
import com.example.control_panel.model.HealthReport;
import com.example.control_panel.repository.HealthReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1")
public class HealthReportController {

    private static final Logger log = LoggerFactory.getLogger(HealthReportController.class);
    private final HealthReportRepository repository;

    public HealthReportController(HealthReportRepository repository) {
        this.repository = repository;
    }

    // TODO(security): no auth yet — add per-agent token validation before Month 4 hardening phase
    @PostMapping("/reports")
    public ResponseEntity<Void> receiveReport(@RequestBody HealthReportRequest report) {
        HealthReport entity = new HealthReport(
                report.serverId(),
                report.cpuPct(),
                report.memPct(),
                report.diskPct(),
                report.processAlive(),
                report.latencyToControlPlaneMs(),
                report.bytesSentSinceLastReport(),
                report.bytesReceivedSinceLastReport(),
                Instant.parse(report.reportedAt())
        );
        repository.save(entity);
        log.info("Saved health report for serverId={}", report.serverId());
        return ResponseEntity.ok().build();
    }
}