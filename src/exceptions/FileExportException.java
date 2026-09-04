package exceptions;

// Thrown by GradeExportService.exportGrades(...) when writing the grade
// report to a file fails. This wraps whatever low-level I/O error caused
// the failure (see the FileHandler.writeLines(...) method in utils/).
public class FileExportException extends Exception {

    // Use this constructor when there is just a message and no underlying cause.
    public FileExportException(String message) {
        super(message);
    }

    // Use this constructor when this exception is wrapping another exception
    // (e.g. an IOException) so the original cause is not lost.
    public FileExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
