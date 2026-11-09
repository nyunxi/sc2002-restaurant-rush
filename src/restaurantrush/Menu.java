package restaurantrush;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        MenuItem salad = new MenuItem(SALAD, 900, 1);
        MenuItem burger = new MenuItem(BURGER, 1500, 1);
        MenuItem pasta = new MenuItem(PASTA, 1800, 2);
        items.add(salad);
        items.add(burger);
        items.add(pasta);
        items.add(new ComboMeal(salad, pasta));
        items.add(new ComboMeal(salad, burger));
    }

    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }

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

    public int size() {
        return items.size();
    }

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
