# Design decisions

> Status: Stage 3, updated to match the code on main (11 Nov 2026).
> Scope: Stage 1, 2 and Stage 3.

## Roles

| Member | Role | Owns (code) | Leads |
|---|---|---|---|
| Paige | A: Customers | Customer, CustomerState | Stage 2 |
| Enoch | B: Menu and pricing | MenuItem, Menu, Order, OrderStatus, Money, IllegalOrderStateException | Stage 3 |
| Rajvi | C: Turn engine and staff | GameController, Waiter, Chef, Cashier, Table, ActionResult | Stage 1 core, integration |
| Yunxi | D: CLI and release | ConsoleUI, InputReader, Main, scripted test runs, tags, README. Stage 3: also Host and Happy Hour activation in GameController (taken over from C) | Releases |

## Classes (Boundary / Control / Entity)

All classes are in package `restaurantrush` (folder `src/restaurantrush/`).

| Class | Type | Responsibility | Owner |
|---|---|---|---|
| `Main` | Boundary (entry point) | Creates the Scanner, InputReader, Menu, GameController and ConsoleUI, starts the game, and stops cleanly if the player quits or input ends | D |
| `ConsoleUI` | Boundary | Displays state and results, reads the manager's choices, shows failure reasons. Contains no game rules | D |
| `InputReader` | Boundary | Reads a number in a range, re-prompts on invalid input, throws `GameQuitException` on `q` or when input ends | D |
| `GameQuitException` | Boundary (exception) | Signals that the player quit or input ran out. Caught in `Main`; the game stops with no result. Its own type, so `Main` never hides an `IllegalStateException` caused by a real bug | D |
| `GameController` | Control | Runs the turn steps in order. Owns the queue, tables, customers, orders, staff, Happy Hour and turn counter. Decides victory or defeat | C + D |
| `Host` | Entity (staff) | Stage 3. Seats at most one queued customer per turn, on top of the Waiter's task | D |
| `HappyHour` | Entity | Stage 3. Once-per-game pricing policy: tracks whether it was used and the turns remaining, applies 20% off | B |
| `ComboMeal` | Entity | Stage 3. A `MenuItem` made of two existing items: units = sum, price = 90% of their total | B |
| `Waiter` | Entity (staff) | Seats a customer, takes an order, serves a READY order. One task per turn | C |
| `Chef` | Entity (staff) | Advances one order by one preparation unit. One unit per turn | C |
| `Cashier` | Entity (staff) | Collects payment from one READY_TO_PAY customer per turn. Keeps revenue, paid count and the satisfaction total used for the average | C |
| `Table` | Entity | Knows whether it is free and who sits there | C |
| `ActionResult` | Entity (value) | Success or failure of a staff action, with a reason | C |
| `Customer` | Entity | Holds id, satisfaction and state. Loses satisfaction while waiting. Chooses its own food. Has four overridable methods with regular behaviour as the default | A |
| `VIPCustomer` | Entity | A customer who loses 5 satisfaction per waiting turn and gets 10% off the bill. Overrides `getWaitingLoss()`, `getDiscountRate()`, `getCustomerType()` | A |
| `CriticCustomer` | Entity | A customer who loses 12 satisfaction per waiting turn and always orders the most expensive dish (stage 3, combos are included). Overrides `getWaitingLoss()`, `chooseItem()`, `getCustomerType()` | A |
| `MenuItem` | Entity | Name, price in cents, preparation units. Immutable | B |
| `Menu` | Entity | Holds the three dishes and from stage 3, 2 combos | B |
| `Order` | Entity | Links a customer and an item. Locks the price when created. Tracks units done and status, and rejects illegal status changes | B |
| `Money` | Utility | Formats cents as text, e.g. 1800 becomes "$18.00", and applies percentage discounts in whole cents (`percentOff`, `applyDiscountRate`) | B |
| `IllegalOrderStateException` | Exception | Thrown by `Order` when an action does not fit its current status | B |

## Design choices

Useful for the design discussion. Each entry: the decision, why, and the alternative considered.

### Stage 1

