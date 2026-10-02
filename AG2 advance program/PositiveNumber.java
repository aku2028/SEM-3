import java.util.Scanner;

public class PositiveNumber
{
    public static void main(String[] args)
    {
         System.out.println("RA2511026011366");
        Scanner sc = new Scanner(System.in);

        int n;

        do
        {
            System.out.print("Enter a positive number: ");
            n = sc.nextInt();
        }
        while(n <= 0);

        System.out.println("Valid number = " + n);

        sc.close();
    }
}