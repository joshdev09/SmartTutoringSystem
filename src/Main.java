import models.MultipleChoiceQuestion;
import models.Quiz;
import models.Student;
import models.Subject;
import models.Teacher;
import models.TrueFalseQuestion;
import models.IdentificationQuestion;
import models.User;

import services.AuthService;
import services.GradeExportService;
import services.StudentService;
import services.StudyToolService;
import services.TeacherService;

import exceptions.FileExportException;
import exceptions.InvalidLoginException;
import exceptions.QuizSubmissionException;
import exceptions.SubjectNotFoundException;

import java.util.Scanner;

// Entry point of the Smart Tutoring System. Pure Java, text-based console menus.
public class Main {

    private static Scanner input = new Scanner(System.in);

    private static AuthService authService = new AuthService();
    private static StudentService studentService = new StudentService();
    private static TeacherService teacherService = new TeacherService();
    private static StudyToolService studyToolService = new StudyToolService();
    private static GradeExportService gradeExportService = new GradeExportService();

    public static void main(String[] args) {
        seedDemoData();

        System.out.println("=================================================");
        System.out.println("   WELCOME TO THE SMART TUTORING SYSTEM (STS)");
        System.out.println("=================================================");

        boolean running = true;
        while (running) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Login");
            System.out.println("2. Register (Onboarding)");
            System.out.println("3. Exit");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleOnboarding();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        System.out.println("\nThank you for using the Smart Tutoring System. Goodbye!");
        input.close();
    }

    // ================= ONBOARDING / AUTH =================

    private static void handleOnboarding() {
        System.out.println("\n--- Onboarding ---");
        System.out.println("1. Student");
        System.out.println("2. Teacher");
        int roleChoice = readInt("Are you a student or a teacher? ");

        System.out.print("Full name: ");
        String fullName = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Choose a username: ");
        String username = input.nextLine();

        if (authService.usernameExists(username)) {
            System.out.println("That username is already taken. Please log in instead.");
            return;
        }

        System.out.print("Choose a password: ");
        String password = input.nextLine();

        if (roleChoice == 1) {
            Student student = authService.registerStudent(fullName, email, username, password);
            System.out.println("Student account created! Your ID is " + student.getStudentId());
        } else if (roleChoice == 2) {
            System.out.print("Department (e.g. School of Computing): ");
            String department = input.nextLine();
            Teacher teacher = authService.registerTeacher(fullName, email, username, password, department);
            System.out.println("Teacher account created! Your ID is " + teacher.getStaffId());
        } else {
            System.out.println("Invalid selection. Registration cancelled.");
        }
    }

