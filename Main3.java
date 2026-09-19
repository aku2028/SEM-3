import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main3 {

    private static int memberCounter = 2000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter total number of members to input: ");
        int totalMembers = scanner.nextInt();
        scanner.nextLine(); 

        GymMember[] members = new GymMember[totalMembers];

        for (int i = 0; i < totalMembers; i++) {
            System.out.println("\n--- Member " + (i + 1) + " ---");
            System.out.print("Enter Monthly Fee: ");
            int fee = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Choose Type (1: Standard, 2: Premium, 3: Elite, 4: Group Class): ");
            int type = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (type) {
                    case 1:
                        members[i] = new GymMember(fee);
                        break;
                    case 2:
                        System.out.print("Enter Trainer Name: ");
                        String trainer = scanner.nextLine();
                        members[i] = new PremiumMember(fee, trainer);
                        break;
                    case 3:
                        System.out.print("Enter Trainer Name: ");
                        String eliteTrainer = scanner.nextLine();
                        System.out.print("Enter Locker Number: ");
                        String locker = scanner.nextLine();
                        members[i] = new EliteMember(fee, eliteTrainer, locker);
                        break;
                    case 4:
                        System.out.print("Enter Group Class Name: ");
                        String className = scanner.nextLine();
                        members[i] = new GroupClassMember(fee, className);
                        break;
                    default:
                        System.out.println("Unknown type chosen. Defaulting to Standard.");
                        members[i] = new GymMember(fee);
                        break;
                }
                System.out.println("Created member with ID: " + members[i].getMembershipNumber());
            } catch (IllegalArgumentException e) {
                System.out.println("Error creating member: " + e.getMessage());
                i--; 
            }
        }

        System.out.println("\n=================================");
        System.out.println("PROCESSING WEEKLY CHECK-IN:");
        System.out.println("=================================");
        System.out.println(processWeeklyCheckIn(members));
        
        scanner.close();
    }

    public static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }
        if (code.charAt(0) != 'G') {
            return false;
        }
        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2))) {
            return false;
        }
        return Character.isUpperCase(code.charAt(3));
    }

    public static String processWeeklyCheckIn(GymMember[] members) {
        int processedCount = 0;
        int nullCount = 0;
        int groupCount = 0;
        int individualCount = 0;

        if (members != null) {
            for (GymMember member : members) {
                if (member == null) {
                    nullCount++;
                    continue;
                }
                
                processedCount++;
                if (member instanceof GroupClassMember) {
                    groupCount++;
                } else {
                    individualCount++;
                }
            }
        }

        return processedCount + " processed | " + nullCount + " null skipped | " + groupCount + " group | " + individualCount + " individual";
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

    public static int getMembersEnrolled() {
        return memberCounter - 2000;
    }

    public static class GymMember {
        private final String membershipNumber;
        private final int monthlyFee;
        private int sessionsAttended;
        private final int[] lateFeeHistory;
        private int lateFeeCount;
        private int feesPaid;

        public GymMember(int monthlyFee) {
            memberCounter++;
            this.membershipNumber = "GYM-" + memberCounter;
            this.monthlyFee = monthlyFee;
            this.sessionsAttended = 0;
            this.lateFeeHistory = new int[10];
            this.lateFeeCount = 0;
            this.feesPaid = 0;
        }

        public String getMembershipNumber() {
            return this.membershipNumber;
        }

        public void payFee(int amount) {
            this.feesPaid += amount;
        }

        public void payFee(int amount, String mode) {
            this.payFee(amount);
        }

        public int getFeesPaid() {
            return this.feesPaid;
        }

        public void attendSession() {
            this.sessionsAttended++;
        }

        public int getSessionsAttended() {
            return this.sessionsAttended;
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
                    new GymMember(monthlyFee);
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

        public PremiumMember(int monthlyFee, String trainerName) {
            super(monthlyFee);
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

        public EliteMember(int monthlyFee, String trainerName, String lockerNumber) {
            super(monthlyFee, trainerName);
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

        public GroupClassMember(int monthlyFee, String className) {
            super(monthlyFee);
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
