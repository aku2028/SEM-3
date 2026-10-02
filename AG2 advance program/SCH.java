class Student {
    int roll;
    String name;
    int[] marks;
    double attendance, total, avg;

    Student(int r, String n, int[] m, double a) {
        roll = r; name = n; marks = m; attendance = a;
    }

    void calculate() {
        for (int m : marks) total += m;
        avg = total / 3;
    }

    void display() {
        String result = avg > 50 ? "Pass" : "Fail";
        String scholarship = avg > 75 && attendance > 80 ? "Eligible" : "Not Eligible";
        String performance = avg >= 85 ? "Excellent" : "Good";

        System.out.println(roll + " " + name + " Total=" + total +
                " Avg=" + avg + " " + result +
                " " + scholarship + " " + performance);
    }
}

public class SCH {
    public static void main(String[] args) {
        Student[] s = {
            new Student(1, "A", new int[]{80, 90, 85}, 90),
            new Student(2, "B", new int[]{60, 65, 70}, 85),
            new Student(3, "C", new int[]{45, 50, 40}, 75),
            new Student(4, "D", new int[]{90, 95, 88}, 95),
            new Student(5, "E", new int[]{70, 75, 72}, 82)
        };

        int high = 0;

        for (int i = 0; i < 5; i++) {
            s[i].calculate();
            s[i].display();
            high = s[i].avg > s[high].avg ? i : high;
        }

        System.out.println("Highest: " + s[high].name +
                           " (" + s[high].avg + ")");
    }
}