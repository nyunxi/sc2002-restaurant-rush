package restaurantrush;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComboMeal extends MenuItem {

    public static final int PRICE_PERCENT = 90;

    private final MenuItem first;
    private final MenuItem second;

    public ComboMeal(MenuItem first, MenuItem second) {
        super(comboName(first, second), comboPriceCents(first, second),
                first.getPrepUnits() + second.getPrepUnits());
        this.first = first;
        this.second = second;
    }

    public List<MenuItem> getComponents() {
        return Collections.unmodifiableList(Arrays.asList(first, second));
    }

    public int getSeparatePriceCents() {
        return first.getPriceCents() + second.getPriceCents();
    }

    @Override
    public String toString() {
        return super.toString() + " - " + first.getName() + " + " + second.getName()
                + ", " + (100 - PRICE_PERCENT) + "% off " + Money.format(getSeparatePriceCents());
    }

    private static String comboName(MenuItem first, MenuItem second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("A combo needs two menu items.");
        }
        return first.getName() + " + " + second.getName() + " Combo";
    }

    private static int comboPriceCents(MenuItem first, MenuItem second) {
        int separate = first.getPriceCents() + second.getPriceCents();
        return Money.percentOff(separate, 100 - PRICE_PERCENT);
    }
}
