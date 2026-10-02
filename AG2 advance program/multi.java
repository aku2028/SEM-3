 import java.util.Scanner;
public class multi {


    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
System.out.println("RA2511026011366");
        int n, i = 1;

        System.out.print("Enter a number: ");
        n = sc.nextInt();

        do
        {
            System.out.println(n + " x " + i + " = " + (n * i));
            i++;
        }
        while(i <= 10);

        sc.close();
    }
}

