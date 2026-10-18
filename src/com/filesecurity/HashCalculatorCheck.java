
package com.filesecurity;

import java.nio.file.Files;
import java.nio.file.Path;

public class HashCalculatorCheck {

    public static void main(String[] args) throws Exception {
        HashCalculator calculator = new HashCalculator();

        Path firstFile = Files.createTempFile("hash-test-1-", ".txt");
        Path secondFile = Files.createTempFile("hash-test-2-", ".txt");

        try {
            Files.writeString(firstFile, "Hello Security Scanner");
            Files.writeString(secondFile, "Hello Security Scanner");

            String firstHash = calculator.calculateSha256(firstFile);
            String secondHash = calculator.calculateSha256(secondFile);

            System.out.println("First SHA-256:  " + firstHash);
            System.out.println("Second SHA-256: " + secondHash);

            if (firstHash.equals(secondHash) && firstHash.length() == 64) {
                System.out.println("PASS: Identical content produces identical SHA-256.");
            } else {
                System.out.println("FAIL: Hash calculation check failed.");
            }

            Files.writeString(secondFile, "Modified content");

            String modifiedHash = calculator.calculateSha256(secondFile);

            if (!firstHash.equals(modifiedHash)) {
                System.out.println("PASS: Modified content produces a different SHA-256.");
            } else {
                System.out.println("FAIL: Modified content was not detected.");
            }
        } finally {
            Files.deleteIfExists(firstFile);
            Files.deleteIfExists(secondFile);
        }
    }
}
