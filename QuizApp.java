import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    private String question;
    private ArrayList<String> options;
    private int correctOptionIndex;

    public QuizQuestion(String question, ArrayList<String> options, int correctOptionIndex) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}

class Quiz {
    private ArrayList<QuizQuestion> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    public Quiz(ArrayList<QuizQuestion> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void startQuiz() {
        System.out.println("Welcome to the Quiz!");
        displayNextQuestion();
    }

    private void displayNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
            System.out.println("\nQuestion: " + currentQuestion.getQuestion());

            ArrayList<String> options = currentQuestion.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            startTimer();
            getUserAnswer();
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Moving to the next question.");
                stopTimer();
                currentQuestionIndex++;
                displayNextQuestion();
            }
        }, 15000); // 15 seconds timer
    }

    private void stopTimer() {
        timer.cancel();
    }

    private void getUserAnswer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice (1-" + questions.get(currentQuestionIndex).getOptions().size() + "): ");

        try {
            int userChoice = scanner.nextInt();
            validateAnswer(userChoice);
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            getUserAnswer();
        }
    }

    private void validateAnswer(int userChoice) {
        int correctOptionIndex = questions.get(currentQuestionIndex).getCorrectOptionIndex();

        if (userChoice == correctOptionIndex + 1) {
            System.out.println("Correct! You earned a point.");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was: " + (correctOptionIndex + 1));
        }

        currentQuestionIndex++;
        stopTimer();
        displayNextQuestion();
    }

    private void endQuiz() {
        System.out.println("\nQuiz completed!");
        System.out.println("Your final score: " + score + " out of " + questions.size());

        // Display a summary of correct and incorrect answers if needed

        System.exit(0);
    }
}

public class QuizApp {
    public static void main(String[] args) {
        // Create quiz questions
        QuizQuestion question1 = new QuizQuestion("What is the capital of France?",
                new ArrayList<>(List.of("London", "Berlin", "Paris", "Rome")), 2);
        QuizQuestion question2 = new QuizQuestion("Which planet is known as the Red Planet?",
                new ArrayList<>(List.of("Earth", "Mars", "Jupiter", "Venus")), 1);

        // Create a quiz
        ArrayList<QuizQuestion> quizQuestions = new ArrayList<>(List.of(question1, question2));
        Quiz quiz = new Quiz(quizQuestions);

        // Start the quiz
        quiz.startQuiz();
    }
}
