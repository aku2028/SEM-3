import java.util.*;

class Student {
    int roll, m1, m2, m3;
    String name;
    double attendance;

    Student(int roll, String name, int m1, int m2, int m3, double attendance) {
        this.roll = roll;
        this.name = name;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.attendance = attendance;
    }
}

public class StudentPerformance {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Roll No: RA2511026011370");

        Student[] s = new Student[5];

        for (int i = 0; i < 5; i++) {
            System.out.println("\nStudent " + (i + 1));

            System.out.print("Roll No: ");
            int roll = sc.nextInt();

            sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Mark 1: ");
            int m1 = sc.nextInt();

            System.out.print("Mark 2: ");
            int m2 = sc.nextInt();

            System.out.print("Mark 3: ");
            int m3 = sc.nextInt();

            System.out.print("Attendance: ");
            double attendance = sc.nextDouble();

            s[i] = new Student(roll, name, m1, m2, m3, attendance);
        }

        double highest = 0;
        String topStudent = "";

        for (int i = 0; i < 5; i++) {
            int total = 0;

            int[] marks = {s[i].m1, s[i].m2, s[i].m3};

            for (int j = 0; j < 3; j++)
                total += marks[j];

            double avg = total / 3.0;

            String result = avg >= 50 ? "Pass" : "Fail";
            String scholarship = (avg >= 75 && s[i].attendance >= 80)
                    ? "Eligible" : "Not Eligible";
            String performance = avg >= 85 ? "Excellent" : "Good";

            System.out.println("\nRoll No: " + s[i].roll);
            System.out.println("Name: " + s[i].name);
            System.out.println("Total: " + total);
            System.out.println("Average: " + avg);
            System.out.println("Result: " + result);
            System.out.println("Scholarship: " + scholarship);
            System.out.println("Performance: " + performance);

            if (avg > highest) {
                highest = avg;
                topStudent = s[i].name;
            }
        }

        System.out.println("\nHighest Average: " + highest);
        System.out.println("Student: " + topStudent);
    }
}