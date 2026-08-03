package com.example.control_panel.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "health_reports")
public class HealthReport {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String serverId;

    private double cpuPct;
    private double memPct;
    private double diskPct;
    private boolean processAlive;
    long latencyToControlPanelMs;
    long bytesSentSinceLastReport;
    long bytesReceivedSinceLastReport;

    @Column(nullable = false)
    private Instant reportedAt;

    protected HealthReport() {} // required by JPA

    public HealthReport(String serverId, double cpuPct, double memPct,
                        double diskPct, boolean processAlive, long latencyToControlPanelMs, long bytesSentSinceLastReport, long bytesReceivedSinceLastReport, Instant reportedAt) {
        this.serverId = serverId;
        this.cpuPct = cpuPct;
        this.memPct = memPct;
        this.diskPct = diskPct;
        this.processAlive = processAlive;
        this.latencyToControlPanelMs = latencyToControlPanelMs;
        this.bytesSentSinceLastReport = bytesSentSinceLastReport;
        this.bytesReceivedSinceLastReport = bytesReceivedSinceLastReport;
        this.reportedAt = reportedAt;
    }

    // getters only — this is a read-mostly record after creation
    public UUID getId() { return id; }
    public String getServerId() { return serverId; }
    public double getCpuPct() { return cpuPct; }
    public double getMemPct() { return memPct; }
    public double getDiskPct() { return diskPct; }
    public boolean isProcessAlive() { return processAlive; }
    public long getLatencyToControlPanelMs() { return latencyToControlPanelMs; }
    public long getBytesSentSinceLastReport() { return bytesSentSinceLastReport; }
    public long getBytesReceivedSinceLastReport() { return bytesReceivedSinceLastReport; }
    public Instant getReportedAt() { return reportedAt; }
}