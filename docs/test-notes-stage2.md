# Test notes: Stage 2

Base setting (brief 2.1). Inputs in `tests/`, one answer per line. Build and run as in [Stage 1 notes](test-notes-stage1.md):

    javac --release 17 -d out src/restaurantrush/*.java
    java -cp out restaurantrush.Main < tests/victory.txt

Customer Type: customer 2 is the VIP and customer 4 is the critic, the rest of the customers are regulars

Food choice is deterministic Regulars and VIPs order by arrival position (1 Salad, 2 Burger, 3 Pasta, 4 Salad, 5 Burger, 6 Pasta) and the critic always orders the most expensive item which is the Pasta. With the base customer schedule: 1 Salad, 2 Burger (VIP), 3 Pasta, 4 Pasta (Critic), 5 Burger, 6 Pasta. 

## Reproducible runs
| File | Result | Change from Stage 1 |
|---|---|---|
| `victory.txt` | **VICTORY**: 5 paid, revenue $73.50, average satisfaction 75.6 | Inputs re-planned so the Critic is served before its satisfaction runs out. Revenue includes the VIP Burger at $13.50 |
| `defeat.txt` | **DEFEAT**: 0 paid, $0.00, average N/A, 2 unhappy departures | Departures are now customers 1 (end of turn 13) and 4, the Critic (end of turn 18). The VIP, who left in Stage 1, now loses only 5 a turn and is still at 25 |
| `happy.txt` | DEFEAT: 1 paid, $9.00 | Unchanged |
| `rejects.txt` | Each invalid action refused with a reason; task not used up | Unchanged |
| `failures.txt` | Re-prompts every time; no crash | Unchanged |

## Stage 2 rules checked
| Rule | Source | Where it shows |
|---|---|---|
| Satisfaction lost per waiting turn: Regular 8, VIP 5, Critic 12 | Brief 4.2 table; 3.2 step 5 | `defeat.txt` final list: VIP at 25, Regular customer 5 at 52; Critic drops from 64 to 52 between turns 13 and 14 |
| VIP pays 10% off | Brief 4.2 table | `victory.txt`: Order #2 Burger shows `$13.50 (menu $15.00)` |
| Discounted price stored as integer cents | Brief 2.2 (Money) | 1500 cents x 0.9 = 1350 cents, displayed as $13.50 |
| Bill fixed when the order is taken | Design decision (from Stage 1) | `victory.txt`: Order #2 stays $13.50 from ordering through payment |
| Critic: always orders the most expensive item | Group-defined Critic behaviour (brief 4.2: "a distinct order preference... that you define and document"); documented in `design-decisions.md` | `victory.txt`: customer 4 orders Pasta ($18.00) |
| At least one VIP and one Critic; still six arrivals | Brief 4.2 | Customer 2 is VIP, customer 4 is Critic; game-over list shows six customers |
| All types follow the same order and service flow | Brief 4.2 | `victory.txt`: VIP and Critic go through seated, ordered, served, paid like Regulars |
| Behaviour dispatched through customer objects, no type checks in the controller | Brief 4.2 | `grep -n instanceof src/restaurantrush/*.java` prints nothing |
| Same win and lose criteria | Brief 4.2; targets in 2.1 | Game-over screen still checks 4 paid, $45.00, average 60 |
| Customer at 0 leaves from a table; unfinished order cancelled, table freed | Brief 3.2 step 5 (gap in Stage 1 notes) | `rejects.txt`: customer 1 ends `LEFT`, Salad 0/1 `Cancelled` |
| Customer type shown on screen | Design decision (UI only displays state, brief 2.2) | Every customer line shows `(Regular)`, `(VIP)` or `(Critic)` |