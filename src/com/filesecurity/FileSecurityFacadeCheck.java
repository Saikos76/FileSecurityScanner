
package com.filesecurity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileSecurityFacadeCheck {

    public static void main(String[] args) throws IOException {
        Path testDirectory = Files.createTempDirectory(
                "facade-check-"
        );

        try {
            Path quarantineDirectory =
                    testDirectory.resolve("quarantine");

            Path cleanFile = testDirectory.resolve("clean.txt");
            Files.writeString(cleanFile, "This is a clean demo file.");

            Path demoThreatFile =
                    testDirectory.resolve("demo-threat.txt");
            Files.createFile(demoThreatFile);

            FileSecurityFacade facade =
                    new FileSecurityFacade(quarantineDirectory);

            String cleanReport = facade.scanFile(cleanFile);

            if (cleanReport.contains("Status: CLEAN")
                    && Files.exists(cleanFile)) {
                System.out.println(
                        "PASS: Clean file scanned and preserved."
                );
            } else {
                System.out.println(
                        "FAIL: Clean file check failed."
                );
            }

            String threatReport = facade.scanFile(demoThreatFile);

            boolean quarantinedFileExists;

            try (Stream<Path> files = Files.list(quarantineDirectory)) {
                quarantinedFileExists = files.anyMatch(
                        Files::isRegularFile
                );
            }

            if (threatReport.contains("Status: THREAT_DETECTED")
                    && threatReport.contains("EMPTY_FILE_DEMO_SIGNATURE")
                    && !Files.exists(demoThreatFile)
                    && quarantinedFileExists) {
                System.out.println(
                        "PASS: Demo signature detected and file quarantined."
                );
            } else {
                System.out.println(
                        "FAIL: Demo threat check failed."
                );
            }

        } finally {
            try (Stream<Path> paths = Files.walk(testDirectory)) {
                paths.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException exception) {
                                throw new RuntimeException(exception);
                            }
                        });
            }
        }
    }
}
