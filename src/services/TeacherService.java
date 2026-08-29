package services;

import models.Quiz;
import models.Student;
import models.Subject;
import models.Teacher;

import java.util.ArrayList;

// Handles viewing enrollees, publishing materials, and setting quizzes.
public class TeacherService {

    public void publishMaterial(Subject subject, String material) {
        subject.publishMaterial(material);
    }

    public void setQuiz(Subject subject, Quiz quiz) {
        subject.addQuiz(quiz);
    }

    public ArrayList<Student> viewEnrolledStudents(Teacher teacher) {
        return teacher.getEnrolledStudents();
    }
}
