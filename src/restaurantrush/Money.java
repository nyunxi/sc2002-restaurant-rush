package restaurantrush;

public final class Money {

    private Money() {
        // No objects: call Money.format(...) directly.
    }

    public static String format(int cents) {
        String sign = cents < 0 ? "-" : "";
        int abs = Math.abs(cents);
        return String.format("%s$%d.%02d", sign, abs / 100, abs % 100);
    }
}
