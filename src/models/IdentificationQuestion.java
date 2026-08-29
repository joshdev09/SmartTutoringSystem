package models;

public class IdentificationQuestion extends Question {

    private String correctAnswer;

    public IdentificationQuestion(String questionId, String questionText, int points, String correctAnswer) {
        super(questionId, questionText, points);
        this.correctAnswer = correctAnswer;
    }

    public boolean checkAnswer(String givenAnswer) {
        if (givenAnswer == null) {
            return false;
        }
        return givenAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }

    public void display(int number) {
        System.out.println(number + ". " + getQuestionText() + " (" + getPoints() + " pt/s)");
        System.out.println("     [Identification]");
    }

    public String getQuestionType() {
        return "Identification";
    }
}