- **Staff act, the controller decides when.** Waiter, Chef and Cashier are entities that perform actions; GameController sequences them. Alternative: all action logic in GameController (rejected: one huge class, low cohesion).
- **No shared staff superclass.** The three staff actions have nothing in common yet, and the brief says not to add abstractions just to satisfy a checklist.
- **Failures are results, not exceptions.** A refused action is normal play, so it returns `ActionResult`. `Order` throws `IllegalOrderStateException`; staff catch it and convert it to `ActionResult.fail(...)`, so it never reaches the UI.
- **One method per state change.** Customer state changes only through `seat()`, `placeOrder()`, `markServed()`, `finishEating()`, `markPaid()`, `leave()`, each checking the current state, so no step can be skipped. Alternative: a public `setState()` (rejected: any class could set any state, so Customer could not protect its own data).
- **Customer knows its Order.** `getOrder()` links them; GameController also keeps all orders, oldest first, for the Cashier and the display.
- **Read-only lists for the UI.** `getQueue()`, `getTables()` and `getOrders()` return unmodifiable lists. This protects the lists, not the objects inside, so ConsoleUI only calls getters on what it receives.

### Stage 2

- **Behaviour by overriding, not type checks.** Each customer type overrides `getWaitingLoss()`, `getDiscountRate()`, `getCustomerType()` or `chooseItem()`. GameController and ConsoleUI only call `Customer` methods; there is no `instanceof` anywhere.
- **`Customer` stays concrete.** Its methods hold the Regular behaviour. Alternative: abstract `Customer` plus a `RegularCustomer` subclass (forces every type to state its own values, but adds a class with no new behaviour).
- **One creation point.** `GameController.createCustomer(id)` is the only code that names the subclasses: customer 2 is a VIP, customer 4 a Critic (constants `VIP_CUSTOMER_ID`, `CRITIC_CUSTOMER_ID`). Choosing a class at creation is not a behaviour type check.
- **Critic rule: most expensive dish.** Group-defined. Deterministic, so runs stay reproducible, and a real method override rather than just a different number. Effect: the Critic holds the kitchen for 2 turns while losing 12 per waiting turn.
- **Bill locked at order time.** The discount is applied once in `Order`'s constructor; the UI and Cashier both read the stored bill, so the price shown always matches the price charged.
- **What Stage 1 did not anticipate.** The waiting loss of 8 was hard-coded in `Customer`, so Stage 2 had to edit `Customer` as well as add subclasses. Now that the loss is overridable, a new customer type needs no change to `Customer`.

### Stage 3

- **Host is its own class.** It has its own per-turn state (`hasActed`) and is a separate role in the brief. Alternative: a shared `Staff` superclass with one `seat` method for Host and Waiter (less repeated code, but it changes the Stage 1 `Waiter`, and the brief says not to add abstractions just to satisfy a checklist). The Host never sets `waiterDone`, so it does not use the Waiter's task or block the Chef.
- **ComboMeal extends MenuItem.** A combo is used everywhere a dish is (menu, order, Chef, bill), so it inherits from `MenuItem` and overrides `toString()`. Nothing else had to learn about combos: `ConsoleUI` prints the menu with `" " + item`, and dynamic binding picks the combo's `toString()`.
- **Happy Hour lives in GameController, read through queries.** The UI gets `canActivateHappyHour()`, `isHappyHourActive()`, `getHappyHourTurnsRemaining()` and `isHappyHourUsed()`, never the `HappyHour` object, so it cannot call `activate()` and skip the rules.
- **"Start of a turn" is enforced in the controller.** `activateHappyHour()` succeeds only if Happy Hour is unused, a turn is in progress (`turnInProgress`, set in `startTurn()`, cleared in `endTurn()`) and the Waiter has not acted. `waiterDone` alone could not tell "turn not started" from "turn started, Waiter not done yet".
- **Recovery is split across two places.** The first active turn recovers inside `activateHappyHour()`, because `startTurn()` has already run. The second recovers in `startTurn()`. Each customer decides for itself whether it recovers (`recoverHappyHourSatisfaction()`), so the controller has no state checks.
- **Price locked at order time.** `Waiter.takeOrder` passes Happy Hour into `new Order(...)`, which applies the VIP discount, then 20% if active, and stores the result. Ending Happy Hour never changes an existing bill.
- **Rounding: once per discount.** Each discount is rounded to the nearest cent as it is applied (`Money.percentOff`), so a VIP order in Happy Hour is rounded twice. The brief does not say whether to round per step or once at the end; its $10.80 example comes out exact either way.
- **Changes to earlier classes (brief 4.4).** `Waiter.takeOrder` gained a `HappyHour` parameter (the price must be fixed when the order is created). `Menu` adds the combos. `Customer` adds `recoverHappyHourSatisfaction()`. `GameController` adds the Host, Happy Hour and the turn-in-progress flag. `ConsoleUI` shows the menu and the two new prompts. `Order` gained a constructor that takes `HappyHour`. `Waiter.takeOrder`'s failure message now names the customer's state. No Stage 1 or 2 rule was rewritten.

