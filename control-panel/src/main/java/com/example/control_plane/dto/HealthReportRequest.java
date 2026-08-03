package com.example.control_plane.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;

public record HealthReportRequest(
        @NotBlank String serverId,

        @DecimalMin("0.0")
        @DecimalMax("100.0")
        double cpuPct,

        @DecimalMin("0.0")
        @DecimalMax("100.0")
        double memPct,

        @DecimalMin("0.0")
        @DecimalMax("100.0")
        double diskPct,

        boolean processAlive,

        @PositiveOrZero
        long latencyToControlPlaneMs,

        @PositiveOrZero
        long bytesSentSinceLastReport,

        @PositiveOrZero
        long bytesReceivedSinceLastReport,

        @NotBlank
        Instant reportedAt
) {}