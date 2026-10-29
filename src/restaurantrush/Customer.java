package restaurantrush;

public class Customer {
    private int customerId;
    private int satisfaction;
    private CustomerState state;

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

    public CustomerState getState() {
		return state;
	}

	public void loseWaitingSatisfaction() {
		satisfaction -= 8;

		if (satisfaction < 0) {
			satisfaction = 0;
		}
	}

    public Boolean hasLeft() {
		return satisfaction == 0;
	}

	public MenuItem chooseItem(MenuItem[] menu) {
		return menu[customerId % menu.length];
	}

	public void setState(CustomerState state) {
		this.state = state;
	}

	public void finishEating() {
		state = CustomerState.READY_TO_PAY;
	}


}