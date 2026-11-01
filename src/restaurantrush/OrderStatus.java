package restaurantrush;

public enum OrderStatus {
    IN_PREPARATION("In preparation"),
    READY("Ready to serve"),
    SERVED("Served"),
    PAID("Paid"),
    CANCELLED("Cancelled");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isCancellable() {
        return this == IN_PREPARATION || this == READY;
    }

    public boolean isClosed() {
        return this == PAID || this == CANCELLED;
    }
}
