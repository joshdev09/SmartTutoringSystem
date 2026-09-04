package exceptions;

// Thrown by StudentService.findSubjectById(...) and StudentService.enrollStudent(...)
// when a given subject ID does not exist in the subject catalog.
public class SubjectNotFoundException extends Exception {
    public SubjectNotFoundException(String message) {
        super(message);
    }
}