## Responsibilities and cohesion

Each rule lives in one place:
- Pricing: only in `Order`, `Money` and `HappyHour`.
- Order transitions: only in `Order`.
- Customer transitions: only in `Customer`.
- The turn order: only in `GameController`.

`ConsoleUI` decides nothing. For example, it asks `game.canActivateHappyHour()` instead of checking the rule itself. The staff classes enforce their own one-action-per-turn rule, so `GameController` only decides *when* they act, and they decide *whether* the action is allowed.

## Design principles (only where the code shows them)

- **SRP:**
  - `Money` is the only class that formats or discounts cents.
  - `Order` owns the bill, and `Cashier` only reads it (`getBillCents()`), so the price shown always equals the price charged.
  - `ConsoleUI` has input and output only.
- **OCP:**
  - **Stage 3, the clearest case.** `ComboMeal` was added without editing `Order`, `Chef`, `Waiter`, `Cashier` or `Customer`, because they only call `getPriceCents()`, `getPrepUnits()` and `getName()`. The only change was registering the combos in `Menu`'s constructor. Customers pick combos up through their existing food-choice rule, because it indexes into whatever menu it is given.
  - **Stage 2, less clean.** The waiting loss of 8 was written directly into `Customer`, so it first had to become an overridable `getWaitingLoss()`. Now that it is, a new customer type needs no change to `Customer`.
- **LSP:**
  - `GameController`, `Waiter` and `ConsoleUI` treat every customer as a `Customer`; there is no `instanceof` anywhere. VIP and Critic change values (5 or 12 instead of 8, 10% instead of 0%) but keep the contract: `chooseItem` still returns an item from the given menu.
  - A `ComboMeal` can be used wherever a `MenuItem` is: the Chef prepares its 3 units and the Cashier charges its locked bill with no special case.
- **ISP: not applied, deliberately.** There are no Java interfaces. We rejected read-only views such as `CustomerView` as extra types that change no behaviour (brief 4.4). The UI's access is narrowed in other ways:
  - Lists are returned unmodifiable.
  - Happy Hour is exposed only through queries (`isHappyHourActive()`, `getHappyHourTurnsRemaining()`, `isHappyHourUsed()`), never as the object itself.

  The remaining weakness: `Customer` objects inside those lists still have public state-changing methods. Keeping the UI to getters only is a team convention, not something the compiler enforces.
- **DIP: not applied.** `GameController` creates and depends on the concrete staff and `HappyHour` classes directly. With one implementation of each, an abstraction would add indirection with no benefit. See the alternative below for where it would pay off.

## Trade-offs

| Decision | Gained | Cost |
|---|---|---|
| Refused actions return `ActionResult`; only a bug throws `IllegalOrderStateException` | The UI just prints the reason and asks again; the exception is a safety net | Two failure channels; staff must convert the exception into a result |
| One control class, `GameController` | The whole turn order is in one place, which made the sequence diagram and rule checks straightforward | It is the largest class (about 230 lines), and every Stage 2 and 3 feature added to it |
| Bill locked in `Order`'s constructor | "Price fixed when the order is taken" holds automatically | `Waiter.takeOrder` had to gain a `HappyHour` parameter in Stage 3 |
| Money as `int` cents, rounded after each discount | No floating-point errors in revenue; each step is testable | A VIP order during Happy Hour is rounded twice |
| No staff superclass | Stage 1 `Waiter` unchanged; each role reads on its own | `Host` repeats about ten lines of the Waiter's seating check |
| `Customer` stays concrete (it is the Regular customer) | No `RegularCustomer` class that adds no behaviour | A subclass that forgets an override silently inherits Regular behaviour |

