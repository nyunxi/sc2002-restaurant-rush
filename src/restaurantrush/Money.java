package restaurantrush;

/**
 * Money helper. Every amount in the game (prices, bills, revenue) is stored as
 * a whole number of cents in an {@code int}, so there is never a rounding error
 * from {@code double} arithmetic (brief 2.2). This class turns cents into
 * dollar text with two decimals for display.
 *
 * A {@code final} class with a {@code private} constructor and only
 * {@code static} methods: it is a toolbox, not something you create objects
 * from (the same idea as {@code java.lang.Math}).
 *
 * Owner: B (Menu and pricing)
 */
public final class Money {

    private Money() {
        // No objects: call Money.format(...) directly.
    }

    /**
     * Formats cents as dollars with two decimals.
     * Examples: 1800 -> "$18.00", 1080 -> "$10.80", 5 -> "$0.05".
     */
    public static String format(int cents) {
        String sign = cents < 0 ? "-" : "";
        int abs = Math.abs(cents);
        return String.format("%s$%d.%02d", sign, abs / 100, abs % 100);
    }
}
