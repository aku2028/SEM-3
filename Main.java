import java.util.Scanner;
import java.util.Random;

public class Main {
    static String playRound(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
            (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
            (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            return "Player Wins";
        }

        return "Computer Wins";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String[] moves = {"Rock", "Paper", "Scissors"};
        String[] playerMoves = new String[5];
        String[] computerMoves = new String[5];
        String[] results = new String[5];

        int wins = 0;
        int losses = 0;
        int draws = 0;

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter your move (Rock/Paper/Scissors): ");
            String playerMove = sc.next();

            int index = random.nextInt(3);
            String computerMove = moves[index];

            String result = playRound(playerMove, computerMove);

            playerMoves[i] = playerMove;
            computerMoves[i] = computerMove;
            results[i] = result;

            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }

            System.out.println("Round " + (i + 1) + " — Player: " + playerMove +
                    ", Computer: " + computerMove + " " + result);
        }

        System.out.println();
        System.out.println("Final Summary");
        System.out.println("Round | Player Move | Computer Move | Result");

        for (int i = 0; i < 5; i++) {
            System.out.println((i + 1) + "     | " + playerMoves[i] +
                    "        | " + computerMoves[i] +
                    "         | " + results[i]);
        }

        double winPercentage = (wins * 100.0) / 5;

        System.out.println("Wins: " + wins +
                " | Losses: " + losses +
                " | Draws: " + draws +
                " | Win % = " + winPercentage + "%");

        sc.close();
    }
}