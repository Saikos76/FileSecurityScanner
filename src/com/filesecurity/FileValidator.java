
package com.filesecurity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;

public class FileValidator {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            ".txt", ".pdf", ".jpg", ".png", ".docx"
    );

    public void validate(Path filePath) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException(
                    "File path must not be null."
            );
        }

        if (!Files.exists(filePath)) {
            throw new IOException(
                    "File does not exist: " + filePath
            );
        }

        if (!Files.isRegularFile(filePath)) {
            throw new IOException(
                    "The specified path is not a regular file: "
                            + filePath
            );
        }

        if (!Files.isReadable(filePath)) {
            throw new IOException(
                    "File is not readable: " + filePath
            );
        }

        String fileName = filePath.getFileName()
                .toString()
                .toLowerCase(Locale.ROOT);

        boolean supported = ALLOWED_EXTENSIONS.stream()
                .anyMatch(fileName::endsWith);

        if (!supported) {
            throw new IOException(
                    "Unsupported file extension: " + fileName
            );
        }

        long fileSize = Files.size(filePath);

        if (fileSize > MAX_FILE_SIZE) {
            throw new IOException(
                    "File exceeds the maximum size of 10 MB."
            );
        }
    }
}
