package restaurantrush;

public class Chef {
    private boolean hasActed;

    public void startTurn() {
        this.hasActed = false;
    }

    public ActionResult prepare(Order o) {
        if (hasActed) {
            return ActionResult.fail("Chef has already worked on this turn.");
        } else if (!o.canAdvance()) {
            return ActionResult.fail("Order cannot advance.");
        } else {
            try {
                o.advanceOneUnit();
            } catch (IllegalOrderStateException e) {
                return ActionResult.fail(e.getMessage());
            }
            hasActed = true;
        }
        return ActionResult.ok();
    }
}
