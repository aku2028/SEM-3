public class Main2 {

    public static int[] warehouseSummary(int[][] grid) {

        int totalItems = 0;
        int maxValue = -1;

        int maxRow = -1;
        int maxCol = -1;

        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                totalItems += grid[i][j];

                if (grid[i][j] > maxValue) {
                    maxValue = grid[i][j];

                    maxRow = i;
                    maxCol = j;
                }
            }
        }

        return new int[]{totalItems, maxRow, maxCol};
    }

    public static void main(String[] args) {

        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };

        int[] result = warehouseSummary(grid);

        System.out.println("(" + result[0] + ", (" 
                           + result[1] + ", " 
                           + result[2] + "))");
    }
}