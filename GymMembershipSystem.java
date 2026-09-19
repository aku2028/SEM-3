
public class GymMembershipSystem {

    public static void main(String[] args) {
        String[] batch1 = {"MEM1", "GM1", "MEM2", " ", "MEM3"};
        String result = GymMember.signUpBatch(batch1, 1000);
        System.out.println(result);

        PremiumMember p = new PremiumMember("MEM01", 2000, "Coach Riya");
        p.attendSession();
        p.attendSession();
        System.out.println("Sessions Attended: " + p.getSessionsAttended());
    }

    public static class GymMember {
        private final String memberId;
        private final int monthlyFee;
        private int sessionsAttended;

        public GymMember(String memberId, int monthlyFee) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("Invalid Member ID. Must be at least 4 characters and not blank.");
            }
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
            this.sessionsAttended = 0;
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
    }
}
