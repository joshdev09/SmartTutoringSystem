package models;

public class MultipleChoiceQuestion extends Question {

    private String[] choices; 
    private char correctAnswer;

    public MultipleChoiceQuestion(String questionId, String questionText, int points, String[] choices, char correctAnswer) {
        super(questionId, questionText, points);
        this.choices = choices;
        this.correctAnswer = Character.toUpperCase(correctAnswer);
    }

    public boolean checkAnswer(String givenAnswer) {
        if (givenAnswer == null || givenAnswer.trim().length() == 0) {
            return false;
        }
        char firstLetter = Character.toUpperCase(givenAnswer.trim().charAt(0));
        return firstLetter == correctAnswer;
    }

    public void display(int number) {
        System.out.println(number + ". " + getQuestionText() + " (" + getPoints() + " pt/s)");
        for (int i = 0; i < choices.length; i++) {
            System.out.println("     " + choices[i]);
        }
    }

    public String getQuestionType() {
        return "Multiple Choice";
    }
}
