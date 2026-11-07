# Design decisions

> Status: Stage 1, updated to match the code on main (7 Nov 2026).
> Scope: Stage 1. Nothing from Stage 2 or 3 (VIP, Critic, Host, ComboMeal, Happy Hour) appears, not even as a placeholder.

## Roles

| Member | Role | Owns (code) | Leads |
|---|---|---|---|
| Yunxi | D: CLI and release | ConsoleUI, InputReader, Main, scripted test runs, tags, README | Releases |
| Paige | A: Customers | Customer, CustomerState | Stage 2 |
| Enoch | B: Menu and pricing | MenuItem, Menu, Order, OrderStatus, Money, IllegalOrderStateException | Stage 3 |
| Rajvi | C: Turn engine and staff | GameController, Waiter, Chef, Cashier, Table, ActionResult | Stage 1 core, integration |

## Classes (Boundary / Control / Entity)

All classes are in package `restaurantrush` (folder `src/restaurantrush/`).

| Class | Type | Responsibility | Owner |
|---|---|---|---|
| `Main` | Boundary (entry point) | Creates the Scanner, InputReader, Menu, GameController and ConsoleUI, starts the game, and stops cleanly if the player quits or input ends | D |
| `ConsoleUI` | Boundary | Displays state and results, reads the manager's choices, shows failure reasons. Contains no game rules | D |
| `InputReader` | Boundary | Reads a number in a range, re-prompts on invalid input, throws `GameQuitException` on `q` or when input ends | D |
| `GameQuitException` | Boundary (exception) | Signals that the player quit or input ran out. Caught in `Main`; the game stops with no result. Its own type, so `Main` never hides an `IllegalStateException` caused by a real bug | D |
| `GameController` | Control | Runs the turn steps in order. Owns the queue, tables, customers, orders, staff and turn counter. Decides victory or defeat | C |
| `Waiter` | Entity (staff) | Seats a customer, takes an order, serves a READY order. One task per turn | C |
| `Chef` | Entity (staff) | Advances one order by one preparation unit. One unit per turn | C |
| `Cashier` | Entity (staff) | Collects payment from one READY_TO_PAY customer per turn. Keeps revenue, paid count and the satisfaction total used for the average | C |
| `Table` | Entity | Knows whether it is free and who sits there | C |
| `ActionResult` | Entity (value) | Success or failure of a staff action, with a reason | C |
| `Customer` | Entity | Holds id, satisfaction and state. Loses satisfaction while waiting. Chooses its own food | A |
| `MenuItem` | Entity | Name, price in cents, preparation units. Immutable | B |
| `Menu` | Entity | Holds the three menu items | B |
| `Order` | Entity | Links a customer and an item. Locks the price when created. Tracks units done and status, and rejects illegal status changes | B |
| `Money` | Utility | Formats cents as text, e.g. 1800 becomes "$18.00" | B |
| `IllegalOrderStateException` | Exception | Thrown by `Order` when an action does not fit its current status | B |

Design choices to note (useful later for the design discussion):
- Staff are entities that perform actions; GameController decides when they act. Alternative considered: putting all action logic in GameController (rejected: one huge class, low cohesion).
- Waiter, Chef and Cashier do not share a superclass in Stage 1. Their actions have nothing in common yet, and the brief says not to create abstractions just to satisfy a checklist.
- Failed actions are reported with `ActionResult`, not exceptions, because a failed action is a normal part of play. `Order` does throw `IllegalOrderStateException`; staff classes catch it and turn it into an `ActionResult.fail(...)`, so it never reaches the UI.
- Customer state can only change through one method per move (`seat()`, `placeOrder()`, `markServed()`, `finishEating()`, `markPaid()`, `leave()`). Each checks the current state first, so no class can skip a step such as WAITING straight to PAID. There is no public `setState()`. This also lets any future customer type override a single move. Alternative considered: one public `setState()` (rejected: any class could put a customer in any state, so Customer could not protect its own data).
- The Customer holds a reference to its own Order (`getOrder()`). GameController also keeps the list of all orders, oldest first, for the Cashier and for display.
- `getQueue()`, `getTables()` and `getOrders()` return unmodifiable lists, so the UI cannot add, remove or replace customers, tables or orders. This does not stop the UI calling mutating methods on the objects inside (e.g. `customer.markPaid()`). Stage 1 rule: ConsoleUI only calls getters on objects it receives.

