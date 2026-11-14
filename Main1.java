public class Main1 {

    public static int[] bubbleStats(int[] scores) {
        int passes = 0;
        int swaps = 0;
        int n = scores.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++;

            for (int j = 0; j < n - 1 - i; j++) {
                if (scores[j] > scores[j + 1]) {
                    int temp = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = temp;

                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        return new int[]{passes, swaps};
    }

    public static void main(String[] args) {

        int[] scores1 = {5, 1, 4, 2, 8};

        int[] result1 = bubbleStats(scores1);

        System.out.println("Sorted Scores: ");
        for (int score : scores1) {
            System.out.print(score + " ");
        }

        System.out.println("\nPasses = " + result1[0]);
        System.out.println("Swaps = " + result1[1]);

        int[] scores2 = {1, 2, 3, 4};

        int[] result2 = bubbleStats(scores2);

        System.out.println("\nSorted Scores: ");
        for (int score : scores2) {
            System.out.print(score + " ");
        }

        System.out.println("\nPasses = " + result2[0]);
        System.out.println("Swaps = " + result2[1]);
    }
}