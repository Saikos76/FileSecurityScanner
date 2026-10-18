
package com.filesecurity;

import java.nio.file.Files;
import java.nio.file.Path;

public class QuarantineManagerCheck {

    public static void main(String[] args) throws Exception {
        Path workspace = Files.createTempDirectory("quarantine-test-");
        Path source = workspace.resolve("demo-threat.txt");
        Path quarantine = workspace.resolve("quarantine");

        try {
            Files.writeString(source, "Demo test content");

            QuarantineManager manager = new QuarantineManager();
            Path movedFile = manager.quarantineFile(source, quarantine);

            if (!Files.exists(source) && Files.exists(movedFile)) {
                System.out.println("PASS: File moved to quarantine.");
            } else {
                System.out.println("FAIL: File was not moved correctly.");
            }

            if (Files.readString(movedFile).equals("Demo test content")) {
                System.out.println("PASS: File content preserved.");
            } else {
                System.out.println("FAIL: File content changed.");
            }

            try {
                manager.quarantineFile(movedFile, quarantine);
                System.out.println("FAIL: Re-quarantine was allowed.");
            } catch (java.io.IOException exception) {
                System.out.println("PASS: Re-quarantine rejected.");
            }
        } finally {
            try (var paths = Files.walk(workspace)) {
                paths.sorted(java.util.Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (java.io.IOException exception) {
                                throw new java.io.UncheckedIOException(exception);
                            }
                        });
            }
        }
    }
}
