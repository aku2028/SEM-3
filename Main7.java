import java.util.*;

public class Main7 {
    public static Map.Entry<String, Integer> mostPopular(List<String> orders) {
        HashMap<String, Integer> map = new HashMap<>();

        for (String item : orders) {
            map.put(item, map.getOrDefault(item, 0) + 1);
        }

        String popularItem = orders.get(0);
        int maxCount = 0;

        for (String item : orders) {
            int count = map.get(item);

            if (count > maxCount) {
                maxCount = count;
                popularItem = item;
            }
        }

        return new AbstractMap.SimpleEntry<>(popularItem, maxCount);
    }

    public static void main(String[] args) {
        List<String> orders = Arrays.asList(
            "dosa", "idli", "vada", "dosa", "idli", "dosa", "tea"
        );

        Map.Entry<String, Integer> result = mostPopular(orders);

        System.out.println("(" + result.getKey() + ", " + result.getValue() + ")");
    }
}