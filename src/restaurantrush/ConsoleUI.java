package restaurantrush;

public class ConsoleUI {

    public static String formatCents(int cents) {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }
}