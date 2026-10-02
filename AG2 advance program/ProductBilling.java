import java.util.Scanner;

class Product {
    int productId;
    String productName;
    double price;
    int quantity;

    Product(int productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    double getTotalPrice() {
        return price * quantity;
    }

    double getDiscount() {
        double total = getTotalPrice();

        if (total >= 5000) {
            return total * 0.10;
        } else {
            return total * 0.05;
        }
    }

    double getFinalPrice() {
        return getTotalPrice() - getDiscount();
    }
}

public class ProductBilling {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Roll No: RA2511026011370");

        Product[] products = new Product[5];

        for (int i = 0; i < 5; i++) {

            System.out.println("\nProduct " + (i + 1) + ":");

            System.out.print("ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();

            System.out.print("Quantity: ");
            int quantity = sc.nextInt();

            products[i] = new Product(id, name, price, quantity);
        }

        System.out.println("\n----- BILL DETAILS -----");

        for (int i = 0; i < 5; i++) {

            System.out.println("\nProduct ID: " + products[i].productId);
            System.out.println("Name: " + products[i].productName);
            System.out.println("Total Price: ₹" + products[i].getTotalPrice());
            System.out.println("Discount: ₹" + products[i].getDiscount());
            System.out.println("Final Price: ₹" + products[i].getFinalPrice());
        }

        sc.close();
    }
}