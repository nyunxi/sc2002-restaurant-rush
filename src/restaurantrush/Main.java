package restaurantrush;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputReader input = new InputReader(scanner);

        // GameController takes the menu as an array; Menu stores it as a List.
        MenuItem[] menu = new Menu().getItems().toArray(new MenuItem[0]);
        GameController game = new GameController(menu);

        try {
            new ConsoleUI(game, input).run();
        } catch (GameQuitException e) {
            System.out.println();
            System.out.println("Game stopped at turn " + game.getTurn() + ". No result for an unfinished game.");
        } catch (IllegalStateException e) {
            // InputReader throws this when input ends (Ctrl+D, or a test file that is too short)
            System.out.println();
            System.out.println("Input ended at turn " + game.getTurn() + ". Game stopped.");
        }
    }
}
