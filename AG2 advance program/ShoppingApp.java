import java.util.Scanner;

class Product {
    int productId;
    String productName;
    double price;
    int quantity;

    // Constructor
    Product(int productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

   
    double getTotal() {
        return price * quantity;
    }
}

public class ShoppingApp {
    public static void main(String[] args) {

        
System.out.println("RA2511026011370");
        Scanner sc = new Scanner(System.in);

      
        Product[] products = new Product[5];

        
        for (int i = 0; i < 5; i++) {

            System.out.println("\nEnter Product " + (i + 1));

            System.out.print("Product ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Product Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();

            System.out.print("Quantity: ");
            int quantity = sc.nextInt();

            products[i] = new Product(id, name, price, quantity);
        }

       
        double total = 0;

        for (int i = 0; i < 5; i++) {
            total = total + products[i].getTotal();
        }

        System.out.println("\nTotal Amount = ₹" + total);

      
        double discount = 0;

        if (total > 5000) {
            discount = total * 0.10;
        }

        double finalAmount = total - discount;

        System.out.println("Discount = ₹" + discount);
        System.out.println("Final Amount = ₹" + finalAmount);

        sc.close();
    }
}