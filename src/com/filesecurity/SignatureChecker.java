
package com.filesecurity;

import java.util.Map;
import java.util.Optional;

public class SignatureChecker {

    private final Map<String, String> knownThreats = Map.of(
            "e3b0c44298fc1c149afbf4c8996fb924"
                    + "27ae41e4649b934ca495991b7852b855",
            "EMPTY_FILE_DEMO_SIGNATURE"
    );

    public Optional<String> findThreat(String sha256) {
        if (sha256 == null || sha256.isBlank()) {
            throw new IllegalArgumentException(
                    "SHA-256 hash must not be empty."
            );
        }

        return Optional.ofNullable(knownThreats.get(
                sha256.toLowerCase(java.util.Locale.ROOT)
        ));
    }

    public boolean isThreat(String sha256) {
        return findThreat(sha256).isPresent();
    }
}