## Alternative design considered in depth: pricing rules behind an interface

**Alternative.** Discounts would be objects behind a shared interface:

```java
interface PricingRule { int apply(int cents, Customer customer); }
```

- The VIP discount and Happy Hour would each be one implementation.
- `GameController` would hold an ordered list of active rules.
- `Order` would pass the price through each rule in turn.

**What it would improve.**
- **OCP and DIP:** `Order` would depend only on the abstraction, so a new promotion would be a new class with no edit to `Order` or `Waiter`.
- **Discount order:** "VIP first, then Happy Hour" would be the order of the list rather than the order of lines in the constructor.

**Why we did not adopt it.**
- Only two rules exist, and the brief fixes their order, so nothing uses the flexibility.
- It would move the VIP discount out of `VIPCustomer`, weakening the Stage 2 polymorphism shown by `getDiscountRate()`.
- The brief says not to create interfaces just to satisfy a checklist.

If a third promotion were added, we would refactor to `PricingRule` at that point.

## How the player's choices change the result

The game is deterministic: the same inputs always give the same game, so any difference in outcome comes from the manager's choices.

**Happy Hour, measured.** `victory-no-happy-hour.txt` replays `victory.txt` with Happy Hour declined at turn 14 and every other choice identical:

| Outcome | Happy Hour on (turn 14) | Declined |
|---|---|---|
| Result | VICTORY | VICTORY |
| Revenue | $59.40 | $62.10 |
| Average satisfaction (paid) | **82.5** | **71.0** |
| VIP: satisfaction at payment / Burger bill | 70 / $10.80 | 40 / $13.50 |
| Critic (customer 4) | Still seated, satisfaction 22 | **Left** |

- **Cost:** $2.70 of revenue.
- **Gain:** 11.5 points of average satisfaction and one fewer unhappy departure. Three customers were waiting at turn 14, so all three got the +15.

Switching it on is the better choice when revenue is safely above $45 but satisfaction is near 60. Because it is once per game, the turn you choose matters.

