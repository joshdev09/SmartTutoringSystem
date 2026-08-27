# OOP Requirements, base to dun sa guidelines then nag lagay ako sample for implementation dun sa system.

| :--------------------: | :------------------------------------------------------------------: |
| Requirement            | Implementation in Smart Tutoring System                              |
| :--------------------: | :------------------------------------------------------------------: |
| **Encapsulation**      | Use `private` fields with `getter` and `setter` methods with         |
|                        | validation across all domain models (e.g., validating email format,  |
|                        | score ranges).                                                       |
|                        |                                                                      |
| **Inheritance**        | Multi-level Inheritance: `User` -> `Staff` -> `Teacher`              |
|                        | Hierarchical: `User` -> `Student` and `User` -> `Teacher`.           |
|                        |                                                                      |
| **Polymorphism**       | Overriding: Abstract method `displayDashboard()` in `User` overridden| 
|                        | differently by `Student` and `Teacher`.                              |
|                        |                                                                      |
|                        | Overloading: `submitQuiz(Quiz quiz)` and `submitQuiz(Quiz quiz, int  |
|                        | timeSpent)`.                                                         |
|                        |                                                                      |
| **Exception Handling** | Custom exceptions like `InvalidLoginException`,                      |
|                        | `SubjectNotFoundException`, and `QuizTimeLimitExceededException`     |
|                        |  handled using `try-catch-finally`.                                  |    
|                        |                                                                      |
| **Array of Objects**   | `Student[] enrolledStudents` in `Teacher`, `Subject[] subjectsList`  |
|                        |  in `Student`, or `Question[] questions` inside `Quiz`.              |        
| :--------------------: | :------------------------------------------------------------------: |

# Proposed Folder Structure for backend [May change if the features are finalized]

backend/
└── src/
    ├── Main.java
    ├── models/
    │   ├── User.java                     (Base class)
    │   ├── Staff.java                    (Inherits User - for multi-level)
    │   ├── Student.java                  (Inherits User)
    │   ├── Teacher.java                  (Inherits Staff)
    │   ├── Subject.java                  (Holds lesson content/materials)
    │   ├── Quiz.java                     (Holds questions and scores)
    │   └── Question.java                 (Individual question object)
    ├── services/
    │   ├── AuthService.java              (Handles login & onboarding)
    │   ├── StudentService.java          (Enrolling, taking quizzes, progress)
    │   └── AnalyticsService.java         (Teacher stats & data visuals backend)
    └── exceptions/
        ├── InvalidLoginException.java
        ├── SubjectNotFoundException.java
        └── QuizSubmissionException.java