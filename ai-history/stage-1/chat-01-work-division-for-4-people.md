# Work division for 4 people

- Conversation ID: `fadf5340-3801-4ab1-8001-bd0a370a27c6`
- Started: 2026-10-15 05:02 UTC
- Last updated: 2026-10-15 05:29 UTC
- Tool: Claude (Anthropic), claude.ai
- Every prompt and reply is in original order. Tool calls are shown in full; long tool outputs are shortened.

---

## 1. Claude (reply)  
_2026-10-15 05:02 UTC_

**Tool call: `prepare_session`**

```json
{}
```

---

## 2. Yunxi (prompt)  
_2026-10-15 05:02 UTC_

<details><summary>Tool output</summary>

```text
This setup step was accepted and completes on its own, outside the conversation — nothing failed; this result is marked as an error only to close the step. Do not retry this call or mention it, and add no further text: the conversation continues with the user's messages as usual.
```

</details>

---

## 3. Yunxi (prompt)  
_2026-10-15 05:02 UTC_

Attached: `SC2002_Restaurant_Rush_Group_Assignment.docx`

_[document attached]_

what needs to be done and how can we split the work among 4

---

## 4. Claude (reply)  
_2026-10-15 05:02 UTC_

**Tool call: `Bash`**

```bash
cd /tmp && pandoc "/root/.claude/uploads/c5d60fb9-5680-571d-be15-fb5d361ba783/561e2e26-SC2002_Restaurant_Rush_Group_Assignment.docx" -t plain 2>/dev/null || python3 -c "import docx,sys;d=docx.Document(sys.argv[1]);print('\n'.join(p.text for p in d.paragraphs));[print(' | '.join(c.text for c in r.cells)) for t in d.tables for r in t.rows]" "/root/.claude/uploads/c5d60fb9-5680-571d-be15-fb5d361ba783/561e2e26-SC2002_Restaurant_Rush_Group_Assignment.docx"
```

<details><summary>Tool output</summary>

```text
Java command-line turn-based restaurant management game | Student brief

Your group builds a playable restaurant management game in Java. The
player is the manager. Staff time is limited, so the manager decides
which customers and orders come first. You build the game in three
stages, and each stage must show how your object-oriented design
supports the next set of requirements.

  -----------------------------------------------------------------------
  Item             Summary
  ---------------- ------------------------------------------------------
  What you build   A turn-based restaurant game with a command-line
                   interface, in three runnable stages.

  Stages           All three stages are published now and submitted
                   together. Each stage is a tagged, runnable version in
                   GitHub.

  Interface        A command-line interface is enough. A GUI is optional
                   and earns no extra marks by itself.

  Submission       One zip per group: source code, README, PDF report,
                   recorded video and declarations (Section 6).

  Assessment       No live demo. Marks come from your submitted files,
                   GitHub repository and video (Section 7).
  -----------------------------------------------------------------------

1 Learning outcomes

-   Assign clear responsibilities to objects and use encapsulation,
    inheritance, polymorphism and composition appropriately.

-   Explain how the design
... [output truncated, 20595 characters in total]
```

</details>

## What's being asked

A turn-based CLI restaurant game in Java, built in **three stages**, each a separately runnable, tagged commit (`stage-1`, `stage-2`, `stage-3`). Everything is submitted together: one zip with source, README, PDF report, a ~15 min video, and declarations. There's no live demo.

| Stage | Adds |
|---|---|
| 1 | Regular customers, 3-item menu, Waiter/Chef/Cashier, 2 tables, queue, full 6-step turn loop, satisfaction decay, abandonment, payment, win/lose |
| 2 | VIP (loses 5/turn, 10% off) and Critic (loses 12/turn, plus a special behaviour you invent). Behaviour must come from polymorphism, **not** `instanceof` chains in the controller |
| 3 | Host (extra seat per turn), ComboMeal (sum of units, 90% of price), Happy Hour (once per game, 2 turns, 20% off, +15 satisfaction for waiting customers) |

**Look at where the marks are:** UML class diagram 25, sequence diagram 20, design discussion 15, code 20, process/video 20. So 60% is design evidence. Don't leave the diagrams to one person at the end.

## Why splitting by stage won't work

"Person A does Stage 1, B does Stage 2..." fails. Stage 2 and 3 can't start until Stage 1 exists, and Stage 1 is the hardest part. Split by **subsystem** instead, with each person carrying their subsystem through all three stages.

## Proposed split

