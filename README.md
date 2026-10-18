# File Security Scanner

## Overview

File Security Scanner is a Java 17 console application developed to demonstrate the **Facade Design Pattern**.

The application combines several file-scanning operations behind a single, simplified interface. The client uses `FileSecurityFacade` instead of interacting with each subsystem class directly.

**Important:** This is an educational demonstration, not a production antivirus application. Its in-memory signature database contains a synthetic signature for an empty file.

## Features

- File validation before scanning.
- SHA-256 hash calculation.
- In-memory signature lookup.
- Quarantine of files matching the demonstration signature.
- Text-based scan reports.
- Console demonstration using dedicated sample files.

## Technologies

- Java 17
- IntelliJ IDEA
- Java NIO (`java.nio.file`)
- Java Security API (`java.security`)
- Git and GitHub

No external libraries or build tools are required.

## Design Pattern: Facade

The `FileSecurityFacade` class provides a simple public method:

```java
scanFile(Path path)
```

It coordinates the following subsystem classes:

| Class | Responsibility |
|---|---|
| `FileValidator` | Checks whether a file meets validation rules. |
| `HashCalculator` | Calculates the file's SHA-256 hash. |
| `SignatureChecker` | Searches for a matching known signature. |
| `QuarantineManager` | Moves a matching file into quarantine. |
| `SecurityReportGenerator` | Creates a text scan report. |

The client calls the facade, while the subsystem classes remain independent of the facade.

## Project Structure

```text
src/
└── com/
    └── filesecurity/
        ├── Main.java
        ├── FileSecurityFacade.java
        ├── FileValidator.java
        ├── HashCalculator.java
        ├── SignatureChecker.java
        ├── QuarantineManager.java
        └── SecurityReportGenerator.java
```

## How to Run

1. Open the project in IntelliJ IDEA.
2. Configure a Java 17 SDK.
3. Open `Main.java`.
4. Run the `main()` method.

The application creates demonstration files under `demo-data/samples`, scans them, prints reports, and moves the empty demonstration file into `demo-data/quarantine`.

The `demo-data/` directory is generated at runtime and excluded from version control.

## Clean Code Principles

The project demonstrates these principles:

1. **Single Responsibility Principle (SRP):** Each subsystem class has a focused responsibility.
2. **Meaningful Names:** Class and method names describe their purpose.
3. **Small, Focused Methods:** Operations are divided into manageable methods.
4. **Encapsulation:** Subsystem instances are kept private inside the facade.
5. **Separation of Concerns:** Validation, hashing, signature lookup, quarantine, and reporting are separated.
6. **Fail Fast:** Invalid inputs and unsupported files are rejected early.
7. **Avoid Duplication:** Shared responsibilities are implemented in dedicated classes.

## Limitations

- The signature database is in memory and contains only a synthetic demonstration signature.
- File extensions are checked, but file content is not fully validated against the claimed format.
- SHA-256 matching alone does not provide comprehensive malware detection.
- The application is intended for educational use, not production security.

## Author

GitHub: [Saikos76](https://github.com/Saikos76)