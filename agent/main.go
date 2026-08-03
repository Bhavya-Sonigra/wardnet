package main

import (
	"bytes"
	"encoding/json"
	"log"
	"net/http"
	"time"

	"github.com/shirou/gopsutil/v3/cpu"
	"github.com/shirou/gopsutil/v3/disk"
	"github.com/shirou/gopsutil/v3/mem"
)

type HealthReport struct {
	ServerId                     string  `json:"serverId"`
	CpuPct                       float64 `json:"cpuPct"`
	MemPct                       float64 `json:"memPct"`
	DiskPct                      float64 `json:"diskPct"`
	ProcessAlive                 bool    `json:"processAlive"`
	LatencyToControlPlaneMs      int64   `json:"latencyToControlPlaneMs"`
	BytesSentSinceLastReport     int64   `json:"bytesSentSinceLastReport"`
	BytesReceivedSinceLastReport int64   `json:"bytesReceivedSinceLastReport"`
	ReportedAt                   string  `json:"reportedAt"`
}

const (
	controlPlaneURL = "http://localhost:8080/api/v1/reports"
	serverId        = "test-1" // TODO: make configurable via env var
	interval        = 10 * time.Second
)

func collectStats() HealthReport {
	cpuPercents, _ := cpu.Percent(time.Second, false)
	cpuPct := 0.0
	if len(cpuPercents) > 0 {
		cpuPct = cpuPercents[0]
	}

	vmStat, _ := mem.VirtualMemory()
	diskStat, _ := disk.Usage("/")

	return HealthReport{
		ServerId:                     serverId,
		CpuPct:                       cpuPct,
		MemPct:                       vmStat.UsedPercent,
		DiskPct:                      diskStat.UsedPercent,
		ProcessAlive:                 true,
		LatencyToControlPlaneMs:      0,
		BytesSentSinceLastReport:     0,
		BytesReceivedSinceLastReport: 0,
		ReportedAt:                   time.Now().UTC().Format(time.RFC3339),
	}
}

func sendReport(report HealthReport) {
	start := time.Now()
	body, err := json.Marshal(report)
	if err != nil {
		log.Println("failed to marshal report:", err)
		return
	}

	resp, err := http.Post(controlPlaneURL, "application/json", bytes.NewBuffer(body))

	if err != nil {
		log.Println("failed to send report:", err)
		return
	}
	defer resp.Body.Close()

	log.Printf("sent report, status=%d, latency=%dms\n", resp.StatusCode, time.Since(start).Milliseconds())
}

func main() {
	log.Println("Wardnet agent starting, reporting every", interval)
	ticker := time.NewTicker(interval)
	defer ticker.Stop()

	for {
		report := collectStats()
		sendReport(report)
		<-ticker.C
	}
}
