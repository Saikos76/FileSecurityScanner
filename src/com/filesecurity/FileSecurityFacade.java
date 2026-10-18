
package com.filesecurity;

import java.io.IOException;
import java.nio.file.Path;

public class FileSecurityFacade {

    private static final Path DEFAULT_QUARANTINE_DIRECTORY =
            Path.of("demo-data", "quarantine");

    private final FileValidator fileValidator;
    private final HashCalculator hashCalculator;
    private final SignatureChecker signatureChecker;
    private final QuarantineManager quarantineManager;
    private final SecurityReportGenerator reportGenerator;
    private final Path quarantineDirectory;

    public FileSecurityFacade() {
        this(DEFAULT_QUARANTINE_DIRECTORY);
    }

    public FileSecurityFacade(Path quarantineDirectory) {
        if (quarantineDirectory == null) {
            throw new IllegalArgumentException(
                    "Quarantine directory must not be null."
            );
        }

        this.quarantineDirectory = quarantineDirectory;

        this.fileValidator = new FileValidator();
        this.hashCalculator = new HashCalculator();
        this.signatureChecker = new SignatureChecker();
        this.quarantineManager = new QuarantineManager();
        this.reportGenerator = new SecurityReportGenerator();
    }

    public String scanFile(Path path) throws IOException {
        fileValidator.validate(path);

        Path normalizedPath = path.toAbsolutePath().normalize();

        String sha256 = hashCalculator.calculateSha256(normalizedPath);

        String threatName = signatureChecker
                .findThreat(sha256)
                .orElse(null);

        String status = "CLEAN";
        Path quarantinedPath = null;

        if (threatName != null) {
            status = "THREAT_DETECTED";

            quarantinedPath = quarantineManager.quarantineFile(
                    normalizedPath,
                    quarantineDirectory
            );
        }

        return reportGenerator.generateReport(
                normalizedPath,
                sha256,
                status,
                threatName,
                quarantinedPath
        );
    }
}
