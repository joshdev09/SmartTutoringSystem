package models;

// Holds its questions in a fixed-size ARRAY OF OBJECTS (Question[]).
public class Quiz {

    private String quizId;
    private String title;
    private String subjectId;
    private Question[] questions;
    private int questionCount;
    private int passingScorePercent;

    public Quiz(String quizId, String title, String subjectId, int capacity, int passingScorePercent) {
        this.quizId = quizId;
        this.title = title;
        this.subjectId = subjectId;
        this.questions = new Question[capacity];
        this.questionCount = 0;
        this.passingScorePercent = passingScorePercent;
    }

    public String getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public int getPassingScorePercent() {
        return passingScorePercent;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public boolean addQuestion(Question question) {
        if (questionCount >= questions.length) {
            return false; 
        }
        questions[questionCount] = question;
        questionCount++;
        return true;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public int getTotalPoints() {
        int total = 0;
        for (int i = 0; i < questionCount; i++) {
            total = total + questions[i].getPoints();
        }
        return total;
    }

    // Grades the quiz and returns the score as a percentage.
    public int grade(String[] studentAnswers) {
        int totalPoints = getTotalPoints();
        if (totalPoints == 0) {
            return 0;
        }
        int earned = 0;
        for (int i = 0; i < questionCount; i++) {
            String answer = "";
            if (i < studentAnswers.length) {
                answer = studentAnswers[i];
            }
            if (questions[i].checkAnswer(answer)) {
                earned = earned + questions[i].getPoints();
            }
        }
        return (earned * 100) / totalPoints;
    }
}
