class Member {
    private final String name;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface MembershipPlan {
    String getName();
    int getMonths();
    double calculateFee();
}

class MonthlyPlan implements MembershipPlan {

    public String getName() {
        return "Monthly";
    }

    public int getMonths() {
        return 1;
    }

    public double calculateFee() {
        return 1000;
    }
}

class QuarterlyPlan implements MembershipPlan {

    public String getName() {
        return "Quarterly";
    }

    public int getMonths() {
        return 3;
    }

    public double calculateFee() {
        return 1000 * 3 * 0.90;
    }
}

class AnnualPlan implements MembershipPlan {

    public String getName() {
        return "Annual";
    }

    public int getMonths() {
        return 12;
    }

    public double calculateFee() {
        return 1000 * 12 * 0.75;
    }
}

// Example of adding a new plan.
class HalfYearlyPlan implements MembershipPlan {

    public String getName() {
        return "Half-Yearly";
    }

    public int getMonths() {
        return 6;
    }

    public double calculateFee() {
        return 1000 * 6 * 0.85;
    }
}

enum MembershipStatus {
    ACTIVE,
    FROZEN,
    EXPIRED
}

class Membership {

    private final Member member;
    private final MembershipPlan plan;

    private MembershipStatus status;

    public Membership(Member member,
                      MembershipPlan plan) {

        this.member = member;
        this.plan = plan;
        this.status = MembershipStatus.ACTIVE;
    }

    public void checkIn() {

        if (status != MembershipStatus.ACTIVE) {
            throw new IllegalStateException(
                "Check-in denied: "
                + member.getName()
                + "'s membership is "
                + status
            );
        }

        System.out.println(
            member.getName()
            + " checked in successfully."
        );
    }

    public void freeze() {

        if (status == MembershipStatus.EXPIRED) {
            throw new IllegalStateException(
                "Cannot freeze an Expired membership."
            );
        }

        if (status == MembershipStatus.FROZEN) {
            throw new IllegalStateException(
                "Membership is already Frozen."
            );
        }

        status = MembershipStatus.FROZEN;

        System.out.println(
            member.getName()
            + "'s membership frozen."
        );

        System.out.println(
            "Status: " + status
        );
    }

    public void unfreeze() {

        if (status == MembershipStatus.EXPIRED) {
            throw new IllegalStateException(
                "Cannot unfreeze an Expired membership."
            );
        }

        if (status != MembershipStatus.FROZEN) {
            throw new IllegalStateException(
                "Membership is not Frozen."
            );
        }

        status = MembershipStatus.ACTIVE;
    }

    public void expire() {

        status = MembershipStatus.EXPIRED;

        System.out.println(
            member.getName()
            + "'s membership expired."
        );

        System.out.println(
            "Status: " + status
        );
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public double getFee() {
        return plan.calculateFee();
    }

    public String getPlanName() {
        return plan.getName();
    }

    public Member getMember() {
        return member;
    }
}

class Gym {

    public Membership buyMembership(
        Member member,
        MembershipPlan plan) {

        Membership membership =
            new Membership(member, plan);

        System.out.printf(
            "%s membership created for %s.%n",
            plan.getName(),
            member.getName()
        );

        System.out.printf(
            "Fee: ₹%.2f%n",
            plan.calculateFee()
        );

        System.out.println(
            "Status: " + membership.getStatus()
        );

        return membership;
    }
}

public class GymDemo {

    public static void main(String[] args) {

        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        Gym gym = new Gym();

        Membership ashaMembership =
            gym.buyMembership(
                asha,
                new QuarterlyPlan()
            );

        Membership raviMembership =
            gym.buyMembership(
                ravi,
                new MonthlyPlan()
            );

        ashaMembership.checkIn();

        ashaMembership.freeze();

        try {
            ashaMembership.checkIn();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        raviMembership.expire();

        try {
            raviMembership.freeze();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}