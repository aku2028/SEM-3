public class Main3 {

    public static long countInversions(int[] roll) {
        int[] temp = new int[roll.length];
        return mergeSort(roll, temp, 0, roll.length - 1);
    }

    public static long mergeSort(int[] roll, int[] temp, int left, int right) {
        long count = 0;

        if (left < right) {
            int mid = left + (right - left) / 2;

            count += mergeSort(roll, temp, left, mid);
            count += mergeSort(roll, temp, mid + 1, right);
            count += merge(roll, temp, left, mid, right);
        }

        return count;
    }

    public static long merge(int[] roll, int[] temp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;
        long count = 0;

        while (i <= mid && j <= right) {
            if (roll[i] <= roll[j]) {
                temp[k++] = roll[i++];
            } else {
                temp[k++] = roll[j++];

                // Count inversions
                count += (mid - i + 1);
            }
        }

        while (i <= mid) {
            temp[k++] = roll[i++];
        }

        while (j <= right) {
            temp[k++] = roll[j++];
        }

        for (int x = left; x <= right; x++) {
            roll[x] = temp[x];
        }

        return count;
    }

    public static void main(String[] args) {
        int[] roll1 = {2, 4, 1, 3, 5};
        int[] roll2 = {5, 4, 3, 2, 1};

        System.out.println(countInversions(roll1));
        System.out.println(countInversions(roll2));
    }
}