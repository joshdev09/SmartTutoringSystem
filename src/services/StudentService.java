package services;

import models.Quiz;
import models.Student;
import models.Subject;
import models.Teacher;
// import exceptions.SubjectNotFoundException; // TODO: uncomment once adds src/exceptions
// import exceptions.QuizSubmissionException;  // TODO: uncomment once adds src/exceptions

import java.util.Scanner;

// Handles subject browsing/enrollment, quiz-taking, and simple progress tips.
public class StudentService {

    private static final int MAX_SUBJECTS = 100;

    private Subject[] subjectCatalog;
    private int subjectCount;

    public StudentService() {
        subjectCatalog = new Subject[MAX_SUBJECTS];
        subjectCount = 0;
    }

    public void registerSubject(Subject subject) {
        if (subjectCount < subjectCatalog.length) {
            subjectCatalog[subjectCount] = subject;
            subjectCount++;
        }
    }

    public Subject[] getSubjectCatalog() {
        return subjectCatalog;
    }

    public int getSubjectCount() {
        return subjectCount;
    }

    // TODO: this should throw SubjectNotFoundException once teammate adds src/exceptions.
    // For now it just returns null when not found so we can test the app already.
    public Subject findSubjectById(String subjectId) /* throws SubjectNotFoundException */ {
        for (int i = 0; i < subjectCount; i++) {
            if (subjectCatalog[i].getSubjectId().equalsIgnoreCase(subjectId)) {
                return subjectCatalog[i];
            }
        }
        // throw new SubjectNotFoundException("No subject found with ID '" + subjectId + "'.");
        return null;
    }

    // Returns true if enrollment succeeded, false if the subject id was not found.
    public boolean enrollStudent(Student student, String subjectId, Teacher owningTeacher)
            /* throws SubjectNotFoundException */ {
        Subject subject = findSubjectById(subjectId);
        if (subject == null) {
            return false;
        }
        student.enrollInSubject(subject);
        if (owningTeacher != null) {
            owningTeacher.enrollStudent(student);
        }
        return true;
    }

    // Runs the quiz question by question in the console using polymorphism:
    // every Question knows how to display itself and check its own answer.
    // TODO: this should throw QuizSubmissionException once teammate adds src/exceptions.
    // For now it just returns -1 when the quiz has no questions.
    public int takeQuiz(Student student, Quiz quiz, Scanner input) /* throws QuizSubmissionException */ {
        if (quiz.getQuestionCount() == 0) {
            System.out.println("Quiz '" + quiz.getTitle() + "' has no questions yet.");
            // throw new QuizSubmissionException("Quiz '" + quiz.getTitle() + "' has no questions yet.");
            return -1;
        }

        String[] answers = new String[quiz.getQuestionCount()];
        System.out.println("\n--- " + quiz.getTitle() + " (" + quiz.getQuestionCount() + " item/s) ---");
        for (int i = 0; i < quiz.getQuestionCount(); i++) {
            quiz.getQuestions()[i].display(i + 1);
            System.out.print("Your answer: ");
            answers[i] = input.nextLine();
        }

        int score = quiz.grade(answers);
        student.recordQuizScore(quiz.getTitle(), score);
        return score;
    }

    // Any enrolled subject without a passing quiz score yet is suggested for review.
    public String recommendLessons(Student student) {
        String message = "";
        for (int i = 0; i < student.getEnrolledSubjects().size(); i++) {
            Subject subject = student.getEnrolledSubjects().get(i);
            boolean hasPassed = false;
            for (int j = 0; j < subject.getQuizzes().size(); j++) {
                Quiz quiz = subject.getQuizzes().get(j);
                int scoreIndex = student.getQuizTitles().indexOf(quiz.getTitle());
                if (scoreIndex != -1 && student.getQuizScores().get(scoreIndex) >= quiz.getPassingScorePercent()) {
                    hasPassed = true;
                }
            }
            if (!hasPassed) {
                message = message + "- Review \"" + subject.getSubjectName() + "\" and attempt its quiz.\n";
            }
        }
        if (message.length() == 0) {
            message = "Great job! You are passing every quiz attempted so far. Keep it up!\n";
        }
        return message;
    }
}
