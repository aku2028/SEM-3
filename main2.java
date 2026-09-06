import java.util.Scanner;

public class main2 {
    static void parseStudentRecord(String csvLine) {
        String[] fields = csvLine.split(",");

        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.println("Name: " + fields[0] +
                " | Roll No: " + fields[1] +
                " | Dept: " + fields[2]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String csvLine = sc.nextLine();

        parseStudentRecord(csvLine);

        sc.close();
    }
}