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

    public static int percentOff(int cents, int percent) {
        if (cents < 0) {
            throw new IllegalArgumentException("Amount cannot be negative: " + cents);
        }
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100 percent, got " + percent);
        }
        return (cents * (100 - percent) + 50) / 100;
    }

    public static int applyDiscountRate(int cents, double rate) {
        return percentOff(cents, (int) Math.round(rate * 100));
}
