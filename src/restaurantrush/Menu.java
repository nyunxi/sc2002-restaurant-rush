package restaurantrush;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <<entity>> The restaurant's menu: an ordered list of {@link MenuItem}s that
 * customers choose from.
 *
 * Composition: the Menu creates and owns its items in its constructor.
 * Encapsulation: the list is private, and {@link #getItems()} hands out a
 * read-only view, so no other class can add or remove dishes.
 *
 * Owner: B (Menu and pricing)
 */
public class Menu {

    /** Names of the base dishes, so other classes need not type the strings. */
    public static final String SALAD = "Salad";
    public static final String BURGER = "Burger";
    public static final String PASTA = "Pasta";

    private final List<MenuItem> items = new ArrayList<MenuItem>();

    /**
     * Creates the base-setting menu from brief 2.1, in this order:
     * Salad $9.00 / 1 unit, Burger $15.00 / 1 unit, Pasta $18.00 / 2 units.
     */
    public Menu() {
        items.add(new MenuItem(SALAD, 900, 1));
        items.add(new MenuItem(BURGER, 1500, 1));
        items.add(new MenuItem(PASTA, 1800, 2));
    }

    /** All items in menu order (0 = Salad, 1 = Burger, 2 = Pasta). The list is read-only. */
    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Item at a 0-based position (0 = first item).
     *
     * @throws IndexOutOfBoundsException if there is no item at that position
     */
    public MenuItem getItem(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("No menu item at position " + index
                    + "; valid positions are 0 to " + (items.size() - 1) + ".");
        }
        return items.get(index);
    }

    /** The item with this name (case ignored), or {@code null} if there is none. */
    public MenuItem findByName(String name) {
        if (name == null) {
            return null;
        }
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name.trim())) {
                return item;
            }
        }
        return null;
    }

    /** Number of items on the menu. Use this rather than a hard-coded 3. */
    public int size() {
        return items.size();
    }

    /** One numbered line per item, e.g. "1. Salad ($9.00, 1 unit)". */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(i + 1).append(". ").append(items.get(i));
        }
        return sb.toString();
    }
}
