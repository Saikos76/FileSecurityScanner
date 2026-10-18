
package com.filesecurity;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileValidatorCheck {

    public static void main(String[] args) throws Exception {
        FileValidator validator = new FileValidator();

        Path testFile = Files.createTempFile(
                "security-test-", ".txt"
        );

        try {
            Files.writeString(testFile, "Test file content");

            validator.validate(testFile);

            System.out.println("PASS: Valid file accepted.");

            try {
                validator.validate(testFile.resolveSibling(
                        "missing-file.txt"
                ));

                System.out.println(
                        "FAIL: Missing file was accepted."
                );
            } catch (java.io.IOException exception) {
                System.out.println(
                        "PASS: Missing file rejected."
                );
            }

            Path unsupportedFile = Files.createTempFile(
                    "security-test-", ".exe"
            );

            try {
                validator.validate(unsupportedFile);

                System.out.println(
                        "FAIL: Unsupported file was accepted."
                );
            } catch (java.io.IOException exception) {
                System.out.println(
                        "PASS: Unsupported extension rejected."
                );
            } finally {
                Files.deleteIfExists(unsupportedFile);
            }
        } finally {
            Files.deleteIfExists(testFile);
        }
    }
}
