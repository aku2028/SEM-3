
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

interface LeavePolicy {
    boolean isLeaveAllowed(
            LocalDate startDate,
            LocalDate endDate);

    String getPolicyName();
}

class FullTimeLeavePolicy implements LeavePolicy {

    private static final int MAX_LEAVE_DAYS = 30;

    @Override
    public boolean isLeaveAllowed(
            LocalDate startDate,
            LocalDate endDate) {

        long days =
                ChronoUnit.DAYS.between(
                        startDate,
                        endDate) + 1;

        return days <= MAX_LEAVE_DAYS;
    }

    @Override
    public String getPolicyName() {
        return "Full Time Leave Policy";
    }
}

class PartTimeLeavePolicy implements LeavePolicy {

    private static final int MAX_LEAVE_DAYS = 15;

    @Override
    public boolean isLeaveAllowed(
            LocalDate startDate,
            LocalDate endDate) {

        long days =
                ChronoUnit.DAYS.between(
                        startDate,
                        endDate) + 1;

        return days <= MAX_LEAVE_DAYS;
    }

    @Override
    public String getPolicyName() {
        return "Part Time Leave Policy";
    }
}

class ContractorLeavePolicy implements LeavePolicy {

    private static final int MAX_LEAVE_DAYS = 10;

    @Override
    public boolean isLeaveAllowed(
            LocalDate startDate,
            LocalDate endDate) {

        long days =
                ChronoUnit.DAYS.between(
                        startDate,
                        endDate) + 1;

        return days <= MAX_LEAVE_DAYS;
    }

    @Override
    public String getPolicyName() {
        return "Contractor Leave Policy";
    }
}

abstract class Employee {

    private final int id;
    private final String name;
    private final LeavePolicy leavePolicy;

    protected Employee(
            int id,
            String name,
            LeavePolicy leavePolicy) {

        this.id = id;
        this.name = name;
        this.leavePolicy = leavePolicy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LeavePolicy getLeavePolicy() {
        return leavePolicy;
    }

    public boolean canTakeLeave(
            LocalDate startDate,
            LocalDate endDate) {

        return leavePolicy.isLeaveAllowed(
                startDate,
                endDate);
    }

    public abstract String getEmployeeType();
}

class FullTimeEmployee extends Employee {

    public FullTimeEmployee(
            int id,
            String name) {

        super(
                id,
                name,
                new FullTimeLeavePolicy());
    }

    @Override
    public String getEmployeeType() {
        return "FullTimeEmployee";
    }
}

class PartTimeEmployee extends Employee {

    public PartTimeEmployee(
            int id,
            String name) {

        super(
                id,
                name,
                new PartTimeLeavePolicy());
    }

    @Override
    public String getEmployeeType() {
        return "PartTimeEmployee";
    }
}

class Contractor extends Employee {
    public Contractor(int id, String name) {
        super(id, name, new ContractorLeavePolicy());
    }

    @Override
    public String getEmployeeType() {
        return "Contractor";
    }
}
class Reviewer {

    private final int id;
    private final String name;

    public Reviewer(
            int id,
            String name) {

        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void approveLeave(
            LeaveRequest request) {

        request.approve(this);
    }

    public void rejectLeave(
            LeaveRequest request) {

        request.reject(this);
    }
}

class LeaveRequest {

    private final Employee employee;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private LeaveStatus status;
    private Reviewer reviewer;

    public LeaveRequest(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee cannot be null.");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(
                    "Leave dates cannot be null.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date.");
        }

        if (!employee.canTakeLeave(
                startDate,
                endDate)) {

            throw new IllegalArgumentException(
                    "Leave request violates employee leave policy.");
        }

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    private void changeStatus(
            LeaveStatus newStatus,
            Reviewer reviewer) {

        if (status != LeaveStatus.PENDING) {
            throw new IllegalStateException(
                    "Cannot change leave request status from "
                            + status
                            + " to "
                            + newStatus
                            + ".");
        }

        if (reviewer == null) {
            throw new IllegalArgumentException(
                    "A reviewer is required.");
        }

        this.reviewer = reviewer;
        this.status = newStatus;
    }

    void approve(Reviewer reviewer) {
        changeStatus(
                LeaveStatus.APPROVED,
                reviewer);
    }

    void reject(Reviewer reviewer) {
        changeStatus(
                LeaveStatus.REJECTED,
                reviewer);
    }
}

class LeaveService {

    public LeaveRequest submitLeaveRequest(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        LeaveRequest request =
                new LeaveRequest(
                        employee,
                        startDate,
                        endDate);

        System.out.println(
                "Leave request submitted for "
                        + employee.getName()
                        + " ("
                        + startDate
                        + "-"
                        + endDate
                        + ").");

        System.out.println(
                "Status: "
                        + request.getStatus());

        return request;
    }
}

public class Main2 {

    public static void main(String[] args) {

        LeaveService leaveService =
                new LeaveService();

        Employee john =
                new FullTimeEmployee(
                        1,
                        "John");

        Employee jane =
                new PartTimeEmployee(
                        2,
                        "Jane");

        Reviewer alice =
                new Reviewer(
                        101,
                        "Alice");

        Reviewer bob =
                new Reviewer(
                        102,
                        "Bob");

        // John submits leave
        LeaveRequest johnRequest =
                leaveService.submitLeaveRequest(
                        john,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 5));

        // Alice approves John's request
        alice.approveLeave(johnRequest);

        System.out.println(
                "John's leave request ("
                        + johnRequest.getStartDate()
                        + "-"
                        + johnRequest.getEndDate()
                        + ") approved.");

        System.out.println(
                "Status: "
                        + johnRequest.getStatus());

        // Jane submits leave
        LeaveRequest janeRequest =
                leaveService.submitLeaveRequest(
                        jane,
                        LocalDate.of(2026, 2, 10),
                        LocalDate.of(2026, 2, 11));

        // Bob rejects Jane's request
        bob.rejectLeave(janeRequest);

        System.out.println(
                "Jane's leave request ("
                        + janeRequest.getStartDate()
                        + "-"
                        + janeRequest.getEndDate()
                        + ") rejected.");

        System.out.println(
                "Status: "
                        + janeRequest.getStatus());

        // John attempts to approve the already approved request again.
        // This demonstrates that an approved request cannot change state.
        try {
            alice.approveLeave(johnRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

