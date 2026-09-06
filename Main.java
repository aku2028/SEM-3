class Student {
    String name;
    int attendance;

    static String collegeName = "SRM Institute of Science and Technology";
    static int studentCount = 0;

    Student(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;
        studentCount++;
    }

    static void printCollegeInfo() {
        System.out.println(collegeName);
        System.out.println("Students created: " + studentCount);
    }
}

public class Main {
    public static void main(String[] args) {
        new Student("Akash", 90);
        new Student("Rahul", 85);

        System.out.println("2 Student objects created");
        System.out.println();

        Student.printCollegeInfo();
    }
}