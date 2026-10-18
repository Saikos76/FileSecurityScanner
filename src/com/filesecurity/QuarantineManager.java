
package com.filesecurity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class QuarantineManager {

    public Path quarantineFile(Path sourceFile, Path quarantineDirectory)
            throws IOException {

        if (sourceFile == null || quarantineDirectory == null) {
            throw new IllegalArgumentException(
                    "Source file and quarantine directory must not be null."
            );
        }

        Path source = sourceFile.toAbsolutePath().normalize();
        Path quarantine = quarantineDirectory.toAbsolutePath().normalize();

        if (!Files.isRegularFile(source)) {
            throw new IOException(
                    "Source must be an existing regular file: " + source
            );
        }

        if (!Files.isReadable(source)) {
            throw new IOException("Source file is not readable: " + source);
        }

        if (source.startsWith(quarantine)) {
            throw new IOException(
                    "File is already inside the quarantine directory."
            );
        }

        Files.createDirectories(quarantine);

        String originalName = source.getFileName().toString();
        String quarantinedName =
                UUID.randomUUID() + "_" + originalName;

        Path destination = quarantine.resolve(quarantinedName);

        return Files.move(
                source,
                destination,
                StandardCopyOption.ATOMIC_MOVE
        );
    }
}