## Enums

```
OrderStatus:   IN_PREPARATION, READY, SERVED, PAID, CANCELLED
CustomerState: WAITING, SEATED, ORDERED, EATING, READY_TO_PAY, PAID, LEFT
```

| State | Meaning |
|---|---|
| WAITING | In the queue, no table |
| SEATED | At a table, order not taken |
| ORDERED | Order taken, not yet served (includes order READY but not served) |
| EATING | Served, eating |
| READY_TO_PAY | Finished eating, waiting for the Cashier |
| PAID | Paid, table freed |
| LEFT | Satisfaction reached 0, left without paying |

`OrderStatus` also carries a display label (`getLabel()`, e.g. "Ready to serve") and two helpers: `isCancellable()` (IN_PREPARATION or READY) and `isClosed()` (PAID or CANCELLED).

Customers in WAITING, SEATED or ORDERED lose satisfaction at turn end. EATING and READY_TO_PAY do not.

## Turn step owners

| Step | What happens | Who does it | Code owner |
|---|---|---|---|
| 1 Arrivals | On turns 1, 4, 7, 10, 13, 16 a new Customer (satisfaction 100) joins the queue. Each staff member's task is reset | `GameController.startTurn()` | C |
| Display | Turn, revenue, average satisfaction, queue, tables, each customer's satisfaction and order status, staff tasks | `ConsoleUI` reads `GameController` getters | D |
| 2 Manager choices | Waiter: seat / take order / serve / wait. Then Chef: prepare / wait | `ConsoleUI` asks, `GameController` calls `Waiter` / `Chef` | D, C |
| 3 Kitchen and serving | Waiter acts first, then Chef (enforced: `chefPrepare` fails until the Waiter has acted or waited). An order becomes READY when its last unit is done. Serving needs READY at the moment the Waiter acts | `Waiter`, `Chef`, `Order` | C, B |
| 4 Payment and eating | First the Cashier pays one READY_TO_PAY customer: revenue updated, order and customer PAID, table freed. Then EATING customers served on an earlier turn become READY_TO_PAY (so they pay next turn at the earliest) | `GameController.endTurn()`, `Cashier`, `Customer`, `Table` | C, A |
| 5 Waiting and abandonment | WAITING / SEATED / ORDERED customers lose 8 (min 0). At 0: customer becomes LEFT; any order still IN_PREPARATION or READY is CANCELLED; the customer leaves the queue or their table is freed | `Customer.loseWaitingSatisfaction()`, `GameController` | A, C |
| 6 Result | After turn 18: Victory if paid >= 4 AND revenue >= $45.00 AND average satisfaction of PAID customers >= 60 | `GameController.isVictory()` | C |

Why serving is automatically correct: the Waiter acts before the Chef in every turn, so an order the Chef finishes this turn cannot be READY when the Waiter acts.

## Method signatures

Only methods another person's code calls. Money is always `int` cents.

### Customer (owner: A)
- `Customer(int customerId)`: starts WAITING with satisfaction 100
- `int getCustomerId()`
- `int getSatisfaction()`
- `CustomerState getState()`
- `Order getOrder()`: null until the order is taken
- `MenuItem chooseItem(MenuItem[] menu)`: applies the food-choice rule
- `void seat()`: WAITING to SEATED
- `void placeOrder(Order order)`: SEATED to ORDERED, keeps the order. The order must belong to this customer
- `void markServed()`: ORDERED to EATING
- `void finishEating()`: EATING to READY_TO_PAY
- `void markPaid()`: READY_TO_PAY to PAID
- `void leave()`: any state except PAID or LEFT, to LEFT
- `void loseWaitingSatisfaction()`: subtracts 8, minimum 0. The caller checks the state first
- `boolean hasLeft()`: true when satisfaction is 0

