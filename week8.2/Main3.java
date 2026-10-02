import java.util.ArrayList;
import java.util.List;

class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Question {
    private final int questionNumber;
    private final int points;

    protected Question(int questionNumber, int points) {
        this.questionNumber = questionNumber;
        this.points = points;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getPoints() {
        return points;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {
    private final String correctOption;

    public MultipleChoiceQuestion(
            int questionNumber,
            int points,
            String correctOption) {

        super(questionNumber, points);
        this.correctOption = correctOption;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctOption.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(
            int questionNumber,
            int points,
            boolean correctAnswer) {

        super(questionNumber, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return Boolean.parseBoolean(answer) == correctAnswer;
    }
}

class Answer {
    private final Question question;
    private final String response;

    public Answer(
            Question question,
            String response) {

        this.question = question;
        this.response = response;
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isCorrect() {
        return question.evaluate(response);
    }

    public int getScore() {
        if (isCorrect()) {
            return question.getPoints();
        }

        return 0;
    }
}

class Examination {
    private final String name;
    private final List<Question> questions =
            new ArrayList<>();

    public Examination(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public int getTotalPoints() {
        int total = 0;

        for (Question question : questions) {
            total += question.getPoints();
        }

        return total;
    }
}

class Attempt {
    private final Student student;
    private final Examination examination;

    private final List<Answer> answers =
            new ArrayList<>();

    private boolean submitted = false;

    public Attempt(
            Student student,
            Examination examination) {

        this.student = student;
        this.examination = examination;
    }

    public void recordAnswer(
            Question question,
            String response) {

        if (submitted) {
            throw new IllegalStateException(
                    "Cannot change answers for a submitted examination."
            );
        }

        answers.add(
                new Answer(question, response)
        );

        System.out.println(
                "Answer recorded for Question "
                        + question.getQuestionNumber()
                        + "."
        );
    }

    public void submit() {

        if (submitted) {
            throw new IllegalStateException(
                    "Examination has already been submitted."
            );
        }

        submitted = true;

        System.out.println(
                "Exam "
                        + examination.getName()
                        + " submitted by "
                        + student.getName()
                        + "."
        );

        calculateResult();
    }

    private void calculateResult() {

        int totalScore = 0;

        System.out.println("Result:");

        for (Answer answer : answers) {

            boolean correct =
                    answer.isCorrect();

            int score =
                    answer.getScore();

            totalScore += score;

            System.out.println(
                    "Question "
                            + answer.getQuestion()
                                    .getQuestionNumber()
                            + ": "
                            + (correct
                            ? "Correct"
                            : "Incorrect")
                            + " ("
                            + score
                            + " points)"
            );
        }

        System.out.println(
                "Total score: "
                        + totalScore
                        + "/"
                        + examination.getTotalPoints()
        );
    }
}

class ExaminationSystem {

    public Attempt startExamination(
            Student student,
            Examination examination) {

        System.out.println(
                examination.getName()
                        + " started by "
                        + student.getName()
                        + "."
        );

        return new Attempt(
                student,
                examination
        );
    }
}

public class Main3 {

    public static void main(String[] args) {

        Student student =
                new Student("Student 1");

        Examination exam =
                new Examination("Exam A");

        Question question1 =
                new MultipleChoiceQuestion(
                        1,
                        5,
                        "C"
                );

        Question question2 =
                new TrueFalseQuestion(
                        2,
                        5,
                        false
                );

        exam.addQuestion(question1);
        exam.addQuestion(question2);

        ExaminationSystem system =
                new ExaminationSystem();

        Attempt attempt =
                system.startExamination(
                        student,
                        exam
                );

        attempt.recordAnswer(
                question1,
                "C"
        );

        attempt.recordAnswer(
                question2,
                "True"
        );

        attempt.submit();

        try {
            attempt.recordAnswer(
                    question1,
                    "A"
            );
        } catch (Exception e) {
            System.out.println(
                    e.getMessage()
            );
        }
    }
}
