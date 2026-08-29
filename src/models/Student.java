package models;

import java.util.ArrayList;

public class Student extends User {

    private String studentId;
    private String learningStyle;
    private ArrayList<Subject> enrolledSubjects;
    private ArrayList<Flashcard> flashcards;
    private ArrayList<StudySession> studySessions;
    private ArrayList<String> quizTitles;
    private ArrayList<Integer> quizScores;

    public Student(String userId, String username, String password, String fullName, String email, String studentId) {
        super(userId, username, password, fullName, email);
        this.studentId = studentId;
        this.learningStyle = "Not assessed";
        enrolledSubjects = new ArrayList<Subject>();
        flashcards = new ArrayList<Flashcard>();
        studySessions = new ArrayList<StudySession>();
        quizTitles = new ArrayList<String>();
        quizScores = new ArrayList<Integer>();
    }

    public String getRole() {
        return "Student";
    }

    public String getStudentId() {
        return studentId;
    }

    public String getLearningStyle() {
        return learningStyle;
    }

    public void setLearningStyle(String learningStyle) {
        this.learningStyle = learningStyle;
    }

    public ArrayList<Subject> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public boolean isEnrolledIn(String subjectId) {
        for (int i = 0; i < enrolledSubjects.size(); i++) {
            if (enrolledSubjects.get(i).getSubjectId().equalsIgnoreCase(subjectId)) {
                return true;
            }
        }
        return false;
    }

    public void enrollInSubject(Subject subject) {
        if (!isEnrolledIn(subject.getSubjectId())) {
            enrolledSubjects.add(subject);
        }
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    public ArrayList<StudySession> getStudySessions() {
        return studySessions;
    }

    public void addStudySession(StudySession session) {
        studySessions.add(session);
    }

    public ArrayList<String> getQuizTitles() {
        return quizTitles;
    }

    public ArrayList<Integer> getQuizScores() {
        return quizScores;
    }

    public void recordQuizScore(String quizTitle, int scorePercent) {
        quizTitles.add(quizTitle);
        quizScores.add(scorePercent);
    }

    // Simple progress model: average of every recorded quiz score.
    public double getOverallProgress() {
        if (quizScores.isEmpty()) {
            return 0.0;
        }
        int total = 0;
        for (int i = 0; i < quizScores.size(); i++) {
            total = total + quizScores.get(i);
        }
        return (double) total / quizScores.size();
    }
}
