
package com.filesecurity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final Path DEMO_DIRECTORY =
            Path.of("demo-data", "samples");

    public static void main(String[] args) {
        try {
            runDemo();
        } catch (IOException exception) {
            System.err.println(
                    "Demo failed: " + exception.getMessage()
            );
        }
    }

    private static void runDemo() throws IOException {
        Files.createDirectories(DEMO_DIRECTORY);

        Path cleanFile = DEMO_DIRECTORY.resolve("clean-demo.txt");
        Path demoThreatFile =
                DEMO_DIRECTORY.resolve("demo-threat.txt");

        // Create or reset files used exclusively by this demo.
        Files.writeString(
                cleanFile,
                "This is a clean demonstration file."
        );

        Files.writeString(demoThreatFile, "");

        FileSecurityFacade facade = new FileSecurityFacade();

        System.out.println("FILE SECURITY SCANNER");
        System.out.println("=====================");

        System.out.println("\nScanning clean demo file...");
        System.out.println(facade.scanFile(cleanFile));

        System.out.println("\nScanning demo signature file...");
        System.out.println(facade.scanFile(demoThreatFile));

        System.out.println("\nDemo completed.");
        System.out.println(
                "Quarantine directory: demo-data/quarantine"
        );
    }
}
