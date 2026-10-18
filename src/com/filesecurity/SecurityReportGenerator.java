
package com.filesecurity;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SecurityReportGenerator {

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String generateReport(
            Path filePath,
            String sha256,
            String status,
            String threatName,
            Path quarantinePath
    ) {
        StringBuilder report = new StringBuilder();

        report.append("\n========== FILE SECURITY SCAN REPORT ==========\n");
        report.append("Timestamp: ")
                .append(LocalDateTime.now().format(TIMESTAMP_FORMAT))
                .append("\n");
        report.append("File: ").append(filePath).append("\n");
        report.append("SHA-256: ").append(
                sha256 == null ? "Not calculated" : sha256
        ).append("\n");
        report.append("Status: ").append(status).append("\n");

        if (threatName != null && !threatName.isBlank()) {
            report.append("Threat signature: ")
                    .append(threatName)
                    .append("\n");
        }

        if (quarantinePath != null) {
            report.append("Quarantine location: ")
                    .append(quarantinePath)
                    .append("\n");
        }

        report.append("===============================================\n");

        return report.toString();
    }
}
