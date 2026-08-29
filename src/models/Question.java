package models;

public abstract class Question {

    private String questionId;
    private String questionText;
    private int points;

    public Question(String questionId, String questionText, int points) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.points = points;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getPoints() {
        return points;
    }

    public abstract boolean checkAnswer(String givenAnswer);

    public abstract void display(int number);

    public abstract String getQuestionType();
}
