
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

class Product {
    private final String name;
    private final double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(
            Product product,
            int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}

interface PaymentMethod {

    String getName();

    boolean processPayment(
            Order order,
            double amount);
}

class CreditCardPayment
        implements PaymentMethod {

    @Override
    public String getName() {
        return "Credit Card";
    }

    @Override
    public boolean processPayment(
            Order order,
            double amount) {

        return true;
    }
}

class PayPalPayment
        implements PaymentMethod {

    @Override
    public String getName() {
        return "PayPal";
    }

    @Override
    public boolean processPayment(
            Order order,
            double amount) {

        return false;
    }
}

enum OrderStatus {
    PENDING,
    PAID
}

class Order {

    private final String orderId;
    private final Customer customer;

    private final List<OrderItem> items =
            new ArrayList<>();

    private OrderStatus status =
            OrderStatus.PENDING;

    public Order(
            String orderId,
            Customer customer) {

        this.orderId = orderId;
        this.customer = customer;

        System.out.println(
                "Order created for "
                        + customer.getName()
                        + "."
        );
    }

    public void addProduct(
            Product product,
            int quantity) {

        items.add(
                new OrderItem(
                        product,
                        quantity)
        );
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotal() {

        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    public void pay(
            PaymentMethod paymentMethod) {

        if (isEmpty()) {
            System.out.println(
                    "Cannot process payment for an empty order."
            );
            return;
        }

        System.out.println(
                "Payment initiated via "
                        + paymentMethod.getName()
                        + " for Order "
                        + orderId
                        + "."
        );

        boolean successful =
                paymentMethod.processPayment(
                        this,
                        getTotal()
                );

        if (successful) {

            status = OrderStatus.PAID;

            System.out.println(
                    "Payment for Order "
                            + orderId
                            + " successful."
            );

        } else {

            System.out.println(
                    "Payment for Order "
                            + orderId
                            + " failed."
            );
        }

        System.out.println(
                "Order status: "
                        + status
        );
    }
}

public class Main5 {

    public static void main(String[] args) {

        Customer customerX =
                new Customer("Customer X");

        Product productA =
                new Product("Product A", 100);

        Product productB =
                new Product("Product B", 200);

        Order orderX =
                new Order(
                        "X",
                        customerX
                );

        orderX.addProduct(
                productA,
                2
        );

        orderX.addProduct(
                productB,
                1
        );

        orderX.pay(
                new CreditCardPayment()
        );

        Customer customerY =
                new Customer("Customer Y");

        Order orderY =
                new Order(
                        "Y",
                        customerY
                );

        orderY.pay(
                new CreditCardPayment()
        );

        Customer customerZ =
                new Customer("Customer Z");

        Product productC =
                new Product("Product C", 150);

        Order orderZ =
                new Order(
                        "Z",
                        customerZ
                );

        orderZ.addProduct(
                productC,
                1
        );

        orderZ.pay(
                new PayPalPayment()
        );
    }
}
