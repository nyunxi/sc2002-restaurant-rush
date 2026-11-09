# Restaurant Rush

SC2002 group project: a turn-based restaurant management game played in the command line. You are the manager. Each turn you decide what the Waiter and the Chef do, and the Cashier collects payments automatically. Serve and charge enough customers in 18 turns to win.

Repository: https://github.com/nyunxi/sc2002-restaurant-rush

## Stages

| Stage | Adds | Tag |
|---|---|---|
| 1 Basic operation | Regular customers, menu, Waiter, Chef, Cashier, full turn sequence, victory and defeat | [`stage-1`](https://github.com/nyunxi/sc2002-restaurant-rush/tree/stage-1) |
| 2 Customer behaviour | To be added | `stage-2` |
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

Six customers arrive, one on each arrival turn. Customer 2 is a **VIP** and customer 4 is a **CRITIC**, the rest are regular customers.
| Customer type | Satisfaction lost per waiting turn | Bill | Food Choice |
| --- | --- | --- | --- |
| Regular | 8 | Full menu price | By customer number: 1 Salad, 2 Burger, 3 Pasta, then cycle repeats |
| VIP | 5 | 10% of menu price | Same rules as Regular |
| CRITIC | 12 | Full menu price | Always orders the most expensive dish (Pasta) |

so customer 2 (VIP) orders a Burger for $13.50, and customer 4 (CRITIC) orders a Pasta instead of the Salad the cycle would give.


**To quit early,** type `q` at any prompt (or press Ctrl+D). The game stops without a result.

## Reproducible test runs

Each file in `tests/` holds one answer per line, so a run can be replayed exactly:

```
java -cp out restaurantrush.Main < tests/victory.txt
java -cp out restaurantrush.Main < tests/defeat.txt
```

`victory.txt` ends in Victory (4 paid, $51.00, average 66.0) and `defeat.txt` ends in Defeat. [`docs/test-notes.md`](docs/test-notes.md) lists every test file, its expected result, and which rule from the brief it checks.

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
| Yunxi | CLI, input handling, testing and releases |
| Paige | Customers |
| Enoch | Menu and pricing |
| Rajvi | Turn engine and staff |
