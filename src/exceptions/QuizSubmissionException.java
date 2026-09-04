package exceptions;

// Thrown by StudentService.takeQuiz(...) when a quiz cannot be submitted,
// e.g. it has zero questions and there is nothing to answer.
public class QuizSubmissionException extends Exception {
    public QuizSubmissionException(String message) {
        super(message);
    }
}
