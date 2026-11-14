import java.util.*;

public class Main9 {
    public static int findSlot(List<Integer> prices, int newPrice) {
        int low = 0;
        int high = prices.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (prices.get(mid) == newPrice) {
                return mid;
            } else if (prices.get(mid) < newPrice) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return low;
    }

    public static void main(String[] args) {
        List<Integer> prices = Arrays.asList(120, 150, 200, 260);
        int newPrice = 210;

        System.out.println(findSlot(prices, newPrice));
    }
}