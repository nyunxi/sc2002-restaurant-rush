// Boundary class: shows game state, reads the manager's choices and passes them to GameController.
// Contains no game rules. Every rule check happens in GameController / Waiter / Chef / Cashier,
// and a failure comes back as an ActionResult whose reason is printed here.

package restaurantrush;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {
    private final GameController game;
    private final InputReader input;

    public ConsoleUI(GameController game, InputReader input) {
        this.game = game;
        this.input = input;
    }

    public void run() {
        System.out.println("=== Restaurant Rush ===");
        showMenu();
        while (!game.isGameOver()) {
            game.startTurn();
            showTurnState();
            askHappyHour();
            askHost();
            askWaiter();
            askChef();
            game.endTurn();
            showTurnResults();
        }
        showFinalResult();
    }

    // ---------- Start-of-turn display ----------

    private void showMenu(){
        System.out.println("Menu:");
        for (MenuItem item: game.getMenu()){
            System.out.println(" " + item);
        }
    }

    private void showTurnState() {
        System.out.println();
        System.out.println("========== Turn " + game.getTurn() + " of " + GameController.TOTAL_TURNS + " ==========");
        System.out.println("Revenue: " + formatCents(game.getRevenueCents()));
        System.out.println("Average satisfaction (paid): " + formatAverage());
        System.out.println(happyHourStatus());

        System.out.println("Waiting queue:");
        List<Customer> queue = game.getQueue();
        if (queue.isEmpty()) {
            System.out.println("  (empty)");
        }
        for (Customer c : queue) {
            System.out.println("  " + describeCustomer(c));
        }

        System.out.println("Tables:");
        for (Table t : game.getTables()) {
            if (t.isFree()) {
                System.out.println("  Table " + t.getTableId() + ": free");
            } else {
                System.out.println("  Table " + t.getTableId() + ": " + describeCustomer(t.getCustomer()));
            }
        }

        System.out.println("Staff this turn: Host (seats 1), Waiter (1 task), Chef (1 preparation unit), Cashier (automatic)");
    }

    // ---------- Manager choices ----------

    private void askWaiter() {
        while (true) {
            System.out.println();
            System.out.println("Waiter: 1 Seat a customer | 2 Take an order | 3 Serve a ready order | 0 Wait | q Quit");
            int task = input.readInt("Choose Waiter task: ", 0, 3);

            if (task == 0) {
                game.waiterWait();      // controller must know the Waiter is done, or the Chef is blocked
                System.out.println("The Waiter waits.");
                return;
            }

            String result;
            if (task == 1) {
                result = chooseSeat();
            } else if (task == 2) {
                result = chooseTakeOrder();
            } else {
                result = chooseServe();
            }

            if (result != null) {
                System.out.println(result);
                return;                 // task used up
            }
            // null: nothing to choose from, or the action failed. Ask again; task not used up.
        }
    }

    // Each choose method returns a success message, or null if nothing happened.

    private String chooseSeat() {
        Customer customer = pickCustomer("Seat which customer?", game.getQueue());
        if (customer == null) {
            return null;
        }
        Table table = pickTable();
        ActionResult r = game.waiterSeat(customer, table);
        if (!report(r)) {
            return null;
        }
        return "Waiter seated Customer " + customer.getCustomerId() + " at Table " + table.getTableId() + ".";
    }

    private String chooseTakeOrder() {
        Customer customer = pickCustomer("Take whose order?", seatedCustomers());
        if (customer == null) {
            return null;
        }
        ActionResult r = game.waiterTakeOrder(customer);
        if (!report(r)) {
            return null;
        }
        Order order = customer.getOrder();
        return "Waiter took Customer " + customer.getCustomerId() + "'s order: "
                + (order == null ? "?" : order.getItem().getName() + ", " + order.getBillDisplay()
                    + (order.isHappyHourPrice() ? " (Happy Hour price)" : "")) + "."; //used ternary operator where if order is null, print "?" else print the order itembu
    }

    private String chooseServe() {
        Order order = pickOrder("Serve which order?");
        if (order == null) {
            return null;
        }
        ActionResult r = game.waiterServe(order);
        if (!report(r)) {
            return null;
        }
        return "Waiter served " + order.getItem().getName()
                + " to Customer " + order.getCustomer().getCustomerId() + ".";
    }

    private void askChef() {
        while (true) {
            System.out.println();
            System.out.println("Chef: 1 Prepare an order | 0 Wait | q Quit");
            int task = input.readInt("Choose Chef task: ", 0, 1);

            if (task == 0) {
                System.out.println("The Chef waits.");
                return;
            }

            Order order = pickOrder("Prepare which order?");
            if (order == null) {
                continue;
            }
            ActionResult r = game.chefPrepare(order);
            if (report(r)) {
                System.out.println("Chef worked on " + order.getItem().getName() + " for Customer "
                        + order.getCustomer().getCustomerId() + ": " + order.getProgress()
                        + (order.isReady() ? ", READY" : "") + ".");
                return;
            }
        }
    }
    
    private void askHost() {
        if (!game.canHostSeat()) {
            return;                       // nobody to seat or no free table: skip the prompt
        }
        while (true) {
            System.out.println();
            System.out.println("Host: 1 Seat a queued customer | 0 Skip | q Quit");
            int task = input.readInt("Choose Host task: ", 0, 1);
            if (task == 0) {
                System.out.println("The Host seats no one.");
                return;
            }
            Customer customer = pickCustomer("Host seats which customer?", game.getQueue());
            Table table = pickTable();
            if (report(game.hostSeat(customer, table))) {
                System.out.println("Host seated Customer " + customer.getCustomerId()
                        + " at Table " + table.getTableId() + ".");
                return;
            }
        }
    }

    private void askHappyHour(){
        if (!game.canActivateHappyHour()){
            return;
        }
        System.out.println();
        System.out.println("Happy Hour: 20% off orders taken, +15 satisfaction for waiting customers, lasts 2 turns. Once per game.");
        int choice = input.readInt("Activate Happy Hour now? (1 Yes/ 0 No): ", 0, 1);
        if (choice ==1 && report(game.activateHappyHour())){
            System.out.println("Happy Hour is ON. " + happyHourStatus());
        }
    }

    private String happyHourStatus(){
        if (game.isHappyHourActive()){
            return "Happy Hour: ACTIVE, " + game.getHappyHourTurnsRemaining() + " turn(s) left including this one";
        }
        return game.isHappyHourUsed() ? "Happy Hour: used" : "Happy Hour: available (once per game)";
    }

    // ---------- Selection helpers ----------

    // Prints the failure reason. Returns true if the action succeeded.
    private boolean report(ActionResult r) {
        if (r.isSuccess()) {
            return true;
        }
        System.out.println("Not possible: " + r.getReason());
        return false;
    }

    private Customer pickCustomer(String question, List<Customer> options) {
        if (options.isEmpty()) {
            System.out.println("No customers to choose from.");
            return null;
        }
        System.out.println(question);
        for (int i = 0; i < options.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + describeCustomer(options.get(i)));
        }
        int choice = input.readInt("Choose (1-" + options.size() + "): ", 1, options.size());
        return options.get(choice - 1);
    }

    // Lists orders that are still open (not PAID or CANCELLED). Whether the chosen order can be
    // prepared or served is decided by the controller, not here.
    private Order pickOrder(String question) {
        List<Order> open = new ArrayList<>();
        for (Order o : game.getOrders()) {
            if (!o.getStatus().isClosed()) {
                open.add(o);
            }
        }
        if (open.isEmpty()) {
            System.out.println("No orders to choose from.");
            return null;
        }
        System.out.println(question);
        for (int i = 0; i < open.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + describeOrder(open.get(i)));
        }
        int choice = input.readInt("Choose (1-" + open.size() + "): ", 1, open.size());
        return open.get(choice - 1);
    }

    private Table pickTable() {
        List<Table> tables = game.getTables();
        int choice = input.readInt("Which table (1-" + tables.size() + ")? ", 1, tables.size());
        return tables.get(choice - 1);
    }

    // Everyone sitting at a table. The controller still checks they are SEATED.
    private List<Customer> seatedCustomers() {
        List<Customer> list = new ArrayList<>();
        for (Table t : game.getTables()) {
            if (!t.isFree()) {
                list.add(t.getCustomer());
            }
        }
        return list;
    }

    // ---------- End-of-turn display ----------

    private void showTurnResults() {
        System.out.println();
        System.out.println("--- End of turn " + game.getTurn() + " ---");
        System.out.println("Served: " + game.getServedCount()
                + " | Paid: " + game.getPaidCount()
                + " | Unhappy departures: " + game.getUnhappyDepartures());
        System.out.println("Revenue: " + formatCents(game.getRevenueCents())
                + " | Average satisfaction (paid): " + formatAverage());
        System.out.println("Turns used: " + game.getTurn() + " of " + GameController.TOTAL_TURNS);
    }

    private void showFinalResult() {
        System.out.println();
        System.out.println("========== GAME OVER ==========");
        System.out.println(game.isVictory() ? "VICTORY" : "DEFEAT");
        System.out.println("Paid customers: " + game.getPaidCount() + " (need 4)");
        System.out.println("Revenue: " + formatCents(game.getRevenueCents()) + " (need $45.00)");
        System.out.println("Average satisfaction (paid): " + formatAverage() + " (need 60)");
        System.out.println("Customers:");
        for (Customer c : game.getCustomers()) {
            System.out.println("  " + describeCustomer(c));
        }
    }

    // ---------- Formatting helpers ----------

    private String describeCustomer(Customer c) {
        String text = "Customer " + c.getCustomerId()
                + " (" + c.getCustomerType() + ")"
                + " | satisfaction " + c.getSatisfaction()
                + " | " + c.getState();
        Order order = c.getOrder();
        if (order != null) {
            text += " | " + order.getItem().getName() + " " + order.getProgress()
                    + " " + order.getStatus().getLabel();
        }
        return text;
    }

    private String describeOrder(Order o) {
        return "Customer " + o.getCustomer().getCustomerId() + ": " + o;
    }

    private String formatAverage() {
        if (game.getPaidCount() == 0) {
            return "N/A";
        }
        return String.format("%.1f", game.getAverageSatisfaction());
    }

    public static String formatCents(int cents) {
        return Money.format(cents);
    }
}
