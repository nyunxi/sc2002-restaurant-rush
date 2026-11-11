package restaurantrush;

public class VIPCustomer extends Customer {

    public VIPCustomer(int customerID) {
        super(customerID);
    }

    @Override 
    public int getWaitingLoss() { 
        return 5; //get 5 instead of 8 because Java chooses getWaitingLoss() to run from the object's runtime type not the declared type (dynamic dispatch)
    }

    @Override 
    public double getDiscountRate() {
        return 0.10;
    }

    @Override 
    public String getCustomerType() {
        return "VIP";
    }
}