    private static void handleLogin() {
        System.out.print("\nUsername: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        User user;
        try {
            user = authService.login(username, password);
        } catch (InvalidLoginException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("\nWelcome back, " + user.getFullName() + " (" + user.getRole() + ")!");

        // POLYMORPHISM: same login flow, different dashboard depending on the account type.
        if (user instanceof Student) {
            Student student = (Student) user;
            studentDashboard(student);
        } else if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            teacherDashboard(teacher);
        }
    }

    // ================= STUDENT DASHBOARD =================

    private static void studentDashboard(Student student) {
        boolean inDashboard = true;
        while (inDashboard) {
            System.out.println("\n===== STUDENT DASHBOARD (" + student.getFullName() + ") =====");
            System.out.println("1. Courses / Subjects (browse & enroll)");
            System.out.println("2. Learning Interface (materials + quiz)");
            System.out.println("3. Pomodoro Timer");
            System.out.println("4. Flashcards Page");
            System.out.println("5. Study Methods and Learning Strategies");
            System.out.println("6. View My Progress");
            System.out.println("7. Logout");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    browseAndEnrollSubjects(student);
                    break;
                case 2:
                    learningInterface(student);
                    break;
                case 3:
                    pomodoroMenu(student);
                    break;
                case 4:
                    flashcardsMenu(student);
                    break;
                case 5:
                    studyMethodsMenu();
                    break;
                case 6:
                    viewProgress(student);
                    break;
                case 7:
                    inDashboard = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void browseAndEnrollSubjects(Student student) {
        Subject[] catalog = studentService.getSubjectCatalog();
        int count = studentService.getSubjectCount();

        if (count == 0) {
            System.out.println("\nNo subjects are available yet.");
            return;
        }

        System.out.println("\n--- Available Subjects ---");
        for (int i = 0; i < count; i++) {
            String tag = "";
            if (student.isEnrolledIn(catalog[i].getSubjectId())) {
                tag = " [ENROLLED]";
            }
            System.out.println((i + 1) + ". " + catalog[i] + tag);
        }

        System.out.print("\nEnter the Subject ID to enroll (or press ENTER to go back): ");
        String subjectId = input.nextLine();
        if (subjectId.trim().length() == 0) {
            return;
        }

        Teacher owner = findTeacherOfSubject(subjectId);
        try {
            studentService.enrollStudent(student, subjectId, owner);
            System.out.println("Successfully enrolled!");
        } catch (SubjectNotFoundException e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        }
    }

    private static void learningInterface(Student student) {
        if (student.getEnrolledSubjects().isEmpty()) {
            System.out.println("\nYou are not enrolled in any subject yet. Enroll first!");
            return;
        }

        System.out.println("\n--- Your Subjects ---");
        for (int i = 0; i < student.getEnrolledSubjects().size(); i++) {
            System.out.println((i + 1) + ". " + student.getEnrolledSubjects().get(i));
        }
        int pick = readInt("Choose a subject number to open (0 to cancel): ");
        if (pick <= 0 || pick > student.getEnrolledSubjects().size()) {
            return;
        }
        Subject subject = student.getEnrolledSubjects().get(pick - 1);

        System.out.println("\n--- Learning Materials: " + subject.getSubjectName() + " ---");
        if (subject.getLearningMaterials().isEmpty()) {
            System.out.println("(No materials published yet.)");
        } else {
            for (int i = 0; i < subject.getLearningMaterials().size(); i++) {
                System.out.println("- " + subject.getLearningMaterials().get(i));
            }
        }

        if (subject.getQuizzes().isEmpty()) {
            System.out.println("\nNo quiz has been set for this subject yet.");
            return;
        }

        System.out.print("\nTake the quiz for this subject now? (y/n): ");
        if (!input.nextLine().equalsIgnoreCase("y")) {
            return;
        }

        Quiz quiz = subject.getQuizzes().get(0);
        int score;
        try {
            score = studentService.takeQuiz(student, quiz, input);
        } catch (QuizSubmissionException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("\nYou scored " + score + "%. Passing score is " + quiz.getPassingScorePercent() + "%.");
        if (score >= quiz.getPassingScorePercent()) {
            System.out.println("You passed!");
        } else {
            System.out.println("Keep practicing!");
        }
    }

    private static void pomodoroMenu(Student student) {
        System.out.print("\nSubject/topic to focus on: ");
        String subjectName = input.nextLine();
        int minutes = readInt("Session length in minutes (e.g. 25): ");
        if (minutes <= 0) {
            System.out.println("Session length must be positive.");
            return;
        }
        studyToolService.runPomodoroSession(student, subjectName, minutes);
    }

    private static void flashcardsMenu(Student student) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- Flashcards Page ---");
            System.out.println("1. Create a flashcard");
            System.out.println("2. Review my flashcards");
            System.out.println("3. Back");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    System.out.print("Subject/category: ");
                    String category = input.nextLine();
                    System.out.print("Term / question: ");
                    String term = input.nextLine();
                    System.out.print("Definition / answer: ");
                    String definition = input.nextLine();
                    studyToolService.createFlashcard(student, category, term, definition);
                    System.out.println("Flashcard added!");
                    break;
                case 2:
                    studyToolService.reviewFlashcards(student, input);
                    break;
                case 3:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void studyMethodsMenu() {
        System.out.println("\n--- Study Methods and Learning Strategies ---");
        System.out.println("Answer a few quick questions and we'll recommend a study method for you.");
        String recommendation = studyToolService.recommendStudyMethod(input);
        System.out.println("\nRecommended study method: " + recommendation);
    }

    private static void viewProgress(Student student) {
        System.out.println("\n--- Your Progress ---");
        if (student.getQuizTitles().isEmpty()) {
            System.out.println("No quizzes attempted yet.");
        } else {
            for (int i = 0; i < student.getQuizTitles().size(); i++) {
                System.out.println(student.getQuizTitles().get(i) + ": " + student.getQuizScores().get(i) + "%");
            }
        }
        System.out.println("Overall Progress: " + student.getOverallProgress() + "%");
        System.out.println("\nRecommended next steps:");
        System.out.print(studentService.recommendLessons(student));
    }

    // ================= TEACHER DASHBOARD =================

    private static void teacherDashboard(Teacher teacher) {
        boolean inDashboard = true;
        while (inDashboard) {
            System.out.println("\n===== TEACHER DASHBOARD (" + teacher.getFullName() + ") =====");
            System.out.println("1. View Enrolled Students");
            System.out.println("2. Publish Learning Materials");
            System.out.println("3. Set a Quiz");
            System.out.println("4. Grade and Export Grades to File");
            System.out.println("5. Logout");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    viewEnrolledStudents(teacher);
                    break;
                case 2:
                    publishMaterials(teacher);
                    break;
                case 3:
                    setQuiz(teacher);
                    break;
                case 4:
                    gradeAndExport(teacher);
                    break;
                case 5:
                    inDashboard = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void viewEnrolledStudents(Teacher teacher) {
        System.out.println("\n--- Enrolled Students ---");
        if (teacher.getEnrolledStudents().isEmpty()) {
            System.out.println("No students enrolled in any of your subjects yet.");
            return;
        }
        for (int i = 0; i < teacher.getEnrolledStudents().size(); i++) {
            Student s = teacher.getEnrolledStudents().get(i);
            System.out.println("- " + s.getFullName() + " (" + s.getStudentId()
                    + ") | Overall Progress: " + s.getOverallProgress() + "%");
        }
    }

    private static void publishMaterials(Teacher teacher) {
        Subject subject = pickHandledSubject(teacher);
        if (subject == null) {
            return;
        }
        System.out.print("Enter the material title/content to publish: ");
        String material = input.nextLine();
        teacherService.publishMaterial(subject, material);
        System.out.println("Material published to \"" + subject.getSubjectName() + "\".");
    }

    private static void setQuiz(Teacher teacher) {
        Subject subject = pickHandledSubject(teacher);
        if (subject == null) {
            return;
        }

        System.out.print("Quiz title: ");
        String title = input.nextLine();
        int numQuestions = readInt("How many questions will this quiz have? ");
        int passingScore = readInt("Passing score (%): ");
        if (numQuestions < 1) {
            numQuestions = 1;
        }

        Quiz quiz = new Quiz("QZ-" + subject.getSubjectId() + "-" + (subject.getQuizzes().size() + 1),
                title, subject.getSubjectId(), numQuestions, passingScore);

        for (int i = 1; i <= numQuestions; i++) {
            System.out.println("\nQuestion " + i + " of " + numQuestions);
            System.out.println("Type: 1) Multiple Choice  2) True/False  3) Identification");
            int type = readInt("Choose question type: ");

            System.out.print("Question text: ");
            String text = input.nextLine();
            int points = readInt("Points for this question: ");
            String qId = quiz.getQuizId() + "-Q" + i;

            if (type == 1) {
                String[] choices = new String[4];
                String[] labels = { "A", "B", "C", "D" };
                for (int c = 0; c < 4; c++) {
                    System.out.print("Choice " + labels[c] + ": ");
                    choices[c] = labels[c] + ") " + input.nextLine();
                }
                System.out.print("Correct choice letter (A/B/C/D): ");
                char correct = input.nextLine().trim().toUpperCase().charAt(0);
                quiz.addQuestion(new MultipleChoiceQuestion(qId, text, points, choices, correct));
            } else if (type == 2) {
                System.out.print("Correct answer (true/false): ");
                boolean correct = input.nextLine().trim().equalsIgnoreCase("true");
                quiz.addQuestion(new TrueFalseQuestion(qId, text, points, correct));
            } else if (type == 3) {
                System.out.print("Correct answer: ");
                String correct = input.nextLine();
                quiz.addQuestion(new IdentificationQuestion(qId, text, points, correct));
            } else {
                System.out.println("Invalid type, skipping this question.");
            }
        }

        teacherService.setQuiz(subject, quiz);
        System.out.println("\nQuiz \"" + title + "\" added to \"" + subject.getSubjectName() + "\".");
    }

    private static void gradeAndExport(Teacher teacher) {
        Subject subject = pickHandledSubject(teacher);
        if (subject == null) {
            return;
        }
        if (teacher.getEnrolledStudents().isEmpty()) {
            System.out.println("No students enrolled to export grades for.");
            return;
        }

        System.out.print("File path to export grades to (e.g. grades_" + subject.getSubjectId() + ".txt): ");
        String path = input.nextLine();
        if (path.trim().length() == 0) {
            path = "grades_" + subject.getSubjectId() + ".txt";
        }

        try {
            gradeExportService.exportGrades(teacher.getEnrolledStudents(), subject, path);
            System.out.println("Grades exported successfully to " + path);
        } catch (FileExportException e) {
            System.out.println("Failed to export grades: " + e.getMessage());
        }
    }

    private static Subject pickHandledSubject(Teacher teacher) {
        if (teacher.getSubjectsHandled().isEmpty()) {
            System.out.println("\nYou have no subjects yet.");
            return null;
        }
        System.out.println("\n--- Your Subjects ---");
        for (int i = 0; i < teacher.getSubjectsHandled().size(); i++) {
            System.out.println((i + 1) + ". " + teacher.getSubjectsHandled().get(i));
        }
        int pick = readInt("Choose a subject number (0 to cancel): ");
        if (pick <= 0 || pick > teacher.getSubjectsHandled().size()) {
            return null;
        }
        return teacher.getSubjectsHandled().get(pick - 1);
    }

    // ================= HELPERS =================

    private static Teacher findTeacherOfSubject(String subjectId) {
        User[] users = authService.getUsers();
        for (int i = 0; i < authService.getUserCount(); i++) {
            if (users[i] instanceof Teacher) {
                Teacher teacher = (Teacher) users[i];
                for (int j = 0; j < teacher.getSubjectsHandled().size(); j++) {
                    if (teacher.getSubjectsHandled().get(j).getSubjectId().equalsIgnoreCase(subjectId)) {
                        return teacher;
                    }
                }
            }
        }
        return null;
    }

    // Reads an integer safely, re-prompting on invalid (non-numeric) input.
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // ================= DEMO DATA =================
    // Seeds one teacher + one subject + one quiz so the app can be tried right away.

    private static void seedDemoData() {
        Teacher demoTeacher = authService.registerTeacher(
                "Juan Dela Cruz", "juan.delacruz@sts.edu", "teacher1", "pass123", "Mathematics");

        Subject math = new Subject("SUB-101", "Basic Algebra", "Introductory algebra concepts.", demoTeacher.getFullName());
        math.publishMaterial("Lesson 1: Variables and Expressions (see handout).");
        math.publishMaterial("Lesson 2: Solving Linear Equations.");

        Quiz algebraQuiz = new Quiz("QZ-SUB-101-1", "Algebra Basics Quiz", math.getSubjectId(), 3, 60);
        algebraQuiz.addQuestion(new MultipleChoiceQuestion(
                "Q1", "What is the value of x in x + 2 = 5?", 1,
                new String[] { "A) 1", "B) 2", "C) 3", "D) 4" }, 'C'));
        algebraQuiz.addQuestion(new TrueFalseQuestion(
                "Q2", "A variable can only represent one fixed number forever.", 1, false));
        algebraQuiz.addQuestion(new IdentificationQuestion(
                "Q3", "What do we call a letter used to represent an unknown number?", 1, "variable"));
        math.addQuiz(algebraQuiz);

        demoTeacher.addSubject(math);
        studentService.registerSubject(math);

        System.out.println("(Demo teacher account ready -> username: teacher1 | password: pass123)");
    }
}