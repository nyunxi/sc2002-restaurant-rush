package restaurantrush;

public class Table {
    private final int tableId;
    private Customer customer; // Not assigned final because table gets and then loses customers

    public Table(int tableId) {
        this.tableId = tableId;
    }

    public boolean isFree() {
        return (customer == null);
    }

    public ActionResult seat(Customer c) {
        if (this.customer != null) {
            return ActionResult.fail("Table " + tableId + "Seat is occupied.");
        } else {
            this.customer = c;
            return ActionResult.ok();
        }
    }

    public void free() {
        this.customer = null;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public int getTableId() {
        return this.tableId;
    }
}
