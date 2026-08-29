# TODO for teammate: `utils/` and `exceptions/` packages

Everything else in the project (models, services, Main.java) is done and
compiles against these two packages. They are intentionally left for you to
implement. Below are the **exact classes, package names, and method
signatures** the rest of the code already calls — match them exactly and
everything will compile and run with no other changes needed.

## `src/exceptions/` (package `exceptions;`)

### `InvalidLoginException.java`
Thrown by `AuthService.login(...)` when the username doesn't exist or the
password is wrong.
```java
package exceptions;

public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
}
```

### `SubjectNotFoundException.java`
Thrown by `StudentService.findSubjectById(...)` / `enrollStudent(...)` when a
subject ID doesn't exist in the catalog.
```java
package exceptions;

public class SubjectNotFoundException extends Exception {
    public SubjectNotFoundException(String message) {
        super(message);
    }
}
```

### `QuizSubmissionException.java`
Thrown by `StudentService.takeQuiz(...)` when a quiz can't be submitted
(e.g. it has zero questions).
```java
package exceptions;

public class QuizSubmissionException extends Exception {
    public QuizSubmissionException(String message) {
        super(message);
    }
}
```

### `FileExportException.java`
Thrown by `GradeExportService.exportGrades(...)` (wrapping any `IOException`
from `FileHandler`) when writing the grade file fails.
```java
package exceptions;

public class FileExportException extends Exception {
    public FileExportException(String message) {
        super(message);
    }

    public FileExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

## `src/utils/` (package `utils;`)

### `FileHandler.java`
Used by `GradeExportService.exportGrades(...)` as:
```java
FileHandler.writeLines(filePath, lines); // lines: List<String>
```
It must catch any I/O failure and re-throw it as `exceptions.FileExportException`
(don't let a raw `IOException` escape, since `GradeExportService` only
declares `FileExportException`). Suggested implementation:
```java
package utils;

import exceptions.FileExportException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileHandler {

    public static void writeLines(String filePath, List<String> lines) throws FileExportException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FileExportException("Could not write grades to file: " + filePath, e);
        }
    }

    // Optional, if TeacherService later needs to read grades back:
    // public static List<String> readLines(String filePath) throws IOException { ... }
}
```

Once both packages exist with these exact names/signatures, compile from the
project root with:
```
javac -d out $(find src -name "*.java")
java -cp out Main
```
(or the PowerShell equivalent: `Get-ChildItem -Recurse src -Filter *.java | % FullName | javac -d out @-` /
just compile via your IDE's Run button).
