package services;

import models.Student;
import models.Teacher;
import models.User;
// import exceptions.InvalidLoginException;  TODO: uncomment once nagawa na exception

// Handles onboarding (registration) and login.
// Uses a fixed-size ARRAY OF OBJECTS to store accounts.
public class AuthService {

    private static final int MAX_USERS = 200;

    private User[] users;
    private int userCount;
    private int nextUserNumber;

    public AuthService() {
        users = new User[MAX_USERS];
        userCount = 0;
        nextUserNumber = 1;
    }

    public boolean usernameExists(String username) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public Student registerStudent(String fullName, String email, String username, String password) {
        String id = "STU-" + nextUserNumber;
        nextUserNumber++;
        Student student = new Student(id, username, password, fullName, email, id);
        users[userCount] = student;
        userCount++;
        return student;
    }

    public Teacher registerTeacher(String fullName, String email, String username, String password, String department) {
        String id = "TCH-" + nextUserNumber;
        nextUserNumber++;
        Teacher teacher = new Teacher(id, username, password, fullName, email, id, department);
        users[userCount] = teacher;
        userCount++;
        return teacher;
    }

    // TODO: this should throw InvalidLoginException once adds src/exceptions.
    // For now it just prints the reason and returns null so we can test the app already.
    public User login(String username, String password) /* throws InvalidLoginException */ {
        for (int i = 0; i < userCount; i++) {
            User u = users[i];
            if (u.getUsername().equalsIgnoreCase(username)) {
                if (u.checkPassword(password)) {
                    return u;
                }
                System.out.println("Incorrect password for username '" + username + "'.");
                // throw new InvalidLoginException("Incorrect password for username '" + username + "'.");
                return null;
            }
        }
        System.out.println("No account found with username '" + username + "'.");
        // throw new InvalidLoginException("No account found with username '" + username + "'.");
        return null;
    }

    public User[] getUsers() {
        return users;
    }

    public int getUserCount() {
        return userCount;
    }
}
