public class Main5 {
    
}
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main5 {

    private static int memberCounter = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter total number of library members to input for the audit: ");
        int totalMembers = scanner.nextInt();
        scanner.nextLine(); 

        LibraryMember[] members = new LibraryMember[totalMembers];

        for (int i = 0; i < totalMembers; i++) {
            System.out.println("\n--- Member " + (i + 1) + " ---");
            System.out.print("Enter Borrow Limit: ");
            int limit = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Choose Type (1: General, 2: Student, 3: Honors Student, 4: Faculty): ");
            int type = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (type) {
                    case 1:
                        members[i] = new LibraryMember(limit);
                        break;
                    case 2:
                        System.out.print("Enter Course: ");
                        String course = scanner.nextLine();
                        members[i] = new StudentMember(limit, course);
                        break;
                    case 3:
                        System.out.print("Enter Course: ");
                        String honorsCourse = scanner.nextLine();
                        System.out.print("Enter Bonus Limit: ");
                        int bonus = scanner.nextInt();
                        scanner.nextLine(); 
                        members[i] = new HonorsStudentMember(limit, honorsCourse, bonus);
                        break;
                    case 4:
                        System.out.print("Enter Department: ");
                        String dept = scanner.nextLine();
                        members[i] = new FacultyMember(limit, dept);
                        break;
                    default:
                        System.out.println("Unknown type. Defaulting to General Member.");
                        members[i] = new LibraryMember(limit);
                        break;
                }
                System.out.println("Created member with ID: " + members[i].getMemberNumber());
            } catch (IllegalArgumentException e) {
                System.out.println("Construction rejected: " + e.getMessage());
                i--; 
            }
        }

        System.out.println("\n=================================");
        System.out.println("PROCESSING NIGHTLY AUDIT:");
        System.out.println("=================================");
        System.out.println(processNightlyAudit(members));
        
        scanner.close();
    }

    public static boolean isValidRenewalCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }
        if (code.charAt(0) != 'R') {
            return false;
        }
        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2))) {
            return false;
        }
        return Character.isUpperCase(code.charAt(3));
    }

    public static String processNightlyAudit(LibraryMember[] members) {
        int processedCount = 0;
        int nullCount = 0;
        int facultyCount = 0;
        int regularCount = 0;

        if (members != null) {
            for (LibraryMember member : members) {
                if (member == null) {
                    nullCount++;
                    continue;
                }
                
                processedCount++;
                if (member instanceof FacultyMember) {
                    facultyCount++;
                } else {
                    regularCount++;
                }
            }
        }

        return processedCount + " processed | " + nullCount + " null skipped | " + facultyCount + " faculty | " + regularCount + " regular";
    }

    public static int getMembersEnrolled() {
        return memberCounter - 100;
    }

    public static class LibraryMember {
        private final String memberNumber;
        private final int borrowLimit;
        private int booksBorrowed;
        private final int[] fineHistory;
        private int fineCount;

        public LibraryMember(int borrowLimit) {
            memberCounter++;
            this.memberNumber = "LIB-" + memberCounter;
            this.borrowLimit = borrowLimit;
            this.booksBorrowed = 0;
            this.fineHistory = new int[10];
            this.fineCount = 0;
        }

        public String getMemberNumber() {
            return this.memberNumber;
        }

        public void borrowBook() {
            this.booksBorrowed++;
        }

        public void borrowBook(String genre) {
            this.borrowBook();
        }

        public int getBooksBorrowed() {
            return this.booksBorrowed;
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
            return "General | Books: " + this.booksBorrowed;
        }
    }

    public static class StudentMember extends LibraryMember {
        private final String course;

        public StudentMember(int borrowLimit, String course) {
            super(borrowLimit);
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
            return "Student | Course: " + this.course + " | Books: " + getBooksBorrowed();
        }
    }

    public static class HonorsStudentMember extends StudentMember {
        private final int bonusLimit;

        public HonorsStudentMember(int borrowLimit, String course, int bonusLimit) {
            super(borrowLimit, course);
            this.bonusLimit = bonusLimit;
        }

        public int getBonusLimit() {
            return this.bonusLimit;
        }

        @Override
        public String displayInfo() {
            return "Honors Student | Course: " + getCourse() + " | Bonus Limit: " + this.bonusLimit + " | Books: " + getBooksBorrowed();
        }
    }

    public static class FacultyMember extends LibraryMember {
        private final String department;

        public FacultyMember(int borrowLimit, String department) {
            super(borrowLimit);
            this.department = department;
        }

        public String getDepartment() {
            return this.department;
        }

        @Override
        public String displayInfo() {
            return "Faculty Member | Department: " + this.department + " | Books: " + getBooksBorrowed();
        }
    }
}
