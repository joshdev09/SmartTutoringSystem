package utils;

import exceptions.FileExportException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Small utility class for writing text files, used by GradeExportService
// to save a grade report to disk.
public class FileHandler {

    // Writes each String in "lines" to its own line in the file at filePath.
    // Any low-level I/O problem (bad path, no write permission, disk full,
    // etc.) is caught here and re-thrown as a FileExportException so callers
    // only ever have to deal with one, project-specific exception type
    // instead of the generic java.io.IOException.
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
}
