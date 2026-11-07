package restaurantrush;

public class Cashier {
    private boolean hasActed;
    private int revenueCents;
    private int paidCount;
    private int satisfactionTotal;

    public void startTurn() {
        hasActed = false;
    }

    public ActionResult processPayment(Order o, Table t) {
        if (hasActed) {
            return ActionResult.fail("Cashier has already taken a payment this turn.");
        }
        Customer c = o.getCustomer();
        if (c.getState() != CustomerState.READY_TO_PAY) {
            return ActionResult.fail("Customer is not ready to pay yet.");
        }
        try {
            o.markPaid(); // Put most fail-able step first
        } catch (IllegalOrderStateException e) {
            return ActionResult.fail(e.getMessage());
        }
        c.markPaid(); // Update state if successful
        t.free();
        revenueCents += o.getBillCents();
        satisfactionTotal += c.getSatisfaction(); // Update satisfaction total for average calculation (recorded at moment of payment)
        paidCount++;
        hasActed = true;
        return ActionResult.ok();
    }

    public int getRevenueCents() {return this.revenueCents;}

    public int getPaidCount() {return this.paidCount;}

    public double getAverageSatisfaction() {
        if (paidCount == 0) {return 0;}
        return (double) satisfactionTotal / paidCount;
    }
}


