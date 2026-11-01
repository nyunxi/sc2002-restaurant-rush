package restaurantrush;

/**
 * <<entity>> One customer's order for one menu item.
 *
 * Responsibilities
 *  1. Lock the bill the moment the order is taken. Later price rules never
 *     change an order that already exists.
 *  2. Track preparation progress, one Chef unit at a time, and become READY
 *     when the last unit is done.
 *  3. Allow only valid status changes:
 *     IN_PREPARATION -> READY -> SERVED -> PAID, or IN_PREPARATION/READY -> CANCELLED.
 *
 * Encapsulation: status and progress are private, with no setters. The only
 * way to change them is through methods that check the rules first, so no
 * other class can put an order into an impossible state.
 *
 * Like every entity, Order never reads input or prints (lecture: BCE).
 *
 * Owner: B (Menu and pricing)
 */
public class Order {

    /** Shared by all orders (static), so each new order gets the next number. */
    private static int nextOrderNumber = 1;

    private final int orderNumber;
    private final Customer customer;
    private final MenuItem item;
    private final int billCents;   // locked when the order is taken
    private int unitsDone;
    private OrderStatus status;

    /**
     * Creates the order when the Waiter takes it. The bill is locked now.
     *
     * @throws IllegalArgumentException if customer or item is null
     */
    public Order(Customer customer, MenuItem item) {
        if (customer == null) {
            throw new IllegalArgumentException("An order needs a customer.");
        }
        if (item == null) {
            throw new IllegalArgumentException("An order needs a menu item.");
        }
        this.orderNumber = nextOrderNumber++;
        this.customer = customer;
        this.item = item;
        this.billCents = item.getPriceCents();
        this.unitsDone = 0;
        this.status = OrderStatus.IN_PREPARATION;
    }

    // ------------------------------------------------------------------
    // Chef
    // ------------------------------------------------------------------

    /** True if the Chef may work on this order this turn. */
    public boolean canAdvance() {
        return status == OrderStatus.IN_PREPARATION;
    }

    /**
     * The Chef does one preparation unit. When the last unit is done the
     * order becomes READY.
     *
     * @return true if this unit finished the dish (the order is now READY)
     * @throws IllegalOrderStateException if the order is not IN_PREPARATION
     */
    public boolean advanceOneUnit() {
        requireStatus(OrderStatus.IN_PREPARATION, "prepared");
        unitsDone++;
        if (unitsDone >= item.getPrepUnits()) {
            status = OrderStatus.READY;
        }
        return status == OrderStatus.READY;
    }

    // ------------------------------------------------------------------
    // Waiter
    // ------------------------------------------------------------------

    /** True if the dish is cooked and waiting to be served. */
    public boolean isReady() {
        return status == OrderStatus.READY;
    }

    /**
     * The Waiter serves the dish.
     *
     * @throws IllegalOrderStateException if the order is not READY
     */
    public void markServed() {
        requireStatus(OrderStatus.READY, "served");
        status = OrderStatus.SERVED;
    }

    // ------------------------------------------------------------------
    // Cashier
    // ------------------------------------------------------------------

    /**
     * The Cashier collects payment. The amount to add to revenue is
     * {@link #getBillCents()}.
     *
     * @throws IllegalOrderStateException if the order has not been SERVED
     */
    public void markPaid() {
        requireStatus(OrderStatus.SERVED, "paid for");
        status = OrderStatus.PAID;
    }

    // ------------------------------------------------------------------
    // Abandonment
    // ------------------------------------------------------------------

    /** True if the order can still be cancelled (IN_PREPARATION or READY). */
    public boolean canCancel() {
        return status.isCancellable();
    }

    /**
     * Cancels the order because the customer left without being served.
     * A cancelled order never adds to revenue.
     *
     * @throws IllegalOrderStateException if the food was already served or paid for
     */
    public void cancel() {
        if (!canCancel()) {
            throw new IllegalOrderStateException(describe() + " cannot be cancelled: it is already "
                    + status.getLabel() + ".");
        }
        status = OrderStatus.CANCELLED;
    }

    // ------------------------------------------------------------------
    // Getters (read-only access)
    // ------------------------------------------------------------------

    public int getOrderNumber() {
        return orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem getItem() {
        return item;
    }

    /** The locked bill in cents. Add this to revenue on payment. */
    public int getBillCents() {
        return billCents;
    }

    /** The locked bill as text, e.g. "$18.00". */
    public String getBillDisplay() {
        return Money.format(billCents);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getUnitsDone() {
        return unitsDone;
    }

    public int getUnitsRemaining() {
        return item.getPrepUnits() - unitsDone;
    }

    /** Progress text for the CLI, e.g. "1/2 units". */
    public String getProgress() {
        return unitsDone + "/" + item.getPrepUnits() + " units";
    }

    public boolean isServed() {
        return status == OrderStatus.SERVED;
    }

    public boolean isPaid() {
        return status == OrderStatus.PAID;
    }

    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    /** e.g. "Order #1: Pasta, $18.00, In preparation (1/2 units)". */
    @Override
    public String toString() {
        String text = describe() + ", " + getBillDisplay() + ", " + status.getLabel();
        if (status == OrderStatus.IN_PREPARATION) {
            text += " (" + getProgress() + ")";
        }
        return text;
    }

    // ------------------------------------------------------------------
    // Private helpers
    // ------------------------------------------------------------------

    /** "Order #3 (Pasta)" */
    private String describe() {
        return "Order #" + orderNumber + " (" + item.getName() + ")";
    }

    /** Guards every status change so an illegal one explains itself. */
    private void requireStatus(OrderStatus required, String action) {
        if (status != required) {
            throw new IllegalOrderStateException(describe() + " cannot be " + action + ": it is "
                    + status.getLabel() + ", but must be " + required.getLabel() + ".");
        }
    }
}
