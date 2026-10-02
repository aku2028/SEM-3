import java.time.LocalDateTime;
import java.util.*;

class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface Seat {
    String getSeatNumber();
    double getPrice();
}

class RegularSeat implements Seat {
    private final String seatNumber;

    public RegularSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return 150;
    }
}

class PremiumSeat implements Seat {
    private final String seatNumber;

    public PremiumSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return 250;
    }
}

class ReclinerSeat implements Seat {
    private final String seatNumber;

    public ReclinerSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return 400;
    }
}

class Show {
    private final String showName;
    private final LocalDateTime startTime;

    private final Set<String> bookedSeats = new HashSet<>();

    public Show(String showName, LocalDateTime startTime) {
        this.showName = showName;
        this.startTime = startTime;
    }

    public String getShowName() {
        return showName;
    }

    public boolean isStarted() {
        return LocalDateTime.now().isAfter(startTime);
    }

    public boolean isSeatAvailable(Seat seat) {
        return !bookedSeats.contains(seat.getSeatNumber());
    }

    boolean reserveSeat(Seat seat) {

        if (!isSeatAvailable(seat)) {
            return false;
        }

        bookedSeats.add(seat.getSeatNumber());
        return true;
    }

    void releaseSeat(Seat seat) {
        bookedSeats.remove(seat.getSeatNumber());
    }
}

class Booking {
    private final Customer customer;
    private final Show show;
    private final List<Seat> seats;

    private boolean cancelled;

    public Booking(Customer customer,
                   Show show,
                   List<Seat> seats) {

        if (seats.isEmpty() || seats.size() > 6) {
            throw new IllegalArgumentException(
                "Booking must contain 1 to 6 seats."
            );
        }

        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>(seats);

        for (Seat seat : seats) {
            if (!show.reserveSeat(seat)) {
                // Roll back seats already reserved.
                for (Seat reserved : this.seats) {
                    show.releaseSeat(reserved);

                    if (reserved == seat) {
                        break;
                    }
                }

                throw new IllegalStateException(
                    "Seat " + seat.getSeatNumber()
                    + " is already booked for this show."
                );
            }
        }
    }

    public double getTotal() {
        double total = 0;

        for (Seat seat : seats) {
            total += seat.getPrice();
        }

        return total;
    }

    public void cancel() {

        if (cancelled) {
            throw new IllegalStateException(
                "Booking is already cancelled."
            );
        }

        if (show.isStarted()) {
            throw new IllegalStateException(
                "Cannot cancel booking after show starts."
            );
        }

        for (Seat seat : seats) {
            show.releaseSeat(seat);
        }

        cancelled = true;

        System.out.println(
            customer.getName() + "'s booking cancelled."
        );

        System.out.println(
            "Seats released."
        );
    }
}

class TicketSystem {

    public Booking book(Customer customer,
                        Show show,
                        Seat... seats) {

        Booking booking = new Booking(
            customer,
            show,
            Arrays.asList(seats)
        );

        System.out.print(
            "Booking confirmed for "
            + customer.getName() + ": "
        );

        for (int i = 0; i < seats.length; i++) {
            System.out.print(seats[i].getSeatNumber());

            if (i < seats.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(
            ". Total: ₹%.2f%n",
            booking.getTotal()
        );

        return booking;
    }
}

public class TicketDemo {

    public static void main(String[] args) {

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Show show = new Show(
            "Campus Premiere",
            LocalDateTime.now().plusHours(2)
        );

        TicketSystem system = new TicketSystem();

        Booking ashaBooking =
            system.book(
                asha,
                show,
                new RegularSeat("A1"),
                new RegularSeat("A2"),
                new PremiumSeat("F5")
            );

        try {
            system.book(
                ravi,
                show,
                new RegularSeat("A2")
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        system.book(
            ravi,
            show,
            new ReclinerSeat("R1")
        );

        ashaBooking.cancel();

        system.book(
            neha,
            show,
            new RegularSeat("A2")
        );
    }
}