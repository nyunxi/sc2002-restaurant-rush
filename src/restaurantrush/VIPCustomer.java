package restaurantrush;

public class VIPCustomer extends Customer {

    public VIPCustomer(int customerID) {
        super(customerID);
    }

    public int getWaitingLoss() { 
        return 5;
    }

    public double getDiscountRate() {
        return 0.10;
    }

    public String getCustomerType() {
        return "VIP";
    }
}