# Design decisions

> Status: DRAFT proposal for the Phase 0 session. Everything below is to be confirmed or changed by the group.
> Scope: Stage 1. Every class listed below is part of Stage 1 and must be implemented. Nothing from Stage 2 or 3 (VIP, Critic, Host, ComboMeal, Happy Hour) appears, not even as a placeholder.

## Roles

| Member | Role | Owns (code) | Leads |
|---|---|---|---|
| Yunxi | D: CLI and release | ConsoleUI, InputReader, Main, scripted test runs, tags, README | Releases |
| Paige | A: Customers | Customer, CustomerState | Stage 2 |
| Enoch | B: Menu and pricing | MenuItem, Menu, Order, OrderStatus | Stage 3 |
| Rajvi | C: Turn engine and staff | GameController, Waiter, Chef, Cashier, Table, ActionResult | Stage 1 core, integration |

## Classes (Boundary / Control / Entity)

All classes are in package `restaurantrush` (folder `src/restaurantrush/`).

| Class | Type | Responsibility | Owner |
|---|---|---|---|
| `Main` | Boundary (entry point) | Creates the Scanner, ConsoleUI and GameController, starts the game | D |
| `ConsoleUI` | Boundary | Displays state and results, reads the manager's choices, shows failure reasons. Contains no game rules | D |
| `InputReader` | Boundary | Reads a number in a range, re-prompts on invalid input | D |
| `GameController` | Control | Runs the turn steps in order. Owns the queue, tables, customers, staff, revenue and turn counter. Decides victory or defeat | C |
| `Waiter` | Entity (staff) | Seats a customer, takes an order, serves a READY order. Checks each action is allowed | C |
| `Chef` | Entity (staff) | Advances one order by one preparation unit | C |
| `Cashier` | Entity (staff) | Collects payment from one READY_TO_PAY customer | C |
| `Table` | Entity | Knows whether it is free and who sits there | C |
| `ActionResult` | Entity (value) | Success or failure of a staff action, with a reason | C |
| `Customer` | Entity | Holds satisfaction and state. Loses satisfaction while waiting. Chooses its own food. Records when it was served and when it became ready to pay | A |
| `MenuItem` | Entity | Name, price in cents, preparation units | B |
| `Menu` | Entity | Holds the three menu items | B |
| `Order` | Entity | Links a customer and an item. Locks the price when created. Tracks units done and status | B |

Design choices to note (useful later for the design discussion):
- Staff are entities that perform actions; GameController decides when they act. Alternative considered: putting all action logic in GameController (rejected: one huge class, low cohesion).
- Waiter, Chef and Cashier do not share a superclass in Stage 1. Their actions have nothing in common yet, and the brief says not to create abstractions just to satisfy a checklist.
- Lists returned to the UI are unmodifiable (`Collections.unmodifiableList`), so the UI cannot add or remove customers or tables. This does NOT stop the UI calling mutating methods on the objects inside the list (e.g. `customer.markPaid()`). Stage 1 rule: ConsoleUI only calls getters on objects it receives. Alternative to decide in the session: return read-only interfaces to the UI (e.g. `CustomerView` with getters only), which enforces this in code (ISP) at the cost of extra types.

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

Customers in WAITING, SEATED or ORDERED lose satisfaction at turn end. EATING and READY_TO_PAY do not.

## Turn step owners

| Step | What happens | Who does it | Code owner |
|---|---|---|---|
| 1 Arrivals | On turns 1, 4, 7, 10, 13, 16 a new Customer (satisfaction 100) joins the queue | `GameController.startTurn()` | C |
| Display | Turn, revenue, average satisfaction, queue, tables, each customer's satisfaction, order status, staff tasks | `ConsoleUI` reads `GameController` getters | D |
| 2 Manager choices | Waiter: seat / take order / serve / wait. Then Chef: prepare / wait | `ConsoleUI` asks, `GameController` calls `Waiter` / `Chef` | D, C |
| 3 Kitchen and serving | Waiter acts first, then Chef. An order becomes READY when its last unit is done. Serving needs READY at the moment the Waiter acts | `Waiter`, `Chef`, `Order` | C, B |
| 4 Eating and payment | EATING customers served on an earlier turn become READY_TO_PAY. Cashier pays the oldest customer who became READY_TO_PAY on an earlier turn. Revenue updated, order and customer PAID, table freed | `GameController.endTurn()`, `Customer`, `Cashier`, `Table` | C, A |
| 5 Waiting and abandonment | WAITING / SEATED / ORDERED customers lose 8 (min 0). At 0: customer becomes LEFT; if they have an order, it is CANCELLED; if they are at a table, it is freed. A WAITING or SEATED customer has no order, so `getOrder()` must be null-checked first | `Customer.loseWaitingSatisfaction()`, `GameController` | A, C |
| 6 Result | After turn 18: Victory if paid >= 4 AND revenue >= $45.00 AND average satisfaction of PAID customers >= 60 (see `getAverageSatisfaction()`) | `GameController.isVictory()` | C |

Why serving is automatically correct: the Waiter acts before the Chef in every turn, so an order the Chef finishes this turn cannot be READY when the Waiter acts. No extra flag is needed.

## Method signatures

Only methods another person's code calls. Money is always `int` cents.