**Staff constraints.**
- **The Waiter has one task per turn and acts before the Chef.** Serving, taking an order and seating compete for that one task, and the Chef cannot cook an order that has not been taken.
- **The Host seats one extra customer without using the Waiter's task.** In `victory.txt` on turn 13, the Host seats customer 5 and the Waiter takes their order in the same turn, saving a turn of waiting loss.
- **Doing nothing loses.** In `defeat.txt` the staff wait every turn: 0 paid, $0.00, two unhappy departures.

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
| 1 Arrivals | On turns 1, 4, 7, 10, 13, 16 a new customer (satisfaction 100) joins the queue: customer 2 is a VIP, customer 4 a Critic, the rest Regular. Each staff member's task is reset (including the Host). On the second Happy Hour turn, waiting customers recover 15 | `GameController.startTurn()` | C, D |
| Display | Turn, revenue, average satisfaction, Happy Hour status, queue, tables, each customer's satisfaction and order status, staff tasks. The menu is shown once at the start | `ConsoleUI` reads `GameController` getters | D |
| 2 Manager choices | Happy Hour: switch on / not (only while unused). Host: seat / skip (only when someone is queued and a table is free). Waiter: seat / take order / serve / wait. Then Chef: prepare / wait | `ConsoleUI` asks, `GameController` calls `HappyHour` / `Host` / `Waiter` / `Chef` | D, C |
| 3 Kitchen and serving | Waiter acts first, then Chef (enforced: `chefPrepare` fails until the Waiter has acted or waited). An order becomes READY when its last unit is done. Serving needs READY at the moment the Waiter acts | `Waiter`, `Chef`, `Order` | C, B |
| 4 Payment and eating | First the Cashier pays one READY_TO_PAY customer: revenue updated, order and customer PAID, table freed. Then EATING customers served on an earlier turn become READY_TO_PAY (so they pay next turn at the earliest) | `GameController.endTurn()`, `Cashier`, `Customer`, `Table` | C, A |
| 5 Waiting and abandonment | WAITING / SEATED / ORDERED customers lose their type's waiting loss (Regular 8, VIP 5, Critic 12; min 0). At 0: customer becomes LEFT; any order still IN_PREPARATION or READY is CANCELLED; the customer leaves the queue or their table is freed. Then Happy Hour counts down one turn | `Customer.loseWaitingSatisfaction()`, `GameController`, `HappyHour.endTurn()` | A, C, D |
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
- `MenuItem chooseItem(MenuItem[] menu)`: applies the food-choice rule. CriticCustomer overrides it
- `int getWaitingLoss()`: satisfaction lost per waiting turn. Regular 8; overridden by VIP (5) and Critic (12)
- `double getDiscountRate()`: share of the menu price taken off the bill. Regular 0.0; overridden by VIP (0.10)
- `String getCustomerType()`: "Regular"; overridden by each subclass. Used by the display
- `void seat()`: WAITING to SEATED
- `void placeOrder(Order order)`: SEATED to ORDERED, keeps the order. The order must belong to this customer
- `void markServed()`: ORDERED to EATING
- `void finishEating()`: EATING to READY_TO_PAY
- `void markPaid()`: READY_TO_PAY to PAID
- `void leave()`: any state except PAID or LEFT, to LEFT
- `void loseWaitingSatisfaction()`: subtracts `getWaitingLoss()`, minimum 0. The caller checks the state first
- `boolean hasLeft()`: true when satisfaction is 0
- `void recoverHappyHourSatisfaction()`: +15, maximum 100, only if WAITING, SEATED or ORDERED and not at 0

Each state-change method throws `IllegalStateException` if the customer is in the wrong state. Staff check the state before calling, so this only fires on a programming bug.

### VIPCustomer extends Customer (owner: A)
- `VIPCustomer(int customerId)`: calls `super(customerId)`
- Overrides `getWaitingLoss()` → 5, `getDiscountRate()` → 0.10, `getCustomerType()` → "VIP"

### CriticCustomer extends Customer (owner: A)
- `CriticCustomer(int customerId)`: calls `super(customerId)`
- Overrides `getWaitingLoss()` → 12, `getCustomerType()` → "Critic", `chooseItem(MenuItem[] menu)` → the most expensive item
- Inherits `getDiscountRate()` → 0.0

### MenuItem (owner: B)
- `MenuItem(String name, int priceCents, int prepUnits)`: throws `IllegalArgumentException` on an empty name, a price of 0 or less, or fewer than 1 unit
- `String getName()`
- `int getPriceCents()`
- `int getPrepUnits()`
- `String getPriceDisplay()`, `String toString()`: e.g. "Pasta ($18.00, 2 units)"

### Menu (owner: B)
- `Menu()`: creates Salad (900, 1), Burger (1500, 1), Pasta (1800, 2), then from Stage 3 a Salad + Pasta Combo and a Salad + Burger Combo
- `List<MenuItem> getItems()`: unmodifiable, in that order
- `MenuItem getItem(int index)`, `MenuItem findByName(String name)`, `int size()`

