import java.util.Objects;
import java.util.Scanner;

public class main2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total number of library members to input: ");
        int totalMembers = scanner.nextInt();
        scanner.nextLine(); 

        LibraryMember[] members = new LibraryMember[totalMembers];

        for (int i = 0; i < totalMembers; i++) {
            System.out.println("\n--- Member " + (i + 1) + " ---");
            System.out.print("Enter Member ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Base Borrow Limit: ");
            int limit = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Choose Type (1: General, 2: Student, 3: Honors Student, 4: Faculty): ");
            int type = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (type) {
                    case 1:
                        members[i] = new LibraryMember(id, limit);
                        break;
                    case 2:
                        System.out.print("Enter Course: ");
                        String course = scanner.nextLine();
                        members[i] = new StudentMember(id, limit, course);
                        break;
                    case 3:
                        System.out.print("Enter Course: ");
                        String honorsCourse = scanner.nextLine();
                        System.out.print("Enter Bonus Limit: ");
                        int bonus = scanner.nextInt();
                        scanner.nextLine(); 
                        members[i] = new HonorsStudentMember(id, limit, honorsCourse, bonus);
                        break;
                    case 4:
                        System.out.print("Enter Department: ");
                        String dept = scanner.nextLine();
                        members[i] = new FacultyMember(id, limit, dept);
                        break;
                    default:
                        System.out.println("Unknown type. Defaulting to General Member.");
                        members[i] = new LibraryMember(id, limit);
                        break;
                }

                System.out.print("Enter number of books this member has borrowed: ");
                int books = scanner.nextInt();
                scanner.nextLine(); 
                for (int b = 0; b < books; b++) {
                    members[i].borrowBook();
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Construction rejected: " + e.getMessage());
                i--; 
            }
        }

        System.out.println("\n=================================");
        System.out.println("LIBRARY SYSTEM REPORTS:");
        System.out.println("=================================");
        
        for (LibraryMember member : members) {
            if (member != null) {
                System.out.println(member.displayInfo());
                System.out.println("Classification: " + classifyGeneration(member));
                System.out.println("---------------------------------");
            }
        }

        System.out.println("Total Books Borrowed Across All Members: " + getTotalBooksBorrowed(members));
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
