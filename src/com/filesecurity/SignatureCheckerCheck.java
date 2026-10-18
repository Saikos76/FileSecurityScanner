
package com.filesecurity;

public class SignatureCheckerCheck {

    public static void main(String[] args) {
        SignatureChecker checker = new SignatureChecker();

        String knownHash =
                "e3b0c44298fc1c149afbf4c8996fb924"
                        + "27ae41e4649b934ca495991b7852b855";

        String unknownHash = "a".repeat(64);

        if (checker.isThreat(knownHash)) {
            System.out.println("PASS: Known signature detected.");
        } else {
            System.out.println("FAIL: Known signature was not detected.");
        }

        if (!checker.isThreat(unknownHash)) {
            System.out.println("PASS: Unknown signature not detected.");
        } else {
            System.out.println("FAIL: Unknown signature matched.");
        }

        System.out.println(
                "Threat name: "
                        + checker.findThreat(knownHash).orElse("Not found")
        );
    }
}
