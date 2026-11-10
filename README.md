# Restaurant Rush

SC2002 group project: a turn-based restaurant management game played in the command line. You are the manager. Each turn you decide what the Waiter and the Chef do, and the Cashier collects payments automatically. Serve and charge enough customers in 18 turns to win.

Repository: https://github.com/nyunxi/sc2002-restaurant-rush

## Stages

| Stage | Adds | Tag |
|---|---|---|
| 1 Basic operation | Regular customers, menu, Waiter, Chef, Cashier, full turn sequence, victory and defeat | [`stage-1`](https://github.com/nyunxi/sc2002-restaurant-rush/tree/stage-1) |
| 2 Customer behaviour | VIP and Critic customers with their own waiting loss, bill and food choice (polymorphism through overriding); customer type shown on screen | [stage-2](https://github.com/nyunxi/sc2002-restaurant-rush/tree/stage-2) |
| 3 New role and menu policy | To be added | `stage-3` |

## Build and run

Requires Java 17 or later. From the repository root:

```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main
```

## How to play

The game always runs 18 turns. Each turn:

1. A new customer may arrive (turns 1, 4, 7, 10, 13 and 16).
2. The screen shows revenue, average satisfaction, the waiting queue, both tables, and each customer's satisfaction and order.
3. You choose one Waiter task: `1` seat a customer, `2` take an order, `3` serve a ready order, or `0` wait. Then you pick the customer, order or table by number.
4. You choose the Chef's task: `1` prepare one unit of an order, or `0` wait.
5. At the end of the turn, the Cashier charges one customer who is ready to pay, and customers still waiting for a table or food lose satisfaction.

If a choice is not allowed, the game explains why and asks again without using up the task.

**Victory:** after turn 18, at least 4 paid customers, revenue of at least $45.00, and average satisfaction of paid customers of at least 60. Otherwise **Defeat**.

| Menu item | Price | Preparation units |
|---|---|---|
| Salad | $9.00 | 1 |
| Burger | $15.00 | 1 |
| Pasta | $18.00 | 2 |

Six customers arrive, one on each arrival turn. Customer 2 is a **VIP** and customer 4 is a **Critic**, the rest are regular customers.

| Customer type | Satisfaction lost per waiting turn | Bill | Food Choice |
| --- | --- | --- | --- |
| Regular | 8 | Full menu price | By customer number: 1 Salad, 2 Burger, 3 Pasta, then cycle repeats |
| VIP | 5 | 10% off menu price | Same rules as Regular |
| Critic | 12 | Full menu price | Always orders the most expensive dish (Pasta) |

So customer 2 (VIP) orders a Burger for $13.50, and customer 4 (Critic) orders a Pasta instead of the Salad the cycle would give.


**To quit early,** type `q` at any prompt (or press Ctrl+D). The game stops without a result.

## Reproducible test runs

Each file in `tests/` holds one answer per line, so a run can be replayed exactly:

```
java -cp out restaurantrush.Main < tests/victory.txt
java -cp out restaurantrush.Main < tests/defeat.txt
```

Expected results and the brief rule each run checks: [Stage 1 notes](docs/test-notes-stage1.md) (for `stage-1`), [Stage 2 notes](docs/test-notes-stage2.md) (for `stage-2`, current).

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
| Yunxi | CLI, input handling, testing and releases |
