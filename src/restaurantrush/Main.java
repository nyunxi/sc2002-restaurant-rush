package restaurantrush;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputReader input = new InputReader(scanner);

        // GameController takes the menu as an array; Menu stores it as a List.
        MenuItem[] menu = new Menu().getItems().toArray(new MenuItem[0]);
        GameController game = new GameController(menu);

        new ConsoleUI(game, input).run();
    }
}
