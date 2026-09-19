import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Testing Student Fine Ledger ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Course: ");
        String course = scanner.nextLine();

        StudentMember s = new StudentMember(id, 3, course);

        System.out.print("Enter fine amount to charge: ");
        int fineAmount = scanner.nextInt();
        scanner.nextLine();

        s.chargeFine(fineAmount);

        System.out.println("Total Fines Accumulated: " + s.getTotalFine());

        int[] history = s.getFineHistory();
        if (history.length > 0) {
            history[0] = 999; 
        }

        System.out.println("Verified Fine History (Defensive Copy Check): " + Arrays.toString(s.getFineHistory()));
        scanner.close();
    }

    public static String classifyGeneration(LibraryMember member) {
        if (member instanceof HonorsStudentMember) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (member instanceof FacultyMember) {
            return "Hierarchical sibling (independent branch)";
        }
        if (member instanceof StudentMember) {
            return "Multilevel descendant (2 generations deep)";
        }
        return "Base generation";
    }

    public static int getTotalBooksBorrowed(LibraryMember[] members) {
        int total = 0;
        for (LibraryMember member : members) {
            if (member != null) {
                total += member.getBooksBorrowed();
            }
        }
        return total;
    }

    public static class LibraryMember {
        private final String memberId;
        private final int borrowLimit;
        private int booksBorrowed;
        private final int[] fineHistory;
        private int fineCount;

        public LibraryMember(String memberId, int borrowLimit) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("Invalid Member ID. Must be at least 4 characters and not blank.");
            }
            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
            this.booksBorrowed = 0;
            this.fineHistory = new int[10];
            this.fineCount = 0;
        }

        public void borrowBook() {
            this.booksBorrowed++;
        }

        public int getBooksBorrowed() {
            return this.booksBorrowed;
        }

        public String getMemberId() {
            return this.memberId;
        }

        public int getBorrowLimit() {
            return this.borrowLimit;
        }

        protected void chargeFine(int amount) {
            if (this.fineCount < this.fineHistory.length) {
                this.fineHistory[this.fineCount] = amount;
                this.fineCount++;
            }
        }

        public int[] getFineHistory() {
            return Arrays.copyOf(this.fineHistory, this.fineCount);
        }

        public int getTotalFine() {
            int total = 0;
            for (int i = 0; i < this.fineCount; i++) {
                total += this.fineHistory[i];
            }
            return total;
        }

        public String displayInfo() {
            return "General Member | Books Borrowed: " + this.booksBorrowed;
        }

        public static String enrollBatch(String[] memberIds, int borrowLimit) {
            int enrolledCount = 0;
            int rejectedCount = 0;

            for (String id : memberIds) {
                try {
                    new LibraryMember(id, borrowLimit);
                    enrolledCount++;
                } catch (IllegalArgumentException e) {
                    rejectedCount++;
                }
            }

            return "Enrolled: " + enrolledCount + " | Rejected: " + rejectedCount;
        }
    }

    public static class StudentMember extends LibraryMember {
        private final String course;

        public StudentMember(String memberId, int borrowLimit, String course) {
            super(memberId, borrowLimit);
            this.course = course;
        }

        public String getCourse() {
            return this.course;
        }

        @Override
        protected void chargeFine(int amount) {
            super.chargeFine(amount / 2);
        }

        @Override
        public String displayInfo() {
            return "Student Member | Course: " + this.course + " | Books Borrowed: " + getBooksBorrowed();
        }
    }

    public static class HonorsStudentMember extends StudentMember {
        private final int bonusLimit;

        public HonorsStudentMember(String memberId, int borrowLimit, String course, int bonusLimit) {
            super(memberId, borrowLimit, course);
            this.bonusLimit = bonusLimit;
        }

        public int getBonusLimit() {
            return this.bonusLimit;
        }

        @Override
        public String displayInfo() {
            return "Honors Student Member | Course: " + getCourse() + " | Bonus Limit: " + this.bonusLimit + " | Books Borrowed: " + getBooksBorrowed();
        }
    }

    public static class FacultyMember extends LibraryMember {
        private final String department;

        public FacultyMember(String memberId, int borrowLimit, String department) {
            super(memberId, borrowLimit);
            this.department = department;
        }

        public String getDepartment() {
            return this.department;
        }

        @Override
        public String displayInfo() {
            return "Faculty Member | Department: " + this.department + " | Books Borrowed: " + getBooksBorrowed();
        }
    }
}
