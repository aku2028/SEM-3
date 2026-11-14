import java.util.*;

public class Main6 {
    public static List<Integer> mergeTokens(List<Integer> counterA, List<Integer> counterB) {
        List<Integer> result = new ArrayList<>();

        int i = 0, j = 0;

        while (i < counterA.size() && j < counterB.size()) {
            if (counterA.get(i) <= counterB.get(j)) {
                result.add(counterA.get(i));
                i++;
            } else {
                result.add(counterB.get(j));
                j++;
            }
        }

        while (i < counterA.size()) {
            result.add(counterA.get(i++));
        }

        while (j < counterB.size()) {
            result.add(counterB.get(j++));
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> counterA = Arrays.asList(3, 8, 15, 20);
        List<Integer> counterB = Arrays.asList(5, 8, 12);

        System.out.println(mergeTokens(counterA, counterB));
    }
}