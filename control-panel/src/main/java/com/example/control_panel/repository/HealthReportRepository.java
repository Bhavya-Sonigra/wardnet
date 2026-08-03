package com.example.control_panel.repository;

import com.example.control_panel.model.HealthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HealthReportRepository extends JpaRepository<HealthReport, java.util.UUID> {
    List<HealthReport> findByServerIdOrderByReportedAtDesc(String serverId);
    Optional<HealthReport> findFirstByServerIdOrderByReportedAtDesc(String serverId);
}