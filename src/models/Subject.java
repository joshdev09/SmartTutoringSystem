package models;

import java.util.ArrayList;

public class Subject {

    private String subjectId;
    private String subjectName;
    private String description;
    private String teacherName;
    private ArrayList <String> learningMaterials;
    private ArrayList <Quiz> quizzes;

    public Subject(String subjectId, String subjectName, String description, String teacherName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.description = description;
        this.teacherName = teacherName;
        learningMaterials = new ArrayList<String>();
        quizzes = new ArrayList<Quiz>();
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getDescription() {
        return description;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void publishMaterial(String material) {
        learningMaterials.add(material);
    }

    public ArrayList<String> getLearningMaterials() {
        return learningMaterials;
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    public String toString() {
        return subjectId + " - " + subjectName + " (" + teacherName + ")";
    }
}
