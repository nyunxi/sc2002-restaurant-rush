package restaurantrush;

/**
 * <<enumeration>> The life cycle of an {@link Order}.
 *
 * <pre>
 *   IN_PREPARATION --last unit done--> READY --Waiter serves--> SERVED --Cashier--> PAID
 *         |                              |
 *         +------ customer leaves -------+----> CANCELLED
 * </pre>
 *
 * These five values were agreed by the whole team in Phase 0. Every other
 * component (turn engine, customers, CLI) depends on them, so do not rename
 * them without telling everyone.
 *
 * Owner: B (Menu and pricing)
 */
public enum OrderStatus {
    IN_PREPARATION("In preparation"),
    READY("Ready to serve"),
    SERVED("Served"),
    PAID("Paid"),
    CANCELLED("Cancelled");

    // An enum can hold fields and methods like any class.
    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    /** Human-friendly text for the CLI, e.g. "Ready to serve". */
    public String getLabel() {
        return label;
    }

    /**
     * True while the customer has not received the food yet. Only these
     * orders may be cancelled when a customer walks out (brief 3.2 step 5).
     */
    public boolean isCancellable() {
        return this == IN_PREPARATION || this == READY;
    }

    /** True once nothing more can happen to the order (PAID or CANCELLED). */
    public boolean isClosed() {
        return this == PAID || this == CANCELLED;
    }
}
