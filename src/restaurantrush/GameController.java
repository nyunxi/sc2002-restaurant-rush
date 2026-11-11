package restaurantrush;

import java.util.*;

public class GameController {
    public static final int TOTAL_TURNS = 18; // Set constant for total number of turns allowed per round
    private static final int[] ARRIVAL_TURNS = {1, 4, 7, 10, 13, 16}; // Turns on which customers will arrive
    private static final int VIP_CUSTOMER_ID = 2; // Stage 2: Second arrival is the VIP customer
    private static final int CRITIC_CUSTOMER_ID = 4; // Stage 2: Fourth arrival is the Critic customer

    private final MenuItem[] menu;
    private final List<Customer> queue = new LinkedList<>(); // Waiting customers (FIFO)
    private final List<Customer> customers = new ArrayList<>(); // All customers
    private final Table[] tables = {new Table(1), new Table(2)};
    private final List <Order> orders = new ArrayList<>(); // Oldest first 
    private final Map<Order, Integer> servedOnTurn = new HashMap<>();
    private final Waiter waiter = new Waiter();
    private final Host host = new Host(); //Stage 3 addition
    private final Chef chef = new Chef();
    private final Cashier cashier = new Cashier();
    private int turn = 0;
    private int nextCustomerId = 1;
    private int unhappyDepartures = 0;
    private boolean waiterDone  =false; // Waiter must act/wait before the chef
    private final HappyHour happyHour = new HappyHour(); //Stage 3: Introduction of HappyHour
    private boolean turnInProgress = false; //true between startTurn() and endTurn()

    public GameController(MenuItem[] menu) {
        if (menu == null || menu.length == 0) {
            throw new IllegalArgumentException("The menu must have at least one item.");
        }
        for (MenuItem item : menu) {
            if (item == null) {throw new IllegalArgumentException("The menu cannot contain an empty entry.");}
        }
        this.menu = menu.clone(); // Defensive copy: later changes to the caller's array do not affect the game
    }

    // Step 1: Arrivals
    public void startTurn() {
        turn++;
        turnInProgress = true;
        for (int t : ARRIVAL_TURNS) {
            if (t == turn) {
                Customer c = createCustomer(nextCustomerId++); //new change for Stage 2
                queue.add(c);
                customers.add(c);
            }
        }
        waiter.startTurn();
        host.startTurn();
        chef.startTurn();
        cashier.startTurn();
        waiterDone = false;
        if (happyHour.isActive()){
            recoverWaitingCustomers();
        }
    }

    //Stage 2: Name the customer subclasses 
    private Customer createCustomer(int id){
        if (id == VIP_CUSTOMER_ID) {
            return new VIPCustomer(id);}
        if (id == CRITIC_CUSTOMER_ID) {
            return new CriticCustomer(id);}
        return new Customer(id); //everyone who is not a VIP or Critic is a regular customer
    }

    // Steps 2, 3: Manager choices (UI calls the methods below and re-prompts on failure)
    public ActionResult waiterSeat(Customer c, Table t) { // Waiter seats customer
        if (!queue.contains(c)) {return ActionResult.fail("That customer is not in the waiting queue.");}
        if (!Arrays.asList(tables).contains(t)) {return ActionResult.fail("That table is not in this restaurant.");}
        ActionResult r = waiter.seat(c,t);
        if (r.isSuccess()) {
            queue.remove(c);
            waiterDone = true;
        }
        return r;
    }

    // Stage 3: Host seats at most one queued customer per turn, on top of the Waiter's task
    public boolean canHostSeat() {
        return turnInProgress && !host.hasActed() && !queue.isEmpty() && hasFreeTable();
    }

    public ActionResult hostSeat(Customer c, Table t) {
        if (!turnInProgress) {return ActionResult.fail("The Host can only act during a turn.");}
        if (!queue.contains(c)) {return ActionResult.fail("That customer is not in the waiting queue.");}
        if (!Arrays.asList(tables).contains(t)) {return ActionResult.fail("That table is not in this restaurant.");}
        ActionResult r = host.seat(c, t);
        if (r.isSuccess()) {
            queue.remove(c);   // no waiterDone change: the Host does not use the Waiter's task
        }
        return r;
    }

    private boolean hasFreeTable() {
        for (Table t : tables) {
            if (t.isFree()) {return true;}
        }
        return false;
    }
    
    public ActionResult waiterTakeOrder(Customer c) { // Waiter takes customer's order
        if (!customers.contains(c)) {return ActionResult.fail("That customer is not in this restaurant.");}
        ActionResult r = waiter.takeOrder(c, menu, orders, happyHour);
        if (r.isSuccess()) {waiterDone = true;}
        return r;
    }

