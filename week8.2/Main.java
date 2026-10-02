import java.util.ArrayList;

abstract class Vehicle {
    protected String vehicleId;
    protected String model;
    protected boolean available;

    public Vehicle(String vehicleId, String model) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getModel() {
        return model;
    }

    public abstract double calculateCharge(int days);
}



class Sedan extends Vehicle {

    public Sedan(String vehicleId, String model) {
        super(vehicleId, model);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50;
    }
}



class SUV extends Vehicle {

    public SUV(String vehicleId, String model) {
        super(vehicleId, model);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 80;
    }
}


class Truck extends Vehicle {

    public Truck(String vehicleId, String model) {
        super(vehicleId, model);
    }

    @Override
    public double calculateCharge(int days) {
        return (days * 100) + 50;
    }
}



class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}



class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double charge;
    private boolean active;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.charge = vehicle.calculateCharge(days);
        this.active = true;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getCharge() {
        return charge;
    }

    public boolean isActive() {
        return active;
    }

    public void closeRental() {
        active = false;
    }
}



class RentalSystem {


    private ArrayList<Vehicle> vehicles;
    private ArrayList<Rental> rentals;

    public RentalSystem() {
        vehicles = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    private Vehicle findVehicle(String vehicleId) {

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId().equals(vehicleId)) {
                return vehicle;
            }
        }

        return null;
    }

    public void rentVehicle(Customer customer, String vehicleId, int days) {

        Vehicle vehicle = findVehicle(vehicleId);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        if (!vehicle.isAvailable()) {
            System.out.println(
                vehicle.getModel() + " is currently unavailable."
            );
            return;
        }

        if (days <= 0) {
            System.out.println("Invalid rental duration.");
            return;
        }

        Rental rental = new Rental(customer, vehicle, days);

        rentals.add(rental);

        vehicle.setAvailable(false);

        System.out.println(
            vehicle.getModel() +
            " rented successfully by " +
            customer.getName()
        );

        System.out.printf(
            "Rental charge: $%.2f%n",
            rental.getCharge()
        );
    }

    public void returnVehicle(Customer customer, String vehicleId) {

        for (Rental rental : rentals) {

            if (rental.isActive()
                    && rental.getVehicle().getVehicleId().equals(vehicleId)
                    && rental.getCustomer().getCustomerId()
                       .equals(customer.getCustomerId())) {

                rental.closeRental();

                
                rental.getVehicle().setAvailable(true);

                System.out.println(
                    rental.getVehicle().getModel() +
                    " returned by " +
                    customer.getName()
                );

                return;
            }
        }

        System.out.println("No active rental found.");
    }
}

public class Main {

    public static void main(String[] args) {

        RentalSystem system = new RentalSystem();

        Vehicle sedanA = new Sedan("S1", "Sedan A");
        Vehicle suvB = new SUV("S2", "SUV B");

   
        system.addVehicle(sedanA);
        system.addVehicle(suvB);

     
        Customer customer1 =
            new Customer("C1", "Customer 1");

        Customer customer2 =
            new Customer("C2", "Customer 2");

        Customer customer3 =
            new Customer("C3", "Customer 3");


        system.rentVehicle(customer1, "S1", 3);

        system.rentVehicle(customer2, "S1", 2);

      
        system.returnVehicle(customer1, "S1");

        system.rentVehicle(customer3, "S2", 5);
    }
}