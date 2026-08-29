package models;

public class Flashcard {

    private String cardId;
    private String subjectCategory;
    private String term;
    private String definition;
    private int timesReviewed;
    private boolean mastered;

    public Flashcard(String cardId, String subjectCategory, String term, String definition) {
        this.cardId = cardId;
        this.subjectCategory = subjectCategory;
        this.term = term;
        this.definition = definition;
        this.timesReviewed = 0;
        this.mastered = false;
    }

    public String getSubjectCategory() {
        return subjectCategory;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public int getTimesReviewed() {
        return timesReviewed;
    }

    public boolean isMastered() {
        return mastered;
    }

    public void review() {
        timesReviewed++;
    }

    public void markMastered() {
        mastered = true;
    }

    public String toString() {
        String status = "";
        if (mastered) {
            status = " [MASTERED]";
        }
        return "[" + subjectCategory + "] " + term + " -> " + definition
                + " (reviewed " + timesReviewed + " time/s)" + status;
    }
}