    public ActionResult waiterServe(Order o) { // Waiter serves order to correct customer
        if (!orders.contains(o)) {return ActionResult.fail("That order was not taken in this restaurant.");}
        ActionResult r = waiter.serve(o);
        if (r.isSuccess()) {
            servedOnTurn.put(o, turn); // Required to ensure that customer eats for a full turn before paying
            waiterDone = true;
        }
        return r;
    }

    public void waiterWait() {waiterDone = true;} // Waiter does nothing this turn

    public ActionResult chefPrepare(Order o) { // Chef prepares order
        if (!orders.contains(o)) {return ActionResult.fail("That order was not taken in this restaurant.");}
        if (!waiterDone) {
            return ActionResult.fail("The waiter must act (or wait) before the chef.");
        }
        return chef.prepare(o);
    }

    // Steps 4, 5, 6: Customers eat and pay cashiers, wait/are abandoned, and results are given
    public void endTurn() {
        payOldestReadyCustomer(); // Customer who becomes ready to pay before finishEating()
        finishEating(); // This turn must wait until next turn to pay
        applyWaitingLoss();
        happyHour.endTurn();
        turnInProgress = false; 
    }

    private void payOldestReadyCustomer() {
        for (Order o : orders) {
            Customer c = o.getCustomer();
            if (c.getState() == CustomerState.READY_TO_PAY) {
                cashier.processPayment(o, tableOf(c));
                return; // At most one payment can be made per turn
            }
        }
    }

    private void finishEating() {
        for (Order o : orders) {
            Customer c = o.getCustomer();
            Integer served = servedOnTurn.get(o);
            if (c.getState() == CustomerState.EATING && served != null && served < turn) {c.finishEating();}
        }
    }

    private void applyWaitingLoss() { // Penalty for customers who have to wait
        for (Customer c : customers) {
            CustomerState s = c.getState(); // Order served, eating and ready-to-pay customers lose nothing 
            if (s == CustomerState.WAITING || s == CustomerState.SEATED || s == CustomerState.ORDERED) {
                c.loseWaitingSatisfaction();
                if (c.hasLeft()) {leave(c);}
            }
        }
    }

    private void leave(Customer c) {
        queue.remove(c);
        for (Order o : orders) {
            if (o.getCustomer() == c && o.canCancel()) {o.cancel();}
        }
        Table t = tableOf(c);
        if (t != null) {t.free();}
        c.leave();
        unhappyDepartures++;
    }

    private Table tableOf(Customer c) {
        for (Table t : tables) {
            if (t.getCustomer() == c) {return t;}
        }
        return null;
    }

    public boolean canActivateHappyHour(){
        return happyHour.canActivate() && turnInProgress && !waiterDone;
    }

    public boolean isHappyHourUsed(){
        return happyHour.hasBeenUsed();
    }

    public ActionResult activateHappyHour() {
        if (!happyHour.canActivate()) {
            return ActionResult.fail("Happy Hour has already been used this game.");
        }
        if (!turnInProgress || waiterDone) {
            return ActionResult.fail("Happy Hour can only be switched on at the start of a turn.");
        }
        happyHour.activate();
        recoverWaitingCustomers();       // first active turn
        return ActionResult.ok();
    }

    public boolean isHappyHourActive() { return happyHour.isActive(); }
    public int getHappyHourTurnsRemaining() { return happyHour.getTurnsRemaining(); }

    private void recoverWaitingCustomers() {
        for (Customer c : customers) { //enhanced loop for the customer list
            c.recoverHappyHourSatisfaction();
        }
    }

    // Step 6: Result
    public boolean isGameOver(){return turn >= TOTAL_TURNS;}

    public boolean isVictory() {
        return cashier.getPaidCount() >= 4 && cashier.getRevenueCents() >= 4500 && cashier.getAverageSatisfaction() >= 60; // 4 customers paif, revenue cents >= 4500, average satisfaction >= 60
    }

    // Read-only states for user interface (UI)
    public int getTurn(){return turn;}
    public List<Customer> getQueue() {return Collections.unmodifiableList(queue);}
    public List<Customer> getCustomers() {return Collections.unmodifiableList(customers);}
    public List<Table> getTables(){return Collections.unmodifiableList(Arrays.asList(tables));}
    public List<Order> getOrders() {return Collections.unmodifiableList(orders);}
    public MenuItem[] getMenu() {return menu.clone();} // Copy, so callers cannot change the game's menu
    public int getRevenueCents() {return cashier.getRevenueCents();}
    public int getPaidCount() {return cashier.getPaidCount();}
    public double getAverageSatisfaction(){return cashier.getAverageSatisfaction();}
    public int getUnhappyDepartures(){return unhappyDepartures;}
    public int getServedCount() {return servedOnTurn.size();}

}