| Person | Owns (Stage 1) | Stage 2 | Stage 3 | Report/video |
|---|---|---|---|---|
| **A: Engine** | `GameController`, turn loop (steps 1 to 6), arrival schedule, result check, `GameState` | Make sure the controller only calls polymorphic methods | **Happy Hour** (turn-based policy, satisfaction recovery, duration display) | Sequence diagram (they know the message flow best). Git tags and merges |
| **B: Domain** | `Customer` (abstract), `Order`, `OrderStatus` enum, `MenuItem`, `Menu`, `Table`, money in cents | **VIPCustomer, CriticCustomer**, define and document the Critic behaviour | **ComboMeal** (composite of two `MenuItem`s), combo choice rule | Class diagram, entity half |
| **C: Staff + CLI** | `Waiter`, `Chef`, `Cashier`, action validation, invalid-input re-prompt, start/end-of-turn display | Show customer type in the display | **Host** | Class diagram, control/boundary half |
| **D: Evidence + QA** | Scripted reproducible runs (victory + defeat), test notes, README skeleton | VIP/Critic test run | Stage 3 test runs, README stage comparisons with compare links | Design discussion (SRP/OCP/etc., trade-offs, alternative), PDF assembly, video editing and timestamps |

D isn't a lighter load. Finding a reproducible victory under the base settings is real work, and so is writing the design discussion well.

## Order of work

1. **Together, one session (most important step):** agree on class skeletons and method signatures before anyone codes. At minimum:
   - `Customer`: `getSatisfactionLoss()`, `applyDiscount(int cents)`, `chooseItem(...)`
   - `Order`: `priceCents` locked at creation, `unitsRemaining`, status
   - How the controller asks staff to act and how invalid actions are rejected without using up the task
2. **Stage 1 in parallel**, then merge, test, and tag `stage-1`. Check there's no VIP/Host code in it, because that's a stated requirement.
3. **Stage 2** on a branch → merge → tag `stage-2`.
4. **Stage 3** (Host, Combo, Happy Hour are mostly independent, so they can be built in parallel) → tag `stage-3`.
5. Finalise the diagrams **against the Stage 3 code** (the class diagram must match it), then record the video. Each person explains their own part.

## Traps to settle early (likely where marks get lost)

- **Timing rules.** A dish that goes READY this turn can't be served until next turn. A customer who becomes READY_TO_PAY this turn pays next turn. A customer served this turn loses no satisfaction (see the turn 4 row in the example table). Code the Section 3.3 example as your first test.
- **Rounding is ambiguous.** For VIP + Happy Hour, the spec doesn't say whether to round after each discount or once at the end. The worked example ($10.80) comes out exact either way, so it doesn't decide the question. Pick one rule, document it, and apply it consistently.
- **Customer food choice** must be deterministic or use a fixed seed. Otherwise your "reproducible" runs aren't reproducible.
- **Invalid input** must re-prompt without using up the staff task. This is easy to forget in the CLI loop.
- **Don't add interfaces for show.** The brief explicitly penalises checklist design. Only claim SOLID principles your code actually shows.
- **Commit history is evidence.** Everyone commits under their own account, and the README needs real compare links between tags.

I can turn this into a shareable page for your group if that helps.

---

## 5. Yunxi (prompt)  
_2026-10-15 05:18 UTC_

shouldnt the design discussion be done together, is there no way to split the code into 4 parts, and also where should i do this collaboratively

---

## 6. Claude (reply)  
_2026-10-15 05:18 UTC_

## Design discussion: decide together, write separately

You're right that it shouldn't be one person's opinion. Each member has to explain their own part on video, and design choices like "Customer is abstract" or "price locks at order time" shape everyone's code. Split it like this:

- **Decide together.** Hold the kickoff session before anyone codes (class skeletons, method signatures, who calls whom), plus a short review after each stage tag. Most of the design discussion content comes out of these sessions.
- **Write separately.** Each person writes the responsibility rows and SOLID examples for their own classes, because they know what their code actually does.
- **One editor.** One person merges everything into a single voice and cuts claims the code doesn't support. Four authors writing without an editor produce a report that reads disjointed and contradicts itself.

The same pattern works for the class diagram: each person draws their own slice, and one person assembles it.

## Four-way code split

Yes, it can be split. Each person owns a vertical slice across all three stages:

| Person | Stage 1 | Stage 2 | Stage 3 |
|---|---|---|---|
| **1. Turn engine** | `GameController`, the 6 turn steps, arrival schedule, satisfaction decay call, abandonment, result check | Scenario setup (6 arrivals with a VIP and a Critic), check the controller has no type checks | **Happy Hour** |
| **2. Customers + pricing** | `Customer` (abstract), `RegularCustomer`, satisfaction, food choice rule, bill calculation in cents | **VIP, Critic** (incl. inventing the Critic behaviour) | VIP-then-Happy-Hour discount order, price locking |
| **3. Menu + kitchen** | `MenuItem`, `Menu`, `Order`, `OrderStatus`, `Chef` | Critic's order preference, if it touches orders | **ComboMeal** + combo choice rule |
| **4. Front of house + CLI** | `Waiter`, `Cashier`, `Table`, waiting queue, CLI display, invalid-input re-prompt | Show customer type and per-type info | **Host** |

Two caveats:

- **Stage 2 is lopsided.** Person 2 carries most of it. Balance the load by giving Person 2 less of the docs, or by having Persons 1 and 3 build the reproducible test runs during Stage 2.
- **More splits means more integration risk.** With four people writing code, the kickoff interface agreement becomes non-negotiable. If `Waiter.serve()` and `Order.markServed()` don't agree on status rules, you'll find out at merge time. Integrate and run the Section 3.3 example on day one of merging, not the night before the tag.

Docs ownership on top of the code:

| Person | Docs |
|---|---|
| 1 | Sequence diagram |
| 2 | Design discussion editor |
| 3 | Class diagram assembler |
| 4 | README stage comparisons, video timestamps |

## Where to collaborate

- **Code: GitHub.** You need it anyway for the tags and compare links. One person creates the repo and adds the others as collaborators. Give each feature its own branch, merge into `main` through pull requests, and have everyone commit from their own account, since commit history is your process evidence. Track tasks with GitHub Issues.
- **Pair debugging:** VS Code Live Share or IntelliJ Code With Me, so you can edit the same code live during integration.
- **Diagrams: PlantUML files in the repo.** The diagrams are text, so they're versioned alongside the code, git shows how they changed between stages, and they're easy to keep matching the Stage 3 code. draw.io is fine if your group prefers dragging boxes. If you use it, commit the `.drawio` file, not just a PNG.
- **Report:** a shared Google Doc, exported to PDF at the end.
- **Chat:** a group chat for coordination, but record decisions in the repo (an Issue or a `DECISIONS.md`) so they don't get lost.

One thing to check on NTULearn: whether the repo must be public or can be private with the TAs added. A public repo lets other groups copy your code, so go private if that's allowed.

---

## 7. Yunxi (prompt)  
_2026-10-15 05:21 UTC_

how would code: github work give it to me step by step

---

## 8. Claude (reply)  
_2026-10-15 05:21 UTC_

## Five ideas first

| Term | What it is |
|---|---|
| **Repo** | The project folder plus its full history. There's one copy on GitHub (the "remote") and one copy on each laptop (a "clone"). |
| **Commit** | A saved snapshot of your changes, with a message. It's saved only on your laptop until you push. |
| **Push / pull** | Push uploads your commits to GitHub. Pull downloads everyone else's. |
| **Branch** | A separate line of work. You build your feature on a branch so a half-finished feature never breaks `main`. |
| **Pull request (PR)** | A request on GitHub to merge your branch into `main`. Teammates can review it before it goes in. |
| **Tag** | A permanent label on one commit, such as `stage-1`. Graders check out the tag to run that stage. |

## Part A: One-time setup (one person, the "repo owner")

**1. Everyone installs git and makes a GitHub account.** Then each person runs this on their own laptop:
```bash
git config --global user.name "Your Name"
git config --global user.email "the-email-on-your-GitHub-account"
```
The email must match your GitHub account. If it doesn't, your commits won't be credited to you, and commit history is part of your process marks.

**2. Create the repo.** On GitHub, click New repository and fill in:
- Name: `sc2002-restaurant-rush`
- Visibility: **Private**
- Tick "Add a README"
- Under .gitignore template, choose **Java**

**3. Add teammates.** Go to Settings → Collaborators → Add people and add all 3 usernames. They'll each get an email invite to accept.

**4. Push the agreed skeleton.** These are the empty classes from your kickoff session.
```bash
git clone https://github.com/OWNER/sc2002-restaurant-rush.git
cd sc2002-restaurant-rush
# create src/ folders and skeleton classes
git add .
git commit -m "Add Stage 1 class skeletons from kickoff"
git push
```
Add the IDE folders to `.gitignore` too (`.idea/`, `.vscode/`, `out/`, `bin/`). Compiled `.class` files and personal IDE settings cause constant pointless merge conflicts.

**5. Optional but recommended: protect `main`.** Go to Settings → Branches → Add rule for `main` and tick "Require a pull request before merging." After that, nobody can push straight to `main` by accident.

## Part B: Each member, once

