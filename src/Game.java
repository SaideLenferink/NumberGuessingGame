import java.util.Scanner;

public class Game {

    private static int finalScore = 0;
    private static int numberOfPlays = 0;
    private static Round round;
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean getHints = false;

    public Game() {
    }

    public static void initiateNew(boolean firstRound) {

        if (firstRound) {
            System.out.print(
                    """
                            Let's play a game! The game is to guess a random number.
                                                        
                            The maximum score is 100.
                            If you guess the number on the first try, you'll get 100 points!
                            For every wrong guess, 10 points get deducted from the maximum score.
                                                        
                            Please choose a difficulty [easy, normal, expert]: ...
                            """
            );
        } else {
            System.out.println("Please choose a difficulty for the next round [easy, normal, expert]: ...");

        }
        initiateNewRound();
        playRound();
    }

    private static void initiateNewRound() {
        round = new Round();
        String difficulty = scanner.nextLine().toLowerCase();

        if (difficulty.matches("easy") || difficulty.matches("normal") || difficulty.matches("expert")) {
            System.out.println("Do you want to receive hints while playing?");
            String hints = scanner.nextLine().toLowerCase();

            getHints = hints.equals("yes");

            Round.chooseDifficulty(difficulty);

        } else if (difficulty.matches("exit")) {
            quitGame(round.getRoundScore());
        } else {
            System.out.println("Wrong input. Restarting the game...");
            initiateNew(true);
        }
    }

    public static void roundWon(int roundScore, boolean firstTry) {
        finalScore += roundScore;
        if (firstTry) {
            System.out.print("""
                    Congratulations!
                    You guessed the number on the first try!
                                       
                    You earned 100 points.
                                        
                    """);
        } else {
            System.out.println(STR."You guessed the number and have earned \{roundScore} points!");
        }
        System.out.println(STR."Your score after \{numberOfPlays} number of games is \{finalScore}");
        System.out.println("Do you want to play another round? ... ");
        playAnotherRound();

    }

    private static void playAnotherRound() {

        String another = scanner.nextLine();
        if (another.equals("yes")) {
            initiateNew(false);
        } else {
            quitGame(0);
            scanner.close();
        }
    }

    static void quitGame(int roundScore) {
        finalScore += roundScore;
        System.out.println(STR."You played \{numberOfPlays} games and have a final score of: \{finalScore}");
    }

    private static void playRound() {
        numberOfPlays++;
        String firstGuessString = scanner.nextLine();
        String firstGuess = Round.firstGuess(firstGuessString);
        if (firstGuess.equals("win")) {
            int roundScore = round.getRoundScore();
            roundWon(roundScore, true);
        } else if (firstGuess.equals("continue")) {
            round.continueGuessing(getHints);
        } else {
            quitGame(0);
        }

    }

}