Each state-change method throws `IllegalStateException` if the customer is in the wrong state. Staff check the state before calling, so this only fires on a programming bug.

### MenuItem (owner: B)
- `MenuItem(String name, int priceCents, int prepUnits)`: throws `IllegalArgumentException` on an empty name, a price of 0 or less, or fewer than 1 unit
- `String getName()`
- `int getPriceCents()`
- `int getPrepUnits()`
- `String getPriceDisplay()`, `String toString()`: e.g. "Pasta ($18.00, 2 units)"

### Menu (owner: B)
- `Menu()`: creates Salad (900, 1), Burger (1500, 1), Pasta (1800, 2)
- `List<MenuItem> getItems()`: unmodifiable, in that order
- `MenuItem getItem(int index)`, `MenuItem findByName(String name)`, `int size()`

### Order (owner: B)
- `Order(Customer customer, MenuItem item)`: status IN_PREPARATION, price locked here, order number assigned
- `Customer getCustomer()`, `MenuItem getItem()`, `int getOrderNumber()`
- `OrderStatus getStatus()`
- `int getUnitsDone()`, `int getUnitsRemaining()`, `String getProgress()`: e.g. "1/2 units"
- `boolean canAdvance()`: true if IN_PREPARATION
- `boolean advanceOneUnit()`: adds 1 unit; sets READY when units done reaches the item's units; returns true if now READY
- `boolean isReady()`, `boolean isServed()`, `boolean isPaid()`, `boolean isCancelled()`
- `void markServed()`: READY to SERVED
- `void markPaid()`: SERVED to PAID
- `boolean canCancel()`, `void cancel()`
- `int getBillCents()`, `String getBillDisplay()`

`advanceOneUnit`, `markServed`, `markPaid` and `cancel` throw `IllegalOrderStateException` if the order is in the wrong status.

### Money (owner: B)
- `static String format(int cents)`: e.g. 1800 becomes "$18.00", -50 becomes "-$0.50"

### Table (owner: C)
- `Table(int tableId)`
- `int getTableId()`
- `boolean isFree()`
- `Customer getCustomer()`: null if free
- `ActionResult seat(Customer c)`: fails if the table is occupied
- `void free()`

### ActionResult (owner: C, used by D)
- `static ActionResult ok()`: success, reason "OK"
- `static ActionResult fail(String reason)`
- `boolean isSuccess()`
- `String getReason()`

The UI writes its own success messages, since `ok()` carries no detail.

### Waiter (owner: C)
- `void startTurn()`: resets the task
- `ActionResult seat(Customer c, Table t)`: fails if the task is used, the customer is not WAITING or the table is occupied; calls `c.seat()`
- `ActionResult takeOrder(Customer c, MenuItem[] menu, List<Order> orders)`: fails if the task is used or the customer is not SEATED. The customer chooses the item, the Order is created and added to `orders`, then `c.placeOrder(order)`
- `ActionResult serve(Order o)`: fails if the task is used or the order is not READY; calls `o.markServed()` and the customer's `markServed()`

### Chef (owner: C)
- `void startTurn()`
- `ActionResult prepare(Order o)`: fails if the unit is used or the order is not IN_PREPARATION

### Cashier (owner: C)
- `void startTurn()`
- `ActionResult processPayment(Order o, Table t)`: fails if already used this turn or the customer is not READY_TO_PAY. Marks order and customer PAID, frees the table, adds the bill to revenue and the customer's current satisfaction to the total
- `int getRevenueCents()`, `int getPaidCount()`
- `double getAverageSatisfaction()`: satisfaction total / paid count; 0 if nobody has paid

