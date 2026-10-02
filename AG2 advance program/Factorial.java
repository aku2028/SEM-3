import java.util.Scanner;

public class Factorial
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
System.out.println("RA2511026011366");
        int n, i = 1;
        long fact = 1;

        System.out.print("Enter a non-negative number: ");
        n = sc.nextInt();

        if(n == 0)
        {
            fact = 1;
        }
        else
        {
            do
            {
                fact = fact * i;
                i++;
            }
            while(i <= n);
        }

        System.out.println("Factorial = " + fact);

        sc.close();
    }
}