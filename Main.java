import java.util.*;

// PROBLEM 1: Library Catalog Lookup

class Book {
    String isbn;
    String title;

    Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }
}

class WarehouseResult {
    long totalItems;
    int[] maxCoordinate;

    WarehouseResult(long totalItems, int[] maxCoordinate) {
        this.totalItems = totalItems;
        this.maxCoordinate = maxCoordinate;
    }
}

public class Main {

    // Binary Search
    // Time Complexity: O(log n)
    // Space Complexity: O(1)

    public static String findBook(List<Book> catalog, String targetIsbn) {
        int left = 0;
        int right = catalog.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparison = catalog.get(mid).isbn.compareTo(targetIsbn);

            if (comparison == 0) {
                return catalog.get(mid).title;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return "Not Found";
    }


    // PROBLEM 2: Warehouse Grid Summary

    // Time Complexity: O(m * n)
    // Space Complexity: O(1)

    public static WarehouseResult warehouseSummary(int[][] grid) {
        long totalItems = 0;
        int maxValue = -1;

        int maxRow = -1;
        int maxCol = -1;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                totalItems += grid[i][j];

                if (grid[i][j] > maxValue) {
                    maxValue = grid[i][j];
                    maxRow = i;
                    maxCol = j;
                }
            }
        }

        return new WarehouseResult(
            totalItems,
            new int[]{maxRow, maxCol}
        );
    }


    // PROBLEM 3: Pair With Target Sum (Unsorted)

    // Approach 1: Brute Force
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)

    public static boolean hasPairWithSumBruteForce(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {

                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }

        return false;
    }

    // Approach 2: HashSet
    // Time Complexity: O(n) average
    // Space Complexity: O(n)

    public static boolean hasPairWithSum(int[] nums, int target) {

        HashSet<Integer> seen = new HashSet<>();

        for (int num : nums) {

            int complement = target - num;

            if (seen.contains(complement)) {
                return true;
            }

            seen.add(num);
        }

        return false;
    }


    // PROBLEM 4: Pair With Target Sum (Unsorted Array)

    // Approach 1: Nested Loops
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)

    public static boolean pairSumBruteForce(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {

                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }

        return false;
    }

    // Approach 2: HashSet
    // Time Complexity: O(n) average
    // Space Complexity: O(n)

    public static boolean pairSumOptimized(int[] nums, int target) {

        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {

            int complement = target - num;

            if (set.contains(complement)) {
                return true;
            }

            set.add(num);
        }

        return false;
    }


    // PROBLEM 5: Maximize Area Between Two Boundaries

    // Approach 1: Brute Force
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)

    public static int maxContainerAreaBruteForce(int[] heights) {

        int maxArea = 0;

        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {

                int height = Math.min(heights[i], heights[j]);
                int width = j - i;

                int area = height * width;

                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    // Approach 2: Two Pointers
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    public static int maxContainerArea(int[] heights) {

        int left = 0;
        int right = heights.length - 1;

        int maxArea = 0;

        while (left < right) {

            int height = Math.min(heights[left], heights[right]);
            int width = right - left;

            int area = height * width;

            maxArea = Math.max(maxArea, area);

            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}