package restaurantrush;

public class Order {

    private static int nextOrderNumber = 1;

    private final int orderNumber;
    private final Customer customer;
    private final MenuItem item;
    private final int billCents;   // locked when the order is taken
    private int unitsDone;
    private OrderStatus status;

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
        this.billCents = Money.applyDiscountRate(item.getPriceCents(), customer.getDiscountRate());
        this.unitsDone = 0;
        this.status = OrderStatus.IN_PREPARATION;
    }
    
    public boolean canAdvance() {
        return status == OrderStatus.IN_PREPARATION;
    }

    public boolean advanceOneUnit() {
        requireStatus(OrderStatus.IN_PREPARATION, "prepared");
        unitsDone++;
        if (unitsDone >= item.getPrepUnits()) {
            status = OrderStatus.READY;
        }
        return status == OrderStatus.READY;
    }
    
    public boolean isReady() {
        return status == OrderStatus.READY;
    }

    public void markServed() {
        requireStatus(OrderStatus.READY, "served");
        status = OrderStatus.SERVED;
    }

    public void markPaid() {
        requireStatus(OrderStatus.SERVED, "paid for");
        status = OrderStatus.PAID;
    }

    public boolean canCancel() {
        return status.isCancellable();
    }

    public void cancel() {
        if (!canCancel()) {
            throw new IllegalOrderStateException(describe() + " cannot be cancelled: it is already "
                    + status.getLabel() + ".");
        }
        status = OrderStatus.CANCELLED;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getBillCents() {
        return billCents;
    }

    public String getBillDisplay() {
        return Money.format(billCents);
    }
    
    public int getSavingsCents() {
        return item.getPriceCents() - billCents;
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
    
    @Override
    public String toString() {
        String text = describe() + ", " + getBillDisplay();
        if (getSavingsCents() > 0) {
            text += " (menu " + item.getPriceDisplay() + ")";
        }
        text += ", " + status.getLabel();
        if (status == OrderStatus.IN_PREPARATION) {
            text += " (" + getProgress() + ")";
        }
        return text;
    }

    private String describe() {
        return "Order #" + orderNumber + " (" + item.getName() + ")";
    }

    private void requireStatus(OrderStatus required, String action) {
        if (status != required) {
            throw new IllegalOrderStateException(describe() + " cannot be " + action + ": it is "
                    + status.getLabel() + ", but must be " + required.getLabel() + ".");
        }
    }
}