### Customer (owner: A)
- `Customer(int id)`: starts WAITING with satisfaction 100
- `int getId()`
- `int getSatisfaction()`
- `CustomerState getState()`
- `MenuItem chooseItem(Menu menu)`: applies the food-choice rule
- `void seat()`: WAITING to SEATED
- `void placeOrder(Order order)`: SEATED to ORDERED, keeps a reference to the order
- `Order getOrder()`: null if no order yet
- `void markServed(int turn)`: ORDERED to EATING, records the turn
- `boolean finishEatingIfDone(int currentTurn)`: EATING to READY_TO_PAY if served on an earlier turn; returns true if it changed
- `int getReadyToPayTurn()`
- `void markPaid()`: READY_TO_PAY to PAID
- `boolean loseWaitingSatisfaction()`: subtracts 8 (min 0) only if WAITING / SEATED / ORDERED; sets LEFT and returns true if satisfaction hit 0

### MenuItem (owner: B)
- `MenuItem(String name, int priceCents, int prepUnits)`
- `String getName()`
- `int getPriceCents()`
- `int getPrepUnits()`

### Menu (owner: B)
- `Menu()`: creates Salad (900, 1), Burger (1500, 1), Pasta (1800, 2)
- `List<MenuItem> getItems()`: unmodifiable, in that order

### Order (owner: B)
- `Order(Customer customer, MenuItem item)`: status IN_PREPARATION, price locked here
- `Customer getCustomer()`
- `MenuItem getItem()`
- `OrderStatus getStatus()`
- `int getUnitsDone()`
- `void advanceOneUnit()`: adds 1 unit; sets READY when units done equals the item's units
- `boolean isReady()`
- `void markServed()`
- `void markPaid()`
- `void cancel()`
- `int getBillCents()`

### Table (owner: C)
- `Table(int number)`
- `int getNumber()`
- `boolean isFree()`
- `Customer getCustomer()`: null if free
- `void seat(Customer customer)`
- `void free()`

### ActionResult (owner: C, used by D)
- `static ActionResult success(String message)`
- `static ActionResult failure(String reason)`
- `boolean isSuccess()`
- `String getMessage()`: success message or failure reason

### Waiter (owner: C)
- `ActionResult seat(Customer customer, Table table)`: fails if customer is not WAITING or table is not free
- `ActionResult takeOrder(Customer customer, Menu menu)`: fails if customer is not SEATED. Customer chooses the item, Order is created and attached
- `ActionResult serve(Customer customer, int turn)`: fails if the customer's order is not READY

### Chef (owner: C)
- `ActionResult prepare(Order order)`: fails if the order is not IN_PREPARATION

### Cashier (owner: C)
- `int collect(Customer customer)`: marks order and customer PAID, returns the bill in cents

### GameController (owner: C, used by D)
Setup:
- `GameController()`: creates the Menu, exactly 2 Tables numbered 1 and 2, one Waiter, one Chef, one Cashier. Turn counter starts at 0

Turn flow:
- `void startTurn()`: increments the turn, runs step 1
- `ActionResult waiterSeat(Customer customer, Table table)`
- `ActionResult waiterTakeOrder(Customer customer)`
- `ActionResult waiterServe(Customer customer)`
- `ActionResult chefPrepare(Customer customer)`
- `void endTurn()`: runs steps 4 and 5
- `boolean isGameOver()`: true after turn 18
- `boolean isVictory()`

A failed action does not use up the staff task. "Wait" means the UI simply does not call the action.

Read-only state for the display:
- `int getTurn()`, `int getMaxTurns()`
- `int getRevenueCents()`
- `boolean hasPaidCustomers()`
- `double getAverageSatisfaction()`: average of the satisfaction recorded at payment, over PAID customers only. LEFT and unpaid customers are excluded. Used by both the display and `isVictory()`. UI shows N/A when `hasPaidCustomers()` is false
- `List<Customer> getWaitingQueue()`
- `List<Table> getTables()`: always 2 tables, in order (table 1, table 2)
- `List<Customer> getActiveCustomers()`: everyone not PAID or LEFT
- `int getServedCount()`, `int getPaidCount()`, `int getUnhappyDepartures()`
- `Menu getMenu()`

### ConsoleUI (owner: D)
- `ConsoleUI(GameController game, InputReader input)`
- `void run()`: loop until `game.isGameOver()`: startTurn, show state, ask Waiter, ask Chef, endTurn, show results; then show Victory or Defeat
- `static String formatCents(int cents)`: e.g. 1800 becomes "$18.00"

### InputReader (owner: D)
- `InputReader(Scanner scanner)`
- `int readInt(String prompt, int min, int max)`

## Food-choice rule

Customer `id` (1 to 6, in arrival order) orders `menu.getItems().get((id - 1) % 3)`. This is a zero-based list index: 0 = Salad, 1 = Burger, 2 = Pasta.

| Customer | 1 | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|---|
| Orders | Salad | Burger | Pasta | Salad | Burger | Pasta |

Deterministic, so every run with the same inputs gives the same game. The order is printed when the Waiter takes it.

## Open questions (decide in the session)

1. Display before or after arrivals? The brief lists the display before the steps, but the manager needs to see the new arrival before choosing. Proposal: run step 1, then display.
2. "Oldest first" for the Cashier: proposal is earliest `readyToPayTurn`, ties broken by lower customer id.
3. When a customer leaves, does an order the Chef already finished (READY) also get cancelled? Proposal: yes, any order not yet SERVED (IN_PREPARATION or READY) is cancelled. Customers with no order simply leave.
4. How the Waiter menu looks: proposal is 1 Seat, 2 Take order, 3 Serve, 0 Wait, then pick the customer by id and the table by number (1 to 2).
5. Read-only access for the UI: rely on the "getters only" rule, or add read-only interfaces such as `CustomerView`? (See Classes section.)