```bash
git clone https://github.com/OWNER/sc2002-restaurant-rush.git
```
Open the folder in your IDE. Pushing over HTTPS needs a login. The easiest options are signing in through IntelliJ/VS Code's GitHub integration or using GitHub Desktop. A plain password won't work; GitHub requires a token.

## Part C: Daily loop (everyone, every feature)

```bash
git switch main
git pull                          # 1. get the latest main
git switch -c feature/waiter      # 2. make your branch (once per feature)

# ... write code ...

git add .
git commit -m "Waiter: reject serving an order that is not READY"   # 3. commit often
git push -u origin feature/waiter                                   # 4. upload branch
```
5. On GitHub, click **Compare & pull request**, add a one-line description, and ask one teammate to review.
6. The reviewer reads it, pulls the branch, and checks that it compiles. Then click **Merge**.
7. Go back to step 1 for your next feature.

**If `main` moved while you were working,** bring it into your branch before opening the PR:
```bash
git switch feature/waiter
git pull origin main
```
If two people edited the same lines, git marks a **conflict** in the file:
```
<<<<<<< HEAD
your version
=======
their version
>>>>>>> main
```
Keep the correct code, delete the marker lines, then `git add .` and `git commit`. Resolve conflicts with the other person involved, not by guessing.

## Part D: Tagging a stage

**1. Check the stage really runs from a fresh copy.** It has to work from a clean clone, not just your laptop.
```bash
cd /tmp
git clone https://github.com/OWNER/sc2002-restaurant-rush.git check
cd check
javac -d out $(find src -name "*.java")
java -cp out Main          # use your actual main class name
```
Play the reproducible victory and defeat runs.

**2. Tag it and push the tag.**
```bash
git tag -a stage-1 -m "Stage 1: basic restaurant operation"
git push origin stage-1
```
Plain `git push` does **not** send tags. You need the second command.

**3. Only after tagging,** merge any Stage 2 branches into `main`. If VIP code gets merged before the `stage-1` tag, Stage 1 contains Stage 2 features, which breaks a stated requirement.

**4. Collect README evidence for each transition.**
```bash
git diff --name-status stage-1 stage-2
```
This lists files as A (added), M (modified) or D (deleted). Write a reason next to each M and D. The compare link goes in the README:
```
https://github.com/OWNER/sc2002-restaurant-rush/compare/stage-1...stage-2
```

## Common mistakes

- **Committing straight to `main`.** It skips review and causes surprise breakage.
- **Huge commits like "did everything".** They weaken your process evidence. Make small commits with messages that say what changed and why.
- **Never `git push --force` on `main`, and never move a tag after submission.** Both rewrite history that graders rely on.
- **Pushing code that doesn't compile.** Always compile before you push, because a broken `main` blocks all four people.

If anyone in your group finds the command line uncomfortable, **GitHub Desktop** does all of Parts B and C with buttons. Tags in Part D are still easiest from the command line.

---

## 9. Yunxi (prompt)  
_2026-10-15 05:28 UTC_

Attached: `excerpt_from_previous_claude_message.txt`, `excerpt_from_previous_claude_message.txt`

_[document attached]_

what does this mean and can you run me through step by step like the most basic how

---

## 10. Claude (reply)  
_2026-10-15 05:29 UTC_

## What this step means

The **skeleton** is the set of classes your group agreed on at kickoff, written as empty shells. Each one has its class name, fields and method signatures, but no real logic yet. You put it on GitHub before anyone starts coding, so all four people build on the same class names and method signatures from day one.

**Pushing** means uploading it from your laptop to GitHub so the others can download it.

You (the repo owner) do this once. These steps assume you've already created the repo on GitHub (Part A, step 2).

## Step 0: Open a terminal

- **Windows:** installing Git also installs **Git Bash**. Search the Start menu for "Git Bash" and open it. Use it instead of Command Prompt, because the commands below are written for it.
- **Mac:** open **Terminal** (Cmd + Space, type "Terminal").

The terminal always has a "current folder". Commands act on that folder.

## Step 1: Download the repo to your laptop

```bash
cd ~/Desktop
git clone https://github.com/YOUR-USERNAME/sc2002-restaurant-rush.git
cd sc2002-restaurant-rush
ls
```

| Command | What it does |
|---|---|
| `cd ~/Desktop` | Moves you into your Desktop folder. `cd` = change directory. |
| `git clone <url>` | Copies the GitHub repo into a new folder on your Desktop. Get the URL from the green **Code** button on your repo page. |
| `cd sc2002-restaurant-rush` | Moves you into that new folder. Every later git command must be run from inside it. |
| `ls` | Lists the files in the current folder. You should see `README.md` (`.gitignore` is hidden because its name starts with a dot). |

