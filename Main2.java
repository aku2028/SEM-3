import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter total number of members to input: ");
        int totalMembers = scanner.nextInt();
        scanner.nextLine(); 

        GymMember[] members = new GymMember[totalMembers];

        for (int i = 0; i < totalMembers; i++) {
            System.out.println("\n--- Member " + (i + 1) + " ---");
            System.out.print("Enter Member ID (min 4 characters): ");
            String id = scanner.nextLine();

            System.out.print("Enter Monthly Fee: ");
            int fee = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Choose Type (1: Standard, 2: Premium, 3: Elite, 4: Group Class): ");
            int type = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (type) {
                    case 1:
                        members[i] = new GymMember(id, fee);
                        break;
                    case 2:
                        System.out.print("Enter Trainer Name: ");
                        String trainer = scanner.nextLine();
                        members[i] = new PremiumMember(id, fee, trainer);
                        break;
                    case 3:
                        System.out.print("Enter Trainer Name: ");
                        String eliteTrainer = scanner.nextLine();
                        System.out.print("Enter Locker Number: ");
                        String locker = scanner.nextLine();
                        members[i] = new EliteMember(id, fee, eliteTrainer, locker);
                        break;
                    case 4:
                        System.out.print("Enter Group Class Name: ");
                        String className = scanner.nextLine();
                        members[i] = new GroupClassMember(id, fee, className);
                        break;
                    default:
                        System.out.println("Unknown type chosen. Defaulting to Standard.");
                        members[i] = new GymMember(id, fee);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error creating member: " + e.getMessage());
                i--; 
            }
        }

        System.out.println("\n=================================");
        System.out.println("GENERATED MONTHLY ANNOUNCEMENT:");
        System.out.println("=================================");
        System.out.println(batchPrint(members));
        
        scanner.close();
    }

    public static String batchPrint(GymMember[] members) {
        StringBuilder sb = new StringBuilder();
        
        for (GymMember member : members) {
            if (member == null) continue;
            
            sb.append(member.displayInfo());
            
            if (member instanceof PremiumMember) {
                PremiumMember pm = (PremiumMember) member;
                sb.append(" [Trainer via downcast: ").append(pm.getTrainerName()).append("]");
            }
            
            sb.append(" | ");
        }
        
        return sb.toString();
    }

    public static String classifyGeneration(GymMember member) {
        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        }
        if (member instanceof PremiumMember) {
            return "Multilevel descendant (2 generations deep)";
        }
        return "Base generation";
    }

    public static int getTotalSessionsAttended(GymMember[] members) {
        int total = 0;
        for (GymMember member : members) {
            if (member != null) {
                total += member.getSessionsAttended();
            }
        }
        return total;
    }

    public static class GymMember {
        private final String memberId;
        private final int monthlyFee;
        private int sessionsAttended;
        private final int[] lateFeeHistory;
        private int lateFeeCount;

        public GymMember(String memberId, int monthlyFee) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("Invalid Member ID. Must be at least 4 characters and not blank.");
            }
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
            this.sessionsAttended = 0;
            this.lateFeeHistory = new int[10];
            this.lateFeeCount = 0;
        }

        public void attendSession() {
            this.sessionsAttended++;
        }

        public int getSessionsAttended() {
            return this.sessionsAttended;
        }

        public String getMemberId() {
            return this.memberId;
        }

        public int getMonthlyFee() {
            return this.monthlyFee;
        }

        protected void chargeLateFee(int amount) {
            if (this.lateFeeCount < this.lateFeeHistory.length) {
                this.lateFeeHistory[this.lateFeeCount] = amount;
                this.lateFeeCount++;
            }
        }

        public int[] getLateFeeHistory() {
            return Arrays.copyOf(this.lateFeeHistory, this.lateFeeCount);
        }

        public int getTotalLateFees() {
            int total = 0;
            for (int i = 0; i < this.lateFeeCount; i++) {
                total += this.lateFeeHistory[i];
            }
            return total;
        }

        public String displayInfo() {
            return "Standard | Sessions: " + this.sessionsAttended;
        }

        public static String signUpBatch(String[] memberIds, int monthlyFee) {
            int signedUpCount = 0;
            int rejectedCount = 0;

            for (String id : memberIds) {
                try {
                    new GymMember(id, monthlyFee);
                    signedUpCount++;
                } catch (IllegalArgumentException e) {
                    rejectedCount++;
                }
            }

            return "Signed Up: " + signedUpCount + " | Rejected: " + rejectedCount;
        }
    }

    public static class PremiumMember extends GymMember {
        private final String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        public String getTrainerName() {
            return this.trainerName;
        }

        @Override
        protected void chargeLateFee(int amount) {
            super.chargeLateFee(amount / 2);
        }

        @Override
        public String displayInfo() {
            return "Premium | Trainer: " + this.trainerName + " | Sessions: " + getSessionsAttended();
        }
    }

    public static class EliteMember extends PremiumMember {
        private final String lockerNumber;

        public EliteMember(String memberId, int monthlyFee, String trainerName, String lockerNumber) {
            super(memberId, monthlyFee, trainerName);
            this.lockerNumber = lockerNumber;
        }

        public String getLockerNumber() {
            return this.lockerNumber;
        }

        @Override
        public String displayInfo() {
            return "Elite Member | Trainer: " + getTrainerName() + " | Locker: " + this.lockerNumber + " | Sessions: " + getSessionsAttended();
        }
    }

    public static class GroupClassMember extends GymMember {
        private final String className;

        public GroupClassMember(String memberId, int monthlyFee, String className) {
            super(memberId, monthlyFee);
            this.className = className;
        }

        public String getClassName() {
            return this.className;
        }

        @Override
        public String displayInfo() {
            return "Group Class Member | Class: " + this.className + " | Sessions: " + getSessionsAttended();
        }
    }
}
