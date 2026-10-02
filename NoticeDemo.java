import java.util.*;

class Student {

    private final String name;
    private final String department;

    private final List<NotificationChannel> preferredChannels =
        new ArrayList<>();

    public Student(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void addChannel(NotificationChannel channel) {
        if (!preferredChannels.contains(channel)) {
            preferredChannels.add(channel);
        }
    }

    public List<NotificationChannel> getPreferredChannels() {
        return Collections.unmodifiableList(
            preferredChannels
        );
    }
}

class Notice {

    private final String title;
    private final Set<String> targetDepartments;

    public Notice(String title,
                  Set<String> targetDepartments) {

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Notice title is required."
            );
        }

        if (targetDepartments == null ||
            targetDepartments.isEmpty()) {

            throw new IllegalArgumentException(
                "At least one target department is required."
            );
        }

        this.title = title;
        this.targetDepartments =
            new HashSet<>(targetDepartments);
    }

    public String getTitle() {
        return title;
    }

    public boolean targets(String department) {
        return targetDepartments.contains(department);
    }
}

interface NotificationChannel {

    String getChannelName();

    void send(Student student, Notice notice);
}

class EmailChannel implements NotificationChannel {

    public String getChannelName() {
        return "Email";
    }

    public void send(Student student, Notice notice) {

        System.out.println(
            "[Email → "
            + student.getName()
            + "] "
            + notice.getTitle()
        );
    }
}

class SmsChannel implements NotificationChannel {

    public String getChannelName() {
        return "SMS";
    }

    public void send(Student student, Notice notice) {

        System.out.println(
            "[SMS → "
            + student.getName()
            + "] "
            + notice.getTitle()
        );
    }
}

class AppChannel implements NotificationChannel {

    public String getChannelName() {
        return "App";
    }

    public void send(Student student, Notice notice) {

        System.out.println(
            "[App → "
            + student.getName()
            + "] "
            + notice.getTitle()
        );
    }
}

// New channel requires no modification to NoticeBoard.
class WhatsAppChannel implements NotificationChannel {

    public String getChannelName() {
        return "WhatsApp";
    }

    public void send(Student student, Notice notice) {

        System.out.println(
            "[WhatsApp → "
            + student.getName()
            + "] "
            + notice.getTitle()
        );
    }
}

class NoticeBoard {

    private final List<Student> students =
        new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void postNotice(Notice notice) {

        System.out.println(
            "Notice '" + notice.getTitle()
            + "' posted."
        );

        for (Student student : students) {

            if (!notice.targets(
                    student.getDepartment())) {
                continue;
            }

            for (NotificationChannel channel :
                    student.getPreferredChannels()) {

                channel.send(student, notice);
            }
        }
    }
}

public class NoticeDemo {

    public static void main(String[] args) {

        Student asha =
            new Student("Asha", "CSE");

        Student ravi =
            new Student("Ravi", "ECE");

        asha.addChannel(new EmailChannel());
        asha.addChannel(new AppChannel());

        ravi.addChannel(new SmsChannel());

        NoticeBoard board = new NoticeBoard();

        board.addStudent(asha);
        board.addStudent(ravi);

        Notice notice1 =
            new Notice(
                "Lab Closed Tomorrow",
                Set.of("CSE")
            );

        board.postNotice(notice1);

        Notice notice2 =
            new Notice(
                "Fee Deadline Extended",
                Set.of("CSE", "ECE")
            );

        board.postNotice(notice2);

        try {
            Notice invalid =
                new Notice(
                    "Sports Day",
                    Collections.emptySet()
                );

            board.postNotice(invalid);

        } catch (Exception e) {
            System.out.println(
                "Cannot post notice: "
                + e.getMessage()
            );
        }
    }
}