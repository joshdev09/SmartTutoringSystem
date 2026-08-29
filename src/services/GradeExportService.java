package services;

import models.Student;
import models.Subject;
// import utils.FileHandler;                  // TODO: uncomment once adds src/utils
// import exceptions.FileExportException;      // TODO: uncomment once adds src/exceptions

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Builds a grade report and writes it to a text file.
public class GradeExportService {

    // TODO: this should throw FileExportException and delegate the writing to
    // utils.FileHandler once teammate adds those files. For now it writes the
    // file directly and just prints an error if writing fails, so we can test
    // the app already.
    public boolean exportGrades(ArrayList<Student> students, Subject subject, String filePath)
            /* throws FileExportException */ {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("Grade Report - " + subject.getSubjectName() + " (" + subject.getSubjectId() + ")");
        lines.add("------------------------------------------------------------");

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            lines.add("Student: " + student.getFullName() + " (" + student.getStudentId() + ")");

            for (int j = 0; j < subject.getQuizzes().size(); j++) {
                String quizTitle = subject.getQuizzes().get(j).getTitle();
                int scoreIndex = student.getQuizTitles().indexOf(quizTitle);
                String scoreText = "Not attempted";
                if (scoreIndex != -1) {
                    scoreText = student.getQuizScores().get(scoreIndex) + "%";
                }
                lines.add("   - " + quizTitle + ": " + scoreText);
            }

            lines.add("   Overall Progress: " + student.getOverallProgress() + "%");
            lines.add("");
        }

        // FileHandler.writeLines(filePath, lines);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.newLine();
            }
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Could not write grades to file: " + filePath);
            // throw new FileExportException("Could not write grades to file: " + filePath, e);
            return false;
        }
    }
}
