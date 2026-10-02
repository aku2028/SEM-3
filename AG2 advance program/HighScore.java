public class HighScore {
    public static void main(String[] args) {
        System.out.println("RA2511026011343");
        int score1 = 85;
        int score2 = 92;
        int score3 = 88;

        if (score1 >= score2 && score1 >= score3) {
            System.out.println("Student 1 has the highest score: " + score1);
        } else if (score2 >= score1 && score2 >= score3) {
            System.out.println("Student 2 has the highest score: " + score2);
        } else {
            System.out.println("Student 3 has the highest score: " + score3);
        }
    }
}