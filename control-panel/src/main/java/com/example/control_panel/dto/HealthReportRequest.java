package com.example.control_panel.dto;

public record HealthReportRequest(
        String serverId,
        double cpuPct,
        double memPct,
        double diskPct,
        boolean processAlive,
        String reportedAt
) {}