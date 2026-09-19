import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total number of library members to input for the report: ");
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
            } catch (IllegalArgumentException e) {
                System.out.println("Construction rejected: " + e.getMessage());
                i--; 
            }
        }

        System.out.println("\n=================================");
        System.out.println("GENERATED WEEKLY CIRCULATION REPORT:");
        System.out.println("=================================");
        System.out.println(batchPrint(members));

        scanner.close();
    }

    public static String batchPrint(LibraryMember[] members) {
        StringBuilder sb = new StringBuilder();
        
        for (LibraryMember member : members) {
            if (member == null) continue;
            
            sb.append(member.displayInfo());
            
            if (member instanceof StudentMember) {
                StudentMember sm = (StudentMember) member;
                sb.append(" [Course via downcast: ").append(sm.getCourse()).append("]");
            }
            
            sb.append(" | ");
        }
        
        return sb.toString();
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
            return "General | Books: " + this.booksBorrowed;
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
            return "Student | Course: " + this.course + " | Books: " + getBooksBorrowed();
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
            return "Honors Student | Course: " + getCourse() + " | Bonus Limit: " + this.bonusLimit + " | Books: " + getBooksBorrowed();
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
            return "Faculty Member | Department: " + this.department + " | Books: " + getBooksBorrowed();
        }
    }
}
