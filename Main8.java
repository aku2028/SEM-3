import java.util.*;

public class Main8 {
    public static int countAlerts(List<Integer> readings, int k, int threshold) {
        int sum = 0;
        int count = 0;

        for (int i = 0; i < k; i++) {
            sum += readings.get(i);
        }

        if (sum >= k * threshold) {
            count++;
        }

        for (int i = k; i < readings.size(); i++) {
            sum = sum + readings.get(i) - readings.get(i - k);

            if (sum >= k * threshold) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        List<Integer> readings = Arrays.asList(2, 2, 2, 2, 5, 5, 5, 8);
        int k = 3;
        int threshold = 4;

        System.out.println(countAlerts(readings, k, threshold));
    }
}