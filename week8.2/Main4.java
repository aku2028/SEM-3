
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Room {
    private final String roomNumber;
    private final List<Reservation> reservations =
            new ArrayList<>();

    protected Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public abstract double calculatePrice(long nights);

    public boolean isAvailable(
            LocalDate startDate,
            LocalDate endDate) {

        for (Reservation reservation : reservations) {

            if (reservation.overlaps(
                    startDate,
                    endDate)) {

                return false;
            }
        }

        return true;
    }

    public void addReservation(
            Reservation reservation) {

        reservations.add(reservation);
    }

    public void removeReservation(
            Reservation reservation) {

        reservations.remove(reservation);
    }
}

class StandardRoom extends Room {

    public StandardRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 100;
    }
}

class DeluxeRoom extends Room {

    public DeluxeRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 150;
    }
}

class Reservation {
    private final Customer customer;
    private final Room room;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDate cancellationDeadline;

    private boolean cancelled;

    public Reservation(
            Customer customer,
            Room room,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate cancellationDeadline) {

        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline =
                cancellationDeadline;

        this.cancelled = false;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getNights() {
        return ChronoUnit.DAYS.between(
                startDate,
                endDate);
    }

    public double getPrice() {
        return room.calculatePrice(
                getNights());
    }

    public boolean overlaps(
            LocalDate start,
            LocalDate end) {

        if (cancelled) {
            return false;
        }

        return start.isBefore(endDate)
                && end.isAfter(startDate);
    }

    public void cancel(
            LocalDate currentDate) {

        if (cancelled) {
            throw new IllegalStateException(
                    "Reservation is already cancelled.");
        }

        if (currentDate.isAfter(
                cancellationDeadline)) {

            throw new IllegalStateException(
                    "Cancellation deadline has passed.");
        }

        cancelled = true;

        room.removeReservation(this);

        System.out.println(
                "Reservation for "
                        + customer.getName()
                        + ", "
                        + room.getClass().getSimpleName()
                        + " "
                        + room.getRoomNumber()
                        + " ("
                        + startDate
                        + "-"
                        + endDate
                        + ") cancelled successfully."
        );
    }
}

class HotelBookingSystem {

    public void checkAvailability(
            Room room,
            LocalDate startDate,
            LocalDate endDate) {

        if (room.isAvailable(
                startDate,
                endDate)) {

            System.out.println(
                    room.getClass().getSimpleName()
                            + " "
                            + room.getRoomNumber()
                            + " is available from "
                            + startDate
                            + " to "
                            + endDate
            );

        } else {

            System.out.println(
                    room.getClass().getSimpleName()
                            + " "
                            + room.getRoomNumber()
                            + " is not available from "
                            + startDate
                            + " to "
                            + endDate
            );
        }
    }

    public Reservation reserveRoom(
            Customer customer,
            Room room,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate cancellationDeadline) {

        if (!room.isAvailable(
                startDate,
                endDate)) {

            System.out.println(
                    room.getClass().getSimpleName()
                            + " "
                            + room.getRoomNumber()
                            + " is not available."
            );

            return null;
        }

        Reservation reservation =
                new Reservation(
                        customer,
                        room,
                        startDate,
                        endDate,
                        cancellationDeadline
                );

        room.addReservation(reservation);

        System.out.println(
                "Reservation confirmed for "
                        + customer.getName()
                        + ", "
                        + room.getClass().getSimpleName()
                        + " "
                        + room.getRoomNumber()
                        + " ("
                        + startDate
                        + "-"
                        + endDate
                        + ")."
        );

        System.out.printf(
                "Price: $%.2f%n",
                reservation.getPrice()
        );

        return reservation;
    }
}

public class Main4 {

    public static void main(String[] args) {

        Customer customerA =
                new Customer("Customer A");

        Customer customerB =
                new Customer("Customer B");

        Customer customerC =
                new Customer("Customer C");

        Room standard101 =
                new StandardRoom("101");

        Room deluxe201 =
                new DeluxeRoom("201");

        HotelBookingSystem system =
                new HotelBookingSystem();

        LocalDate jan1 =
                LocalDate.of(2026, 1, 1);

        LocalDate jan5 =
                LocalDate.of(2026, 1, 5);

        LocalDate jan3 =
                LocalDate.of(2026, 1, 3);

        LocalDate jan7 =
                LocalDate.of(2026, 1, 7);

        // Customer A checks availability
        system.checkAvailability(
                standard101,
                jan1,
                jan5
        );

        // Customer A makes reservation
        Reservation reservationA =
                system.reserveRoom(
                        customerA,
                        standard101,
                        jan1,
                        jan5,
                        LocalDate.of(2025, 12, 31)
                );

        // Customer B attempts overlapping reservation
        system.reserveRoom(
                customerB,
                standard101,
                jan3,
                jan7,
                LocalDate.of(2025, 12, 31)
        );

        // Customer A cancels reservation
        if (reservationA != null) {
            reservationA.cancel(
                    LocalDate.of(2025, 12, 30)
            );
        }

        // Customer C reserves Deluxe Room
        system.reserveRoom(
                customerC,
                deluxe201,
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 12),
                LocalDate.of(2026, 2, 5)
        );
    }
}

