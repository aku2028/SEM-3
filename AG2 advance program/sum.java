
    import java.util.Scanner;
public class sum {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
System.out.println("RA2511026011366");
        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        int i = 1;
        int sum = 0;

        while (i <= n) {
            sum = sum + i;
            i++;
        }

        System.out.println("Sum = " + sum);

        sc.close();
    }
}


