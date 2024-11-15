import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Round {

    private static int roundScore;
    private boolean numberHasBeenGuessed;
    private static int theNumber;
    private static int guesses = 0;

    public Round() {
        roundScore = 100;
        numberHasBeenGuessed = false;
    }

    public static void chooseDifficulty(String s) {
        int i = 0;

        switch (s) {
            case "easy" -> i = 10;
            case "normal" -> i = 100;
            case "expert" -> i = 1000;
        }

        theNumber = generateRandom(i);
        System.out.print(STR.
                """
                        Difficulty has been selected.
                                     
                        Generating a random number.. A number has been generated.
                        The number lies somewhere between 0 and \{i}

                        Good luck in the game! [if you want to give up, put in "exit" at any time]

                        Put in your first guess for 100 points: ...
                        """
        );
    }

    private static int generateRandom(int upper) {
        Random newRandom = new Random();
        return newRandom.nextInt(1, upper);
    }

    public static String firstGuess(String first) {
        try {
            int firstGuess = Integer.parseInt(first);
            guesses++;
            if (firstGuess == theNumber) {
                return "win";
            } else {
                System.out.println("Too bad, that did not match the number.");
                roundScore -= 10;
                System.out.println("Try again: ...");
                return "continue";
            }
        } catch (NullPointerException e) {
            System.out.println("Game exited.");
            return "quit";
        } catch (IllegalArgumentException | InputMismatchException e) {
            System.out.println("Whoops that input was invalid.. let's try that again");
            return "continue";
        }
    }

    public void continueGuessing(boolean hints) {
        Scanner scanner = new Scanner(System.in);
        while (!numberHasBeenGuessed) {
            try {
                int nextLine = scanner.nextInt();
                guesses++;
                if (nextLine != theNumber) {
                    System.out.println("Too bad, that did not match the number.");
                    roundScore -= 10;
                    if (hints && (guesses < 5)) {
                        if (nextLine > theNumber) {
                            System.out.println("Try a lower number.");
                        } else if (nextLine < theNumber) {
                            System.out.println("Try a higher number.");
                        }
                    } else if (hints) {
                        int rounded = (int) Math.abs(Math.round((theNumber - nextLine) / 10.0) * 10);
                        if (rounded == 0) {
                            rounded = (int) Math.abs(Math.round((theNumber - nextLine) / 2.0) * 2);
                        }
                        if (nextLine > theNumber) {
                            System.out.println(STR."Try a lower number. You're about \{rounded} numbers off.");
                        } else if (nextLine < theNumber) {
                            System.out.println(STR."Try a higher number. You're about \{rounded} numbers off.");
                        }
                    }
                } else {
                    numberHasBeenGuessed = true;
                    Game.roundWon(roundScore, false);
                }
            } catch (NullPointerException e) {
                Game.quitGame(roundScore);
                scanner.close();
            } catch (IllegalArgumentException | InputMismatchException e) {
                System.out.println("Whoops that input was invalid... breaking connection.");
                Game.quitGame(roundScore);
                scanner.close();
            }
        }
    }

    public int getRoundScore() {
        return roundScore;
    }

}