### Order (owner: B)
- `Order(Customer customer, MenuItem item, HappyHour happyHour)`: status IN_PREPARATION, order number assigned. Bill locked here: menu price less `customer.getDiscountRate()`, then 20% off if Happy Hour is active, each step rounded to the nearest cent. `Order(Customer, MenuItem)` is kept and passes no Happy Hour
- `boolean isHappyHourPrice()`, `int getSavingsCents()`
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
- `static int percentOff(int cents, int percent)`: rounds to the nearest cent
- `static int applyDiscountRate(int cents, double rate)`

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
- `ActionResult takeOrder(Customer c, MenuItem[] menu, List<Order> orders, HappyHour happyHour)`: fails if the task is used or the customer is not SEATED (the reason names the customer's current state). The customer chooses the item, the Order is created with the current Happy Hour so its price is locked, added to `orders`, then `c.placeOrder(order)`
- `ActionResult serve(Order o)`: fails if the task is used or the order is not READY; calls `o.markServed()` and the customer's `markServed()`

### Host (owner: D, Stage 3)
- `void startTurn()`: resets the action
- `boolean hasActed()`
- `ActionResult seat(Customer c, Table t)`: fails if the Host has already seated someone this turn, the customer is not WAITING or the table is occupied; calls `c.seat()`

### HappyHour (owner: B, Stage 3)
- `DISCOUNT_PERCENT = 20`, `DURATION_TURNS = 2`, `SATISFACTION_RECOVERY = 15`
- `boolean canActivate()`, `void activate()`: throws `IllegalStateException` if already used
- `boolean isActive()`, `int getTurnsRemaining()`, `boolean hasBeenUsed()`, `void endTurn()`: counts down
- `int applyTo(int cents)`: 20% off if active

### ComboMeal extends MenuItem (owner: B, Stage 3)
- `ComboMeal(MenuItem first, MenuItem second)`: name "First + Second Combo", price `Money.percentOff(total, 10)`, units = sum
- `List<MenuItem> getComponents()`, `int getSeparatePriceCents()`, `toString()` adds " - 10% off $X" (X = separate price)

### Chef (owner: C)
- `void startTurn()`
- `ActionResult prepare(Order o)`: fails if the unit is used or the order is not IN_PREPARATION

### Cashier (owner: C)
- `void startTurn()`
- `ActionResult processPayment(Order o, Table t)`: fails if already used this turn or the customer is not READY_TO_PAY. Marks order and customer PAID, frees the table, adds the bill to revenue and the customer's current satisfaction to the total
- `int getRevenueCents()`, `int getPaidCount()`
- `double getAverageSatisfaction()`: satisfaction total / paid count; 0 if nobody has paid

### GameController (owner: C + D, used by D)
Setup:
- `GameController(MenuItem[] menu)`: creates exactly 2 Tables numbered 1 and 2, one Waiter, one Chef, one Host, one Cashier and one HappyHour. Turn counter starts at 0. `Main` builds the array with `new Menu().getItems().toArray(new MenuItem[0])`
- `static final int TOTAL_TURNS = 18`

Turn flow:
- `void startTurn()`: increments the turn, runs step 1, resets every staff task. Arrivals are created by customer number: 2 is a VIPCustomer, 4 a CriticCustomer, the rest Regular. Also resets the Host, and applies Happy Hour recovery on its second active turn
- `ActionResult waiterSeat(Customer c, Table t)`: also fails if the customer is not in the queue
- `ActionResult waiterTakeOrder(Customer c)`
- `ActionResult waiterServe(Order o)`: records the turn it was served
- `void waiterWait()`: the Waiter skips its task. Must be called when the manager chooses Wait, or `chefPrepare` stays blocked
- `ActionResult chefPrepare(Order o)`: fails until the Waiter has acted or waited this turn
- `void endTurn()`: runs steps 4 and 5, then counts Happy Hour down
- `boolean isGameOver()`: true once turn 18 has been played
- `boolean isVictory()`

A failed action does not use up the staff task.

Stage 3:
- `boolean canActivateHappyHour()`: unused, turn in progress, Waiter not acted yet
- `ActionResult activateHappyHour()`: fails otherwise; applies the first +15 recovery
- `boolean isHappyHourActive()`, `int getHappyHourTurnsRemaining()`, `boolean isHappyHourUsed()`
- `boolean canHostSeat()`: turn in progress, Host not acted, someone queued, a table free
- `ActionResult hostSeat(Customer c, Table t)`: also fails if the customer is not in the queue

Read-only state for the display:
- `int getTurn()`
- `int getRevenueCents()`, `int getPaidCount()`, `int getServedCount()`, `int getUnhappyDepartures()`
- `double getAverageSatisfaction()`: average of satisfaction recorded at payment, over PAID customers only. UI shows N/A when `getPaidCount()` is 0
- `List<Customer> getQueue()`: unmodifiable, oldest first
- `List<Table> getTables()`: unmodifiable, always 2 tables, table 1 then table 2
- `List<Order> getOrders()`: unmodifiable, every order ever taken, oldest first
- `List<Customer> getCustomers()`: unmodifiable, every customer who has arrived, in arrival order. Used for the final summary
- `MenuItem[] getMenu()`

### ConsoleUI (owner: D)
- `ConsoleUI(GameController game, InputReader input)`
- `void run()`: show the menu once, then loop until `game.isGameOver()`: startTurn, show state, ask Happy Hour (only while it can be activated), ask Host (only when it can seat someone), ask Waiter, ask Chef, endTurn, show results; then show Victory or Defeat
- `static String formatCents(int cents)`: delegates to `Money.format`

### InputReader (owner: D)
- `InputReader(Scanner scanner)`
- `int readInt(String prompt, int min, int max)`: throws `GameQuitException` if the player types `q` or input ends

## Food-choice rule

Customer `id` (1 to 6, in arrival order) orders `menu[(id - 1) % menu.length]`. This is a zero-based array index: 0 = Salad, 1 = Burger, 2 = Pasta, and from Stage 3, 3 = Salad + Pasta Combo, 4 = Salad + Burger Combo.

Stages 1 and 2 (3 menu items, so `% 3`):

| Customer | 1 | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|---|
| Orders | Salad | Burger | Pasta | Salad | Burger | Pasta |

Deterministic, so every run with the same inputs gives the same game. The order is printed when the Waiter takes it.

Stage 2: VIPs follow the same rule. The Critic ignores it and always orders the most expensive dish (Pasta, $18.00, 2 units). This is a group-defined Critic behaviour and was chosen because it is deterministic (runs stay reproducible) and it is a real method override, not just a different number.
- Effect: The Critic holds the kitchen for 2 turns while losing 12 per waiting turn.

Stage 3 (combo-choice rule, brief 4.3): the same rule now runs over all five menu items (`% 5`), and the Critic's "most expensive" now includes combos. With customer 2 as the VIP and customer 4 as the Critic:

| Customer | 1 | 2 (VIP) | 3 | 4 (Critic) | 5 | 6 |
|---|---|---|---|---|---|---|
| Orders | Salad | Burger | Pasta | Salad + Pasta Combo | Salad + Burger Combo | Salad |

We kept the rule instead of adding a separate combo rule: it needs no new code or type checks, both combos get ordered, and the game stays winnable (see `tests/victory.txt`). The trade-off is that choices depend on the menu's order and length, so adding the combos to `Menu` changed what customers 4 to 6 order without any change to `Customer`.
- Effect: The Critic now holds the kitchen for 3 turns ($24.30, 3 units) while losing 12 per waiting turn.

## Decisions (formerly open questions)

1. Display after arrivals: step 1 runs, then the display, so the manager sees the new arrival before choosing.
2. Cashier order: the customer who became READY_TO_PAY first pays first. In Stage 1 at most one customer is ever READY_TO_PAY when the Cashier acts: the Waiter serves at most one order per turn, so no two customers finish eating on the same turn, and the Cashier pays every turn before new customers finish eating. The code therefore takes the first READY_TO_PAY customer in its order list, which is always that customer. If a later stage lets two customers be ready at once (e.g. two serves in one turn), `payOldestReadyCustomer()` must instead compare the turn each became ready, ties broken by lower customer id. Still true in Stage 3, the Host only seats customers, so the Waiter still serves at most one order per turn.
3. When a customer leaves, any order not yet SERVED (IN_PREPARATION or READY) is cancelled. Customers with no order simply leave.
4. Waiter menu: 1 Seat, 2 Take order, 3 Serve, 0 Wait. Seat picks from the queue, then a table (1 to 2). Take order picks from customers at tables. Serve and Chef Prepare pick from open orders (not PAID or CANCELLED). The controller decides whether the pick is allowed. Before the Waiter: Happy Hour 1 / 0, then Host 1 Seat / 0 Skip.
5. Read-only access for the UI: rely on the "getters only" rule. No read-only interfaces in any stage (see ISP above).
