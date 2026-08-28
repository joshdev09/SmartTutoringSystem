# Folder Structure

SmartTutoringSystem/
└── src/
    ├── Main.java                         (Entry point, contains Scanner and text-based menus)
    ├── models/
    │   ├── User.java                     (Base class)
    │   ├── Staff.java                    (Inherits User - for multi-level)
    │   ├── Student.java                  (Inherits User)
    │   ├── Teacher.java                  (Inherits Staff)
    │   ├── Subject.java                  (Holds lesson content/materials)
    │   ├── Quiz.java                     (Holds questions and scores)
    │   ├── Question.java                 (Individual question object)
    │   ├── Flashcard.java                (Custom flashcards for memory skills)
    │   └── StudySession.java             (Tracks Pomodoro focus time)
    ├── services/
    │   ├── AuthService.java              (Handles login & onboarding logic)
    │   ├── StudentService.java           (Enrolling, taking quizzes, progress)
    │   ├── TeacherService.java           (Publishing materials, grading students)
    │   ├── StudyToolService.java         (Logic for Pomodoro, Flashcards, and Study Methods)
    │   └── GradeExportService.java       (Handles extracting grades)
    ├── utils/
    │   └── FileHandler.java              (Java File I/O for reading/writing grade text files)
    └── exceptions/
        ├── InvalidLoginException.java
        ├── SubjectNotFoundException.java
        ├── QuizSubmissionException.java
        └── FileExportException.java      (Handles errors during grade extraction/file writing)