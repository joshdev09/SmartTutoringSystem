package services;

import models.Student;
import models.Subject;
import utils.FileHandler;
import exceptions.FileExportException;

import java.util.ArrayList;

// Builds a grade report and writes it to a text file.
public class GradeExportService {

    public void exportGrades(ArrayList<Student> students, Subject subject, String filePath)
            throws FileExportException {
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

        FileHandler.writeLines(filePath, lines);
    }
}
