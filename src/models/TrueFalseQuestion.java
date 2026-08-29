package models;

public class TrueFalseQuestion extends Question {

    private boolean correctAnswer;

    public TrueFalseQuestion(String questionId, String questionText, int points, boolean correctAnswer) {
        super(questionId, questionText, points);
        this.correctAnswer = correctAnswer;
    }

    public boolean checkAnswer(String givenAnswer) {
        if (givenAnswer == null || givenAnswer.trim().length() == 0) {
            return false;
        }
        
        String answer = givenAnswer.trim().toLowerCase();
        boolean givenAsBoolean = answer.equals("true") || answer.equals("t");
        return givenAsBoolean == correctAnswer;
    }

    public void display(int number) {
        System.out.println(number + ". " + getQuestionText() + " (" + getPoints() + " pt/s)");
        System.out.println("     [True or False]");
    }

    public String getQuestionType() {
        return "True/False";
    }
}
