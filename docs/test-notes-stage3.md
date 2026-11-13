# Test notes: Stage 3

All runs use the base setting from brief 2.1. Each file in `tests/` holds one answer per line, so you can replay any run exactly. Build and run the same way as in the [Stage 1 notes](test-notes-stage1.md):

    javac --release 17 -d out src/restaurantrush/*.java
    java -cp out restaurantrush.Main < tests/victory.txt

Stage 3 adds two questions at the start of a turn, so we rebuilt every input file:

- **Happy Hour** (`1` yes, `0` no): asked every turn until you use it.
- **Host** (`1` seat someone, `0` skip): asked only when someone is waiting in the queue and a table is free.

Who orders what (see the [food-choice rule](design-decisions.md#food-choice-rule)): customer 1 Salad, 2 Burger (VIP), 3 Pasta, 4 Salad + Pasta Combo (Critic), 5 Salad + Burger Combo, 6 Salad.

## Reproducible runs

| File | Result | What changed since Stage 2 |
|---|---|---|
| `victory.txt` | **VICTORY**: 4 paid, revenue $59.40, average satisfaction 82.5 | Re-planned so one run shows every Stage 3 feature: the Host seats customer 5 on turn 13, Happy Hour goes on at turn 14, the VIP pays $10.80 for a Burger, and a combo is paid for |
| `victory-no-happy-hour.txt` | **VICTORY**: 4 paid, revenue $62.10, average satisfaction 71.0, 1 unhappy departure (the Critic, end of turn 18) | New, added after the `stage-3` tag. The same answers as `victory.txt`, except Happy Hour is declined at turn 14, and the Happy Hour question (which keeps appearing until it is used) is answered `0` on turns 15 to 18 |
| `defeat.txt` | **DEFEAT**: 0 paid, $0.00, average N/A, 2 unhappy departures (customers 1 and 4) | Only `0`s added for the new questions. Same result as Stage 2 |
| `happy.txt` | DEFEAT: 1 paid, $9.00, average 84.0 | Only `0`s added for the new questions. Same result as Stage 2 |
| `rejects.txt` | Every invalid action is refused with a reason, and the task is not used up | Now also tries a bad Host move: on turn 4 the Host tries to seat customer 2 at Table 1, which is taken |
| `failures.txt` | Bad input is re-asked every time, and the game never crashes | Now also types `5` at the Happy Hour question |

The choices in `victory.txt` matter. `victory-no-happy-hour.txt` replays it with Happy Hour declined at turn 14. You still win, but no one recovers satisfaction: the VIP pays $13.50 instead of $10.80 and finishes at 40 instead of 70, customer 5 finishes at 84 instead of 100, and the Critic, still waiting, reaches 0 and leaves at the end of turn 18. Revenue is $2.70 higher ($62.10), but the average drops from 82.5 to 71.0.

## Stage 3 rules checked

| Rule | Source | Where you can see it |
|---|---|---|
| The Host seats at most one waiting customer per turn, on top of the Waiter's task | Brief 4.3 | `victory.txt`, turn 13: the Host seats customer 5, and the Waiter takes the same customer's order in the same turn |
| A refused Host move does not use up the Host's action | Brief 3.4 | `rejects.txt`, turn 4: `Not possible: Table 1 is occupied.`, then the Host is asked again |
| A combo is two existing items. Its units add up, and its price is 90% of the two prices, rounded to the cent | Brief 4.3 | The menu at the start of every game shows `Salad + Pasta Combo ($24.30, 3 units)` and `Salad + Burger Combo ($21.60, 2 units)`. In `victory.txt`, customer 5's combo takes the Chef 2 turns and is charged $21.60 |
| Customers choose combos by a rule we wrote down | Brief 4.3 | [Food-choice rule](design-decisions.md#food-choice-rule) |
| Happy Hour can be used once, at the start of a turn, lasts that turn and the next, and shows how long is left | Brief 4.3 | `victory.txt`: switched on at turn 14 (`2 turn(s) left`), `1 turn(s) left` on turn 15, `used` from turn 16, and the question never comes back |
| Orders taken during Happy Hour get 20% off, after the VIP discount | Brief 4.3 | `victory.txt`, turn 14: the VIP's Burger shows `$10.80 (Happy Hour price)`. That is $15 × 0.9 × 0.8, the brief's own example |
| Customers waiting for a table or food get +15 satisfaction at the start of each Happy Hour turn (up to 100) | Brief 4.3 | `victory.txt`: the VIP goes from 50 to 65 when Happy Hour starts on turn 14, then from 60 to 75 at the start of turn 15 |
| The price is fixed when the order is taken | Brief 4.3 | `victory.txt`: the VIP is still charged $10.80 on turn 18, after Happy Hour has ended |
| Happy Hour does not change orders placed before it | Brief 4.3 | `victory.txt`: customer 5's combo, ordered on turn 13, stays at $21.60 |
| It cannot be switched on a second time | Brief 4.3 | Once used, the game stops asking. `GameController.activateHappyHour()` would also refuse a second call (checked in code review) |

## Stage 1 and 2 behaviour still works

| What we checked | Result |
|---|---|
| `defeat.txt` and `happy.txt` on the Stage 3 code | Same results as in the [Stage 2 notes](test-notes-stage2.md): the same waiting losses (Regular 8, VIP 5, Critic 12), the same departures, and the same READY → SERVED → payment steps |
| Turn order, staff limits, arrival turns, and the win and lose targets | Unchanged |
| No type checks in the game code | `grep -n instanceof src/restaurantrush/*.java` prints nothing |
| Food choice | Changed on purpose. The menu grew from 3 items to 5, so customers 4 to 6 now order different things. The [food-choice rule](design-decisions.md#food-choice-rule) explains why |
