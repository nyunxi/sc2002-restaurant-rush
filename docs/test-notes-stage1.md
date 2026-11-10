# Test notes: Stage 1

All runs use the required base setting (brief 2.1). Input files are in `tests/`. Each line is one answer typed at a prompt, so every run is reproducible.

Build and run a file:

```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main < tests/victory.txt
```

Food choice is deterministic (customer 1 Salad, 2 Burger, 3 Pasta, 4 Salad, 5 Burger, 6 Pasta), so the same input always gives the same game.

## Reproducible runs

| File | What it does | Expected result |
|---|---|---|
| `victory.txt` | Each turn the Waiter serves a READY order if there is one, else takes a seated customer's order, else seats the oldest queued customer. The Chef always works on the oldest order in preparation | **VICTORY**: 4 paid, revenue $51.00, average satisfaction 66.0 |
| `defeat.txt` | Waiter and Chef wait on every turn | **DEFEAT**: 0 paid, revenue $0.00, average N/A, 2 unhappy departures. Waiting costs 8 a turn, so a customer needs 13 turns to reach 0: customers 1 and 2 leave (end of turns 13 and 16); the rest are still waiting at turn 18 |
| `happy.txt` | Customer 1 seated, ordered, served on turns 1 to 3, then the manager waits | DEFEAT: 1 paid, $9.00. Shows READY to SERVED to payment for one customer |
| `rejects.txt` | Serves an order that is not READY; seats a customer at an occupied table | Each is refused with a reason and the Waiter chooses again without losing the task |
| `failures.txt` | Non-numeric and out-of-range input; choosing an action with nothing to pick; Chef preparing a READY order | Re-prompts every time; no crash |

## Rules checked against the brief

| Brief rule | Where it shows |
|---|---|
| Dish finished this turn cannot be served until next turn (3.2 step 3) | `victory.txt`: Customer 3's Pasta reaches 2/2 READY, served the following turn |
| One full turn of eating, then READY_TO_PAY, then paid next turn at the earliest (step 4) | `happy.txt`: served turn 3, READY_TO_PAY end of turn 4, paid end of turn 5 |
| Waiting, seated and ordered customers lose 8 per turn; served customers lose nothing (step 5) | `happy.txt`: Customer 1 at 92 after turn 1, 84 after turn 2, stays 84 once served |
| Customer at 0 leaves; order cancelled, table freed (step 5) | `defeat.txt`: customers 1 and 2 leave from the queue, unhappy departures 2. Not yet covered: a customer leaving from a table with an unfinished order |
| Invalid choice explains why and does not use up the task (3.4) | `rejects.txt`, `failures.txt` |
| Average uses satisfaction at payment; N/A before anyone pays (3.5) | `defeat.txt` (N/A), `victory.txt` (66.0) |