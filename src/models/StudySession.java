package models;

public class StudySession {

    private String sessionId;
    private String subjectStudied;
    private int plannedMinutes;
    private boolean completed;

    public StudySession(String sessionId, String subjectStudied, int plannedMinutes) {
        this.sessionId = sessionId;
        this.subjectStudied = subjectStudied;
        this.plannedMinutes = plannedMinutes;
        this.completed = false;
    }

    public String getSubjectStudied() {
        return subjectStudied;
    }

    public int getPlannedMinutes() {
        return plannedMinutes;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void complete() {
        this.completed = true;
    }

    public String toString() {
        String status = "Incomplete";
        if (completed) {
            status = "Completed";
        }
        return subjectStudied + " | " + plannedMinutes + " min planned | " + status;
    }
}
