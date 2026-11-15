# Restaurant Rush

SC2002 group project: a turn-based restaurant management game played in the command line. You are the manager. Each turn you decide what the Host, the Waiter and the Chef do, and when to run a once-per-game Happy Hour. The Cashier collects payments automatically. Serve and charge enough customers in 18 turns to win.

Repository: https://github.com/nyunxi/sc2002-restaurant-rush

## Stages

| Stage | Adds | Tag |
|---|---|---|
| 1 Basic operation | Regular customers, menu, Waiter, Chef, Cashier, full turn sequence, victory and defeat | [`stage-1`](https://github.com/nyunxi/sc2002-restaurant-rush/tree/stage-1) |
| 2 Customer behaviour | VIP and Critic customers with their own waiting loss, bill and food choice (polymorphism through overriding); customer type shown on screen | [stage-2](https://github.com/nyunxi/sc2002-restaurant-rush/tree/stage-2) |
| 3 New role and menu policy | Host (seats one extra customer per turn), combo meals, Happy Hour (once per game: 20% off and +15 satisfaction for 2 turns) | [`stage-3`](https://github.com/nyunxi/sc2002-restaurant-rush/tree/stage-3) |

## Build and run

Requires Java 17 or later. From the repository root:

```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main
```

## How to play

The game always runs 18 turns. Each turn:

1. A new customer may arrive (turns 1, 4, 7, 10, 13 and 16).
2. The screen shows revenue, average satisfaction, Happy Hour status, the waiting queue, both tables, and each customer's satisfaction and order.
3. Until you have used it, you are asked whether to switch on **Happy Hour** (`1` yes, `0` no). It lasts this turn and the next: orders taken get 20% off, and customers still waiting for a table or food recover 15 satisfaction at the start of each active turn (maximum 100). Once per game.
4. If someone is queued and a table is free, the **Host** can seat one customer (`1`) or skip (`0`). This is on top of the Waiter's task.
5. You choose one Waiter task: `1` seat a customer, `2` take an order, `3` serve a ready order, or `0` wait. Then you pick the customer, order or table by number.
6. You choose the Chef's task: `1` prepare one unit of an order, or `0` wait.
7. At the end of the turn, the Cashier charges one customer who is ready to pay, and customers still waiting for a table or food lose satisfaction.

If a choice is not allowed, the game explains why and asks again without using up the task.

**Victory:** after turn 18, at least 4 paid customers, revenue of at least $45.00, and average satisfaction of paid customers of at least 60. Otherwise **Defeat**.

| Menu item | Price | Preparation units |
|---|---|---|
| Salad | $9.00 | 1 |
| Burger | $15.00 | 1 |
| Pasta | $18.00 | 2 |
| Salad + Pasta Combo | $24.30 | 3 |
| Salad + Burger Combo | $21.60 | 2 |

A combo is two existing items: its preparation units are the sum, and its price is 90% of the two prices together, rounded to the cent. The menu is shown at the start of the game.

Six customers arrive, one on each arrival turn. Customer 2 is a **VIP** and customer 4 is a **Critic**, the rest are regular customers.

| Customer type | Satisfaction lost per waiting turn | Bill | Food Choice |
| --- | --- | --- | --- |
| Regular | 8 | Full menu price | By customer number, cycling through the menu in order: 1 Salad, 2 Burger, 3 Pasta, 4 Salad + Pasta Combo, 5 Salad + Burger Combo, 6 Salad |
| VIP | 5 | 10% off menu price | Same rules as Regular |
| Critic | 12 | Full menu price | Always orders the most expensive item (Salad + Pasta Combo) |

So customer 2 (VIP) orders a Burger for $13.50 ($10.80 if ordered during Happy Hour: the VIP discount first, then 20% off), and customer 4 (Critic) orders the Salad + Pasta Combo. The price is fixed when the order is taken.


**To quit early,** type `q` at any prompt (or press Ctrl+D). The game stops without a result.

## Reproducible test runs

Each file in `tests/` holds one answer per line, so a run can be replayed exactly:

```
java -cp out restaurantrush.Main < tests/victory.txt
java -cp out restaurantrush.Main < tests/victory-no-happy-hour.txt
java -cp out restaurantrush.Main < tests/defeat.txt
```

`victory-no-happy-hour.txt` is `victory.txt` with one choice changed: Happy Hour is declined at turn 14. It shows how that single decision changes the result (still a victory, but average satisfaction falls from 82.5 to 71.0 and the Critic leaves).

Expected results and the brief rule each run checks: [Stage 1 notes](docs/test-notes-stage1.md) (for `stage-1`), [Stage 2 notes](docs/test-notes-stage2.md) (for `stage-2`), [Stage 3 notes](docs/test-notes-stage3.md) (for `stage-3`, current).

**Added after the `stage-3` tag** (on `main`; the game code is unchanged from `stage-3`):
- `tests/victory-no-happy-hour.txt`: the comparison run above
- `docs/uml/stage-3-class-viewA.puml`, `docs/uml/stage-3-class-viewB.puml`: the Stage 3 class diagram split into two views for the report (the same model as `stage-3-class.puml`)

## Video Walkthrough

**Video file:** `SC2002 Restaurant Rush Video Presentation.mp4` (included in the submission zip)
**Length:** 15:00
**Recorded against:** the `stage-3` tag

| Time | Segment | Presenter |
|------|---------|-----------|
| | **1. Game overview and the three stages** | |
| 0:00 | 1.1 Game overview and the three stages | Rajvi |
| 1:15 | 1.2 Game launching from the submitted project | Rajvi |
| | **2. Gameplay demonstration and key outcomes** | |
| 1:50 | 2.1 Manager choices and constrained staff | Yun Xi |
| 3:05 | 2.2 A dish going READY → SERVED → payment | Enoch |
| 4:55 | 2.3 One victory and one defeat | Rajvi |
| | **3. Class diagrams and evidence of stage changes** | |
| 6:11 | 3.1 Final class diagram, responsibilities and cohesion | Paige |
| 7:25 | 3.2 Stage 1 → 2: VIP and Critic | Paige |
| 8:06 | 3.3 Stage 2 → 3: ComboMeal and Happy Hour pricing | Enoch |
| 9:05 | 3.4 Stage 2 → 3: Host, Happy Hour turn flow and tags | Yun Xi |
| | **4. Selected use case and sequence diagram** | |
| 10:05 | 4.1 Selected use case | Paige |
| 11:13 | 4.2 Sequence diagram | Yun Xi |
| 12:15 | 4.3 Trade-offs and an alternative design | Enoch |
| | **5. Individual contributions and key learning** | |
| 12:55 | 5. Individual reflections | Paige, Enoch, Rajvi, Yun Xi |

## Stage 1 to 2 changes 
Compare: https://github.com/nyunxi/sc2002-restaurant-rush/compare/stage-1...stage-2

**New files**
- `VIPCustomer.java`, `CriticCustomer.java`: each overrides the `Customer` methods that differ for its type
- `docs/uml/stage-2-class.*`: Stage 2 class diagram
- `docs/test-notes-stage2.md`: Stage 2 results and the brief rules they check
- `docs/test-notes-stage1.md`: Stage 1 results and the brief rules they check, added after the stage-1 tag

**Changed files**

| File | Why |
|---|---|
| `Customer.java` | Waiting loss was a fixed 8. It now calls the overridable `getWaitingLoss()`, and adds `getDiscountRate()` and `getCustomerType()`, so a new type needs no edit here |
| `Order.java` | The bill now applies the customer's own discount rate when the order is taken |
| `Money.java` | Added `percentOff` and `applyDiscountRate` so discounts stay in integer cents (brief 2.2) |
| `GameController.java` | Creates customer 2 as a VIP and customer 4 as a Critic. This is the only place that names the subclasses |
| `ConsoleUI.java` | Shows each customer's type and the bill when an order is taken |
| `Menu.java` | Comments and formatting only; no behaviour change |
| `tests/victory.txt` | Re-planned so the Critic is served in time |
| `docs/design-decisions.md` | Stage 2 customer types, the Critic rule, where customers are created |
| `docs/test-notes-stage1.md` | Renamed from `test-notes.md`; content unchanged |

**Unchanged:** Regular customers behave as in Stage 1. There are still six arrivals on the same turns, the same menu, the same turn steps and the same win and lose targets. All five Stage 1 test files still run to the end; see [Stage 2 notes](docs/test-notes-stage2.md).

## Stage 2 to 3 changes
Compare: https://github.com/nyunxi/sc2002-restaurant-rush/compare/stage-2...stage-3

**New files**
- `Host.java`: the Host role, one seat per turn on top of the Waiter's task
- `ComboMeal.java`: a `MenuItem` made of two items (summed units, 90% price)
- `HappyHour.java`: the once-per-game pricing policy and its countdown
- `docs/test-notes-stage3.md`: Stage 3 results and the brief rules they check
- `docs/uml/stage-3-class.*`: Stage 3 class diagram
- `docs/uml/stage-3-sequence.*`: Stage 3 sequence diagram (the VIP's order, with and without Happy Hour)

**Changed files**

| File | Why |
|---|---|
| `Menu.java` | Adds the two combos after the three dishes |
| `Order.java` | Applies Happy Hour after the customer's discount when the order is created, so the price is locked |
| `Customer.java` | Adds `recoverHappyHourSatisfaction()`; each customer decides whether it is waiting |
| `Waiter.java` | `takeOrder` passes Happy Hour into the new order (the price must be fixed at order time); its refusal message now names the customer's state |
| `GameController.java` | Owns the Host and Happy Hour; activation only at the start of a turn; recovery at the start of each active turn |
| `ConsoleUI.java` | Shows the menu, the Happy Hour status and prompt, the Host prompt, and marks Happy Hour prices |
| `tests/*.txt` | Rebuilt for the two new prompts; `victory.txt` re-planned to use every Stage 3 feature |
| `docs/design-decisions.md` | Stage 3 classes, design choices, method signatures and the combo-choice rule |

**Unchanged:** the turn steps, staff limits, six arrivals, customer types and the win and lose targets. Food choice uses the same rule as before, but over five menu items, so customers 4 to 6 now order differently; see [design decisions](docs/design-decisions.md#food-choice-rule). `defeat.txt` and `happy.txt` give the same results as in Stage 2.

## Project structure

```
src/restaurantrush/   Java source (package restaurantrush)
tests/                Input files for reproducible runs
docs/                 Design decisions and test notes
```

Design: [`docs/design-decisions.md`](docs/design-decisions.md) covers class responsibilities, method signatures and turn rules.

## Team

| Member | Role |
|---|---|
| Paige | Customers |
| Enoch | Menu and pricing |
| Rajvi | Turn engine and staff |
| Yunxi | CCLI, input handling, testing and releases; Stage 3 Host and Happy Hour wiring; Stage 3 class and sequence diagrams |
