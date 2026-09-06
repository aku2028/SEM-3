import java.util.Scanner;

public class Main3 {
    static String validateFileExtension(String filename) {
        int dot = filename.lastIndexOf('.');

        if (dot == -1) {
            return "Rejected — invalid file type";
        }

        String extension = filename.substring(dot + 1);

        if (extension.equalsIgnoreCase("pdf") ||
            extension.equalsIgnoreCase("docx") ||
            extension.equalsIgnoreCase("zip")) {
            return "Accepted";
        }

        return "Rejected — invalid file type";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();

        System.out.println(validateFileExtension(filename));

        sc.close();
    }
}