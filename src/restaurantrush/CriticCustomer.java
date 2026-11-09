package restaurantrush;

public class CriticCustomer extends Customer {

    public CriticCustomer(int customerID) {
        super(customerID);
    }

    @Override 
    public int getWaitingLoss() {
        return 12;
    }

    @Override 
    public String getCustomerType() {
        return "Critic";
    }

    @Override
    public MenuItem chooseItem(MenuItem[] menu) {
        if (menu == null || menu.length == 0) {
            throw new IllegalArgumentException("Menu cannot be empty.");
        }

        MenuItem chosen = menu[0];

        for(MenuItem item : menu) {
            if (item.getPriceCents() > chosen.getPriceCents()) {
                chosen = item;
            }
        }

        return chosen;
    }
}