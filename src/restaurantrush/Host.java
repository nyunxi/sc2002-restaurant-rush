package restaurantrush;

public class Host {
    private boolean hasActed;

    public void startTurn(){
        hasActed = false;
    }

    public boolean hasActed(){
        return hasActed;
    }

    public ActionResult seat(Customer c, Table t) {
        if (hasActed) {
            return ActionResult.fail("The Host has already seated a customer this turn.");
        }
        if (c.getState() != CustomerState.WAITING) {
            return ActionResult.fail("Customer is not waiting to be seated.");
        }
        ActionResult result = t.seat(c);
        if (result.isSuccess()) {
            c.seat();
            hasActed = true;
        }
        return result;
    }
}