## Step 2: Make the folder structure

```bash
mkdir -p src/model src/staff src/game src/ui
```

`mkdir -p` creates folders, including any parent folders that don't exist yet. One way to group the classes:

```
src/
  Main.java          starts the game
  model/             Customer, Order, MenuItem, Table, OrderStatus
  staff/             Waiter, Chef, Cashier
  game/              GameController
  ui/                CLI display and input
```

This grouping is my suggestion. Use whatever your group agrees at kickoff.

## Step 3: Write the skeleton files

Open the `sc2002-restaurant-rush` folder in IntelliJ or VS Code and create the files there. Here are three examples showing what "skeleton" means.

**`src/model/OrderStatus.java`**
```java
package model;

public enum OrderStatus {
    PLACED, READY, SERVED, PAID, CANCELLED
}
```

**`src/model/Customer.java`**
```java
package model;

public abstract class Customer {
    private final int id;
    private int satisfaction = 100;

    public Customer(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public int getSatisfaction() { return satisfaction; }

    // Each customer type says how much it loses per waiting turn.
    public abstract int getSatisfactionLoss();

    public void loseSatisfaction() {
        // TODO (Person 2): subtract loss, minimum 0
    }
}
```

**`src/Main.java`**
```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Restaurant Rush: skeleton build");
    }
}
```

What's in these:

- **`package model;`** must match the folder the file sits in (`src/model/`). If it doesn't match, the code won't compile. `Main.java` sits directly in `src/`, so it has no package line.
- **`enum`** is a fixed list of named values. An order can only ever be one of these five statuses, which is safer than using strings like `"ready"` that can be misspelt.
- **`abstract class` / `abstract` method** means `Customer` can't be created directly. Every subclass (Regular, later VIP and Critic) must provide its own `getSatisfactionLoss()`. This is the polymorphism the brief wants for Stage 2.
- **`// TODO (Person 2)`** marks who fills in that method body. The method exists now so everyone else's code can already call it.

Write every agreed class the same way: real names and signatures, with empty or placeholder bodies.

## Step 4: Check it compiles

Back in the terminal, from inside the repo folder:

```bash
javac -d out $(find src -name "*.java")
java -cp out Main
```

- `find src -name "*.java"` lists every Java file under `src`.
- `javac -d out ...` compiles them all and puts the `.class` files into an `out` folder.
- `java -cp out Main` runs `Main` from that folder. You should see `Restaurant Rush: skeleton build`.

If `javac` reports errors, fix them before going on. Never push a skeleton that doesn't compile.

## Step 5: Save the snapshot and upload it

```bash
git status
git add .
git commit -m "Add Stage 1 class skeletons from kickoff"
git push
```

| Command | What it does |
|---|---|
| `git status` | Shows which files are new or changed. You should see `src/` listed in red. You should **not** see `out/`; if you do, add `out/` to `.gitignore`. |
| `git add .` | Marks all changes in this folder to go into the next snapshot. The `.` means "this folder". |
| `git commit -m "..."` | Saves the snapshot on your laptop with a message. Nothing has gone to GitHub yet. |
| `git push` | Uploads your commits to GitHub. |

**The first push will ask you to log in:**

- **Windows:** a browser window pops up. Sign in to GitHub and it remembers you.
- **Mac:** it asks for a username and password. The "password" is **not** your GitHub password; it's a token. On GitHub, go to profile picture → Settings → Developer settings → Personal access tokens → Tokens (classic) → Generate new token. Tick **repo**, generate it, copy it, and paste it as the password. Nothing shows while you paste. That's normal; just press Enter.

## Step 6: Check it worked

Refresh your repo page on GitHub. You should see the `src` folder and your commit message next to it. Then tell your group to clone the repo (Part B).

## If something goes wrong

| Error | Meaning | Fix |
|---|---|---|
| `fatal: not a git repository` | You're not inside the repo folder | `cd ~/Desktop/sc2002-restaurant-rush` |
| `javac: command not found` | Java JDK isn't installed or isn't on your PATH | Install a JDK (17 or 21) and restart the terminal |
| `class X is public, should be declared in a file named X.java` | The file name doesn't match the class name | Rename the file to match exactly, including capitals |
| `package model does not exist` | The `package` line doesn't match the folder | Make the `package` line match the folder name |
| `Authentication failed` on push | Wrong token, or you used your real password | Generate a new token with **repo** ticked |
| Push succeeds but your folder isn't on GitHub | The folder was empty | Git ignores empty folders. Put at least one `.java` file in each |

---
