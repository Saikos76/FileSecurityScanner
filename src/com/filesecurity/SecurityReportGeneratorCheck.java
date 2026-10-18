
package com.filesecurity;

import java.nio.file.Path;

public class SecurityReportGeneratorCheck {

    public static void main(String[] args) {
        SecurityReportGenerator generator =
                new SecurityReportGenerator();

        String cleanReport = generator.generateReport(
                Path.of("samples/clean.txt"),
                "a".repeat(64),
                "CLEAN",
                null,
                null
        );

        if (cleanReport.contains("Status: CLEAN")
                && cleanReport.contains("SHA-256:")
                && !cleanReport.contains("Threat signature:")) {
            System.out.println("PASS: Clean report generated correctly.");
        } else {
            System.out.println("FAIL: Clean report is incorrect.");
        }

        String threatReport = generator.generateReport(
                Path.of("samples/demo-threat.txt"),
                "b".repeat(64),
                "THREAT_DETECTED",
                "DEMO_SIGNATURE",
                Path.of("quarantine/demo-threat.txt")
        );

        if (threatReport.contains("Status: THREAT_DETECTED")
                && threatReport.contains("DEMO_SIGNATURE")
                && threatReport.contains("Quarantine location:")) {
            System.out.println("PASS: Threat report generated correctly.");
        } else {
            System.out.println("FAIL: Threat report is incorrect.");
        }
    }
}
