package models;

import java.util.ArrayList;

public class Teacher extends Staff {

    private ArrayList<Student> enrolledStudents;
    private ArrayList<Subject> subjectsHandled;

    public Teacher(String userId, String username, String password, String fullName, String email, String staffId, String department) {
        super(userId, username, password, fullName, email, staffId, department);
        enrolledStudents = new ArrayList<Student>();
        subjectsHandled = new ArrayList<Subject>();
    }

    public String getRole() {
        return "Teacher";
    }

    public void addSubject(Subject subject) {
        subjectsHandled.add(subject);
    }

    public ArrayList<Subject> getSubjectsHandled() {
        return subjectsHandled;
    }

    public void enrollStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }
}
