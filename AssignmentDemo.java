import java.time.LocalDate;

class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Assignment {
    private final String title;
    private final int maxMarks;
    private final LocalDate dueDate;

    public Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public abstract double applyPenalty(
        double marks,
        long lateDays
    );
}

class CodingAssignment extends Assignment {

    public CodingAssignment(String title, int maxMarks,
                            LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public double applyPenalty(double marks, long lateDays) {
        double penalty = lateDays * 0.10;
        return Math.max(0, marks * (1 - penalty));
    }
}

class WrittenAssignment extends Assignment {

    public WrittenAssignment(String title, int maxMarks,
                             LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public double applyPenalty(double marks, long lateDays) {
        double penalty = lateDays * 0.20;
        return Math.max(0, marks * (1 - penalty));
    }
}

enum SubmissionStatus {
    SUBMITTED,
    GRADED
}

class Submission {
    private final Student student;
    private final Assignment assignment;
    private final LocalDate submissionDate;

    private SubmissionStatus status;
    private double finalMarks;

    public Submission(Student student,
                      Assignment assignment,
                      LocalDate submissionDate) {

        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.status = SubmissionStatus.SUBMITTED;
    }

    public long getLateDays() {
        if (submissionDate.isAfter(assignment.getDueDate())) {
            return java.time.temporal.ChronoUnit.DAYS.between(
                assignment.getDueDate(),
                submissionDate
            );
        }

        return 0;
    }

    public void grade(double awardedMarks) {

        if (status == SubmissionStatus.GRADED) {
            throw new IllegalStateException(
                "Submission has already been graded."
            );
        }

        if (awardedMarks < 0 ||
            awardedMarks > assignment.getMaxMarks()) {

            throw new IllegalArgumentException(
                "Invalid marks."
            );
        }

        finalMarks = assignment.applyPenalty(
            awardedMarks,
            getLateDays()
        );

        status = SubmissionStatus.GRADED;
    }

    public void resubmit(LocalDate newDate) {

        if (status == SubmissionStatus.GRADED) {
            throw new IllegalStateException(
                "Cannot resubmit: assignment has already been graded."
            );
        }
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public double getFinalMarks() {
        return finalMarks;
    }

    public Student getStudent() {
        return student;
    }

    public Assignment getAssignment() {
        return assignment;
    }
}

class SubmissionPortal {

    public Submission submit(Student student,
                             Assignment assignment,
                             LocalDate date) {

        Submission submission =
            new Submission(student, assignment, date);

        long lateDays = submission.getLateDays();

        System.out.printf(
            "%s's submission for '%s' received (%s).%n",
            student.getName(),
            assignment.getTitle(),
            lateDays == 0
                ? "on time"
                : lateDays + " days late"
        );

        System.out.println(
            "Status: " + submission.getStatus()
        );

        return submission;
    }

    public void grade(Submission submission, double marks) {

        submission.grade(marks);

        System.out.printf(
            "%s graded: %.0f/%d. Status: %s%n",
            submission.getStudent().getName(),
            submission.getFinalMarks(),
            submission.getAssignment().getMaxMarks(),
            submission.getStatus()
        );
    }
}

public class AssignmentDemo {

    public static void main(String[] args) {

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Assignment coding =
            new CodingAssignment(
                "Linked List Lab",
                50,
                LocalDate.of(2026, 3, 10)
            );

        Assignment written =
            new WrittenAssignment(
                "Design Essay",
                50,
                LocalDate.of(2026, 3, 12)
            );

        SubmissionPortal portal = new SubmissionPortal();

        Submission ashaSubmission =
            portal.submit(
                asha,
                coding,
                LocalDate.of(2026, 3, 10)
            );

        Submission raviSubmission =
            portal.submit(
                ravi,
                written,
                LocalDate.of(2026, 3, 14)
            );

        portal.grade(ashaSubmission, 45);
        portal.grade(raviSubmission, 40);

        try {
            ashaSubmission.resubmit(
                LocalDate.of(2026, 3, 11)
            );
        } catch (Exception e) {
            System.out.println(
                "Cannot resubmit: 'Linked List Lab' has already been graded."
            );
        }
    }
}