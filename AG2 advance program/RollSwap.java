public class RollSwap {
   
    public static void main(String[] args) {
System.out.println("RA2511026011343");
        int roll1 = 101;
        int roll2 = 202;
        int swap;

        System.out.println("Before Swapping:");
        System.out.println("Roll 1 = " + roll1);
        System.out.println("Roll 2 = " + roll2);

        swap = roll1;
        roll1 = roll2;
        roll2 = swap;

        System.out.println("\nAfter Swapping:");
        System.out.println("Roll 1 = " + roll1);
        System.out.println("Roll 2 = " + roll2);
    }
}