### GameController (owner: C, used by D)
Setup:
- `GameController(MenuItem[] menu)`: creates exactly 2 Tables numbered 1 and 2, one Waiter, one Chef, one Cashier. Turn counter starts at 0. `Main` builds the array with `new Menu().getItems().toArray(new MenuItem[0])`
- `static final int TOTAL_TURNS = 18`

Turn flow:
- `void startTurn()`: increments the turn, runs step 1, resets every staff task
- `ActionResult waiterSeat(Customer c, Table t)`: also fails if the customer is not in the queue
- `ActionResult waiterTakeOrder(Customer c)`
- `ActionResult waiterServe(Order o)`: records the turn it was served
- `void waiterWait()`: the Waiter skips its task. Must be called when the manager chooses Wait, or `chefPrepare` stays blocked
- `ActionResult chefPrepare(Order o)`: fails until the Waiter has acted or waited this turn
- `void endTurn()`: runs steps 4 and 5
- `boolean isGameOver()`: true once turn 18 has been played
- `boolean isVictory()`

A failed action does not use up the staff task.

Read-only state for the display:
- `int getTurn()`
- `int getRevenueCents()`, `int getPaidCount()`, `int getServedCount()`, `int getUnhappyDepartures()`
- `double getAverageSatisfaction()`: average of satisfaction recorded at payment, over PAID customers only. UI shows N/A when `getPaidCount()` is 0
- `List<Customer> getQueue()`: unmodifiable, oldest first
- `List<Table> getTables()`: unmodifiable, always 2 tables, table 1 then table 2
- `List<Order> getOrders()`: unmodifiable, every order ever taken, oldest first
- `MenuItem[] getMenu()`

### ConsoleUI (owner: D)
- `ConsoleUI(GameController game, InputReader input)`
- `void run()`: loop until `game.isGameOver()`: startTurn, show state, ask Waiter, ask Chef, endTurn, show results; then show Victory or Defeat
- `static String formatCents(int cents)`: delegates to `Money.format`

### InputReader (owner: D)
- `InputReader(Scanner scanner)`
- `int readInt(String prompt, int min, int max)`: throws `GameQuitException` if the player types `q` or input ends

## Food-choice rule

Customer `id` (1 to 6, in arrival order) orders `menu[(id - 1) % 3]`. This is a zero-based array index: 0 = Salad, 1 = Burger, 2 = Pasta.

| Customer | 1 | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|---|
| Orders | Salad | Burger | Pasta | Salad | Burger | Pasta |

Deterministic, so every run with the same inputs gives the same game. The order is printed when the Waiter takes it.

## Decisions (formerly open questions)

1. Display after arrivals: step 1 runs, then the display, so the manager sees the new arrival before choosing.
2. Cashier order: the customer who became READY_TO_PAY first pays first. In Stage 1 at most one customer is ever READY_TO_PAY when the Cashier acts: the Waiter serves at most one order per turn, so no two customers finish eating on the same turn, and the Cashier pays every turn before new customers finish eating. The code therefore takes the first READY_TO_PAY customer in its order list, which is always that customer. If a later stage lets two customers be ready at once (e.g. two serves in one turn), `payOldestReadyCustomer()` must instead compare the turn each became ready, ties broken by lower customer id.
3. When a customer leaves, any order not yet SERVED (IN_PREPARATION or READY) is cancelled. Customers with no order simply leave.
4. Waiter menu: 1 Seat, 2 Take order, 3 Serve, 0 Wait. Seat picks from the queue, then a table (1 to 2). Take order picks from customers at tables. Serve and Chef Prepare pick from open orders (not PAID or CANCELLED). The controller decides whether the pick is allowed.
5. Read-only access for the UI: rely on the "getters only" rule. No read-only interfaces in Stage 1.
