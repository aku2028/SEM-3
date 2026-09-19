import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total number of library members to input: ");
        int totalMembers = scanner.nextInt();
        scanner.nextLine(); 

        String[] memberIds = new String[totalMembers];
        
        for (int i = 0; i < totalMembers; i++) {
            System.out.print("Enter Member ID for person " + (i + 1) + ": ");
            memberIds[i] = scanner.nextLine();
        }

        System.out.print("Enter default borrow limit: ");
        int limit = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("\n--- Processing Enrollment Batch ---");
        String result = LibraryMember.enrollBatch(memberIds, limit);
        System.out.println(result);

        System.out.println("\n--- Testing Single Student Member Creation ---");
        System.out.print("Enter Student Member ID: ");
        String stuId = scanner.nextLine();
        System.out.print("Enter Student Course Name: ");
        String course = scanner.nextLine();

        try {
            StudentMember s = new StudentMember(stuId, limit, course);
            System.out.print("How many books to borrow? ");
            int booksToBorrow = scanner.nextInt();
            for (int i = 0; i < booksToBorrow; i++) {
                s.borrowBook();
            }
            System.out.println("Books Borrowed successfully: " + s.getBooksBorrowed());
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected: " + e.getMessage());
        }

        scanner.close();
    }

    public static class LibraryMember {
        private final String memberId;
        private final int borrowLimit;
        private int booksBorrowed;

        public LibraryMember(String memberId, int borrowLimit) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("Invalid Member ID. Must be at least 4 characters and not blank.");
            }
            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
            this.booksBorrowed = 0;
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
    }
}
