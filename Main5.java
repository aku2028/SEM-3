public class Main5 {

    static class Entry {
        String name;
        int marks;

        Entry(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }
    }

    public static Entry[] leaderboard(Entry[] entries) {
        int n = entries.length;
        int[] count = new int[101];

        // Step 1: Count the frequency of each key
        for (Entry entry : entries) {
            int key = 100 - entry.marks;
            count[key]++;
        }

        // Step 2: Calculate cumulative counts
        for (int i = 1; i < 101; i++) {
            count[i] += count[i - 1];
        }

        // Step 3: Place entries in sorted order
        Entry[] result = new Entry[n];

        for (int i = n - 1; i >= 0; i--) {
            int key = 100 - entries[i].marks;
            result[count[key] - 1] = entries[i];
            count[key]--;
        }

        return result;
    }

    public static void main(String[] args) {
        Entry[] entries = {
            new Entry("Asha", 90),
            new Entry("Ravi", 75),
            new Entry("Neha", 90),
            new Entry("Kiran", 100),
            new Entry("Meera", 75)
        };

        Entry[] result = leaderboard(entries);

        for (Entry entry : result) {
            System.out.println(entry.name + " " + entry.marks);
        }
    }
}