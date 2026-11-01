package restaurantrush;

/**
 * <<entity>> One dish on the menu: its name, its price in cents, and how many
 * preparation units the Chef must spend on it.
 *
 * Encapsulation: every field is {@code private final}, so a dish cannot be
 * changed once it exists. The menu price therefore stays fixed for the whole
 * game; what a customer actually pays is worked out and locked on the
 * {@link Order}, never by editing the MenuItem.
 *
 * Owner: B (Menu and pricing)
 */
public class MenuItem {

    private final String name;
    private final int priceCents;
    private final int prepUnits;

    /**
     * @param name       e.g. "Pasta"
     * @param priceCents price in cents, e.g. 1800 for $18.00
     * @param prepUnits  Chef units needed, at least 1
     * @throws IllegalArgumentException if any value is invalid
     */
    public MenuItem(String name, int priceCents, int prepUnits) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("A menu item needs a name.");
        }
        if (priceCents <= 0) {
            throw new IllegalArgumentException("Price must be a positive number of cents, got " + priceCents + ".");
        }
        if (prepUnits < 1) {
            throw new IllegalArgumentException("Preparation units must be at least 1, got " + prepUnits + ".");
        }
        this.name = name.trim();
        this.priceCents = priceCents;
        this.prepUnits = prepUnits;
    }

    public String getName() {
        return name;
    }

    /** Menu price in cents. */
    public int getPriceCents() {
        return priceCents;
    }

    /** Number of Chef turns (one unit per turn) needed to make this dish. */
    public int getPrepUnits() {
        return prepUnits;
    }

    /** Menu price as text, e.g. "$18.00". */
    public String getPriceDisplay() {
        return Money.format(priceCents);
    }

    /** e.g. "Pasta ($18.00, 2 units)". Overrides Object.toString(). */
    @Override
    public String toString() {
        return name + " (" + getPriceDisplay() + ", " + prepUnits + (prepUnits == 1 ? " unit)" : " units)");
    }
}
