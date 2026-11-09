package restaurantrush;

public class Customer {
    private final int customerId;
    private int satisfaction;
    private CustomerState state;
    private Order order; // null until the Waiter takes the order

    public Customer(int customerId) {
        this.customerId = customerId;
        this.satisfaction = 100;
        this.state = CustomerState.WAITING;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

	public double getDiscountRate() {
		return 0.0;
	}

	public String getCustomerType() {
		return "Regular";
	}

	public int getWaitingLoss() {
		return 8;
	}

    public CustomerState getState() {
        return state;
    }

    public Order getOrder() {
        return order;
    }

    public MenuItem chooseItem(MenuItem[] menu) {
        return menu[(customerId - 1) % menu.length];
    }

    // ---------- State changes ----------
    // Each method allows only one move, so no class can skip a step (e.g. WAITING straight to PAID).
    // Staff check the state before calling these, so an exception here means a bug, not a bad choice.

    public void seat() {
        requireState(CustomerState.WAITING, "be seated");
        state = CustomerState.SEATED;
    }

    public void placeOrder(Order order) {
        requireState(CustomerState.SEATED, "order");
        if (order == null || order.getCustomer() != this) {
            throw new IllegalArgumentException("Customer " + customerId + " can only place their own order.");
        }
        this.order = order;
        state = CustomerState.ORDERED;
    }

    public void markServed() {
        requireState(CustomerState.ORDERED, "be served");
        state = CustomerState.EATING;
    }

    public void finishEating() {
        requireState(CustomerState.EATING, "finish eating");
        state = CustomerState.READY_TO_PAY;
    }

    public void markPaid() {
        requireState(CustomerState.READY_TO_PAY, "pay");
        state = CustomerState.PAID;
    }

    public void leave() {
        if (state == CustomerState.PAID || state == CustomerState.LEFT) {
            throw new IllegalStateException("Customer " + customerId + " cannot leave: already " + state + ".");
        }
        state = CustomerState.LEFT;
    }

    // ---------- Waiting ----------

    public void loseWaitingSatisfaction() {
        satisfaction -= getWaitingLoss();
        if (satisfaction < 0) {
            satisfaction = 0;
        }
    }

    public boolean hasLeft() {
        return satisfaction == 0;
    }

    private void requireState(CustomerState required, String action) {
        if (state != required) {
            throw new IllegalStateException("Customer " + customerId + " cannot " + action
                    + ": is " + state + ", must be " + required + ".");
        }
    }
}
