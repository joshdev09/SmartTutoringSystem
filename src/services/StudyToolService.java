package services;

import models.Flashcard;
import models.Student;
import models.StudySession;

import java.util.Scanner;

// Logic for the Pomodoro timer, Flashcards, and Study Methods pages.
public class StudyToolService {

    private int sessionCounter = 1;

    // Runs a real countdown timer in the console, one second at a time.
    public void runPomodoroSession(Student student, String subjectName, int minutes) {
        StudySession session = new StudySession("SESS-" + sessionCounter, subjectName, minutes);
        sessionCounter++;
        int totalSeconds = minutes * 60;

        System.out.println("\nStarting a " + minutes + "-minute focus session on \"" + subjectName + "\".");

        try {
            for (int remaining = totalSeconds; remaining >= 0; remaining--) {
                int mm = remaining / 60;
                int ss = remaining % 60;
                System.out.print("\r  Time remaining: " + pad(mm) + ":" + pad(ss) + "   ");
                if (remaining > 0) {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("\nSession interrupted.");
            student.addStudySession(session);
            return;
        }

        System.out.println("\n\nTime's up! Great focus session.");
        session.complete();
        student.addStudySession(session);
    }

    // Adds a leading zero for single-digit numbers, e.g. 5 -> "05".
    private String pad(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return "" + number;
    }

    public Flashcard createFlashcard(Student student, String subjectCategory, String term, String definition) {
        Flashcard card = new Flashcard("CARD-" + (student.getFlashcards().size() + 1), subjectCategory, term, definition);
        student.addFlashcard(card);
        return card;
    }

    // Shows each flashcard one at a time and lets the student self-check.
    public void reviewFlashcards(Student student, Scanner input) {
        if (student.getFlashcards().isEmpty()) {
            System.out.println("You have not created any flashcards yet.");
            return;
        }
        for (int i = 0; i < student.getFlashcards().size(); i++) {
            Flashcard card = student.getFlashcards().get(i);
            System.out.println("\nTerm: " + card.getTerm());
            System.out.print("Press ENTER to reveal the definition...");
            input.nextLine();
            System.out.println("Definition: " + card.getDefinition());
            card.review();
            System.out.print("Mark as mastered? (y/n): ");
            String answer = input.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                card.markMastered();
            }
        }
    }

    // A short quiz that recommends the best study method for the student.
    public String recommendStudyMethod(Scanner input) {
        int visual = 0;
        int auditory = 0;
        int kinesthetic = 0;
        int reading = 0;

        System.out.println("\n1. When learning something new, I prefer to...");
        System.out.println("   A) see diagrams/pictures   B) listen to an explanation");
        System.out.println("   C) try it hands-on         D) read/write notes");
        System.out.print("Answer (A/B/C/D): ");
        String ans1 = input.nextLine().trim().toUpperCase();

        System.out.println("\n2. I remember things best when I...");
        System.out.println("   A) picture them in my mind   B) hear them repeated aloud");
        System.out.println("   C) act them out or practice  D) write them down");
        System.out.print("Answer (A/B/C/D): ");
        String ans2 = input.nextLine().trim().toUpperCase();

        System.out.println("\n3. During free time I would rather...");
        System.out.println("   A) watch a video   B) listen to a podcast");
        System.out.println("   C) build/do something  D) read a book/article");
        System.out.print("Answer (A/B/C/D): ");
        String ans3 = input.nextLine().trim().toUpperCase();

        String[] answers = { ans1, ans2, ans3 };
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].equals("A")) {
                visual++;
            } else if (answers[i].equals("B")) {
                auditory++;
            } else if (answers[i].equals("C")) {
                kinesthetic++;
            } else if (answers[i].equals("D")) {
                reading++;
            }
        }

        int best = visual;
        if (auditory > best) {
            best = auditory;
        }
        if (kinesthetic > best) {
            best = kinesthetic;
        }
        if (reading > best) {
            best = reading;
        }

        if (best == visual) {
            return "Visual Learner - use diagrams, mind maps, and color-coded notes.";
        } else if (best == auditory) {
            return "Auditory Learner - use recorded lectures, discussions, and read-alouds.";
        } else if (best == kinesthetic) {
            return "Kinesthetic Learner - use hands-on practice and real examples.";
        } else {
            return "Reading/Writing Learner - use written summaries and rewriting notes.";
        }
    }
}
