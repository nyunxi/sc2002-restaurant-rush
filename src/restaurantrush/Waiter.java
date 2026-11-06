package restaurantrush;

import java.util.List;

public class Waiter {
    private boolean hasActed;

    public void startTurn() {
        this.hasActed = false;
    }

    // Task 1: Seat a queued customer at a table
    public ActionResult seat(Customer c, Table t) {
        // If task is already used, return failure
        if (this.hasActed == true) {
            return ActionResult.fail("Waiter is busy.");
        }
        ActionResult result = t.seat(c);
        if (result.isSuccess()) {
            c.setState(customerState.SEATED);
            this.hasActed = true;
        }
        return result;
    }

    // Task 2: Take a seated customer's order
    public ActionResult takeOrder(Customer c, MenuItem[] menu, List<Order> orders) {
        // Fail if the task is already used
        if (hasActed) {
            return ActionResult.fail("Waiter is occupied.");
        } else if(c.getState() != CustomerState.SEATED) {
            return ActionResult.fail("Customer has not been seated.");
        } else {
            MenuItem item = c.chooseItem(menu);
            new Order(c, item);
            orders.add(new Order(c, item));
            c.setState(CustomerState.ORDERED);
            this.hasActed = true;
            return ActionResult.ok();
        }
    }
    
    // Task 3: Serve an order (READY)
    public ActionResult serve(Order o) {
        if (this.hasActed) {
            return ActionResult.fail("Waiter has already used this turn's task.");
        } else if (!o.isReady()) {
            return ActionResult.fail("Order is not ready yet.");
        }
        try {
            o.markServed();
        } catch (IllegalOrderStateException e){
            return ActionResult.fail(e.getMessage());
        }
        o.getCustomer().setState(CustomerState.EATING);
        this.hasActed = true;
        return ActionResult.ok();
    }
}
