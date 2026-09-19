import java.util.Arrays;
import java.util.Objects;

public class Main1 {

    public static void main(String[] args) {
        PremiumMember p = new PremiumMember("MEM5", 2000, "Coach Riya");
        p.chargeLateFee(200);
        System.out.println("Total Late Fees: " + p.getTotalLateFees());

        int[] history = p.getLateFeeHistory();
        if (history.length > 0) {
            history[0] = 999;
        }
        System.out.println("Defensive Copy Verification: " + Arrays.toString(p.getLateFeeHistory()));
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
            total += member.getSessionsAttended();
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
            return "Standard Member | Sessions: " + this.sessionsAttended;
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
            return "Premium Member | Trainer: " + this.trainerName + " | Sessions: " + getSessionsAttended();
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
