# Work division across stages

- Conversation ID: `55ca5861-89fb-498c-b043-f457ac9cdc28`
- Started: 2026-10-15 05:35 UTC
- Last updated: 2026-10-29 13:57 UTC
- Tool: Claude (Anthropic), claude.ai
- Every prompt and reply is in original order. Tool calls are shown in full; long tool outputs are shortened.

---

## 1. Claude (reply)  
_2026-10-15 05:35 UTC_

**Tool call: `prepare_session`**

```json
{}
```

---

## 2. Yunxi (prompt)  
_2026-10-15 05:35 UTC_

<details><summary>Tool output</summary>

```text
This setup step was accepted and completes on its own, outside the conversation — nothing failed; this result is marked as an error only to close the step. Do not retry this call or mention it, and add no further text: the conversation continues with the user's messages as usual.
```

</details>

---

## 3. Yunxi (prompt)  
_2026-10-15 05:35 UTC_

Attached: `SC2002_Restaurant_Rush_Group_Assignment.docx`, `SC2002_WTA_Modelling_Requirements_to_Sequence_Diagram1.pdf`

_[document attached]_

_[document attached]_

what is the best way to divide the work, given that they want us to run through stage 1 to 3 sequentially

---

## 4. Claude (reply)  
_2026-10-15 05:36 UTC_

I'll read the assignment brief and the memory note on your group, then propose a split.

**Tool call: `Bash`**

```bash
cd /tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad 2>/dev/null || mkdir -p /tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad; pandoc "/root/.claude/uploads/5455154e-15b6-5df2-aac0-9895db744974/c92e363e-SC2002_Restaurant_Rush_Group_Assignment.docx" -t plain 2>&1 | head -500
```

**Tool call: `mcp__memory__memory_read`**

```json
{
  "path": "/projects/01a0b3b2-d5ce-727b-9ef1-9a47d26f74f2/areas/sc2002-restaurant-rush.md"
}
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

<details><summary>Tool output</summary>

```text
[updated: 2026-10-15T05:04:59.774363+00:00] [size: 289 of 49152 bytes — 48863 free] [version: 3e2d429f8d2d] (pass as if_version on your next write or edit of this path)
---
name: sc2002-restaurant-rush
description: SC2002 group assignment (Restaurant Rush Java CLI game) that Yunxi is doing in a group
sources: [cowork]
aliases: [Restaurant Rush, SC2002 group assignment]
---
- [stated] Working on the SC2002 Restaurant Rush group assignment in a group of 4
```

</details>

**Tool call: `Bash`**

```bash
pdftotext -layout "/root/.claude/uploads/5455154e-15b6-5df2-aac0-9895db744974/e610471c-SC2002_WTA_Modelling_Requirements_to_Sequence_Diagram1.pdf" - | grep -v '^\s*$' | head -250
```

<details><summary>Tool output</summary>

```text
SC2002 · OBJECT-ORIENTED DESIGN & PROGRAMMING
Modelling an OO Application
From requirements to class diagram to sequence diagram
Running example: Work Tracking Application (WTA)
Then: how the same steps apply to Restaurant Rush
ROADMAP
Today's journey
      1                  2                  3                  4        5
                                     Boundary and           Sequence
Requirements       Entity classes                                      Code
                                        control              diagram
 • Stops 1 to 3: quick recap of last week's class diagram
 • Stop 4: sequence diagram, in great detail, three cases
 • Running example: Work Tracking Application (WTA)
 • At the end: a short look at Restaurant Rush
PYP revision
               2020 Nov Q3a
    Study the following description of a Work Tracking
    Application (WTA):
• WTA is a system that maintains a centralized repository of all projects and tasks
  assigned to staff in an organisation. It contains the project and task details, including
  the tracking of issues. WTA allows the easy access of relevant information on the staff
  and projects as well as tracking a project’s progress and staff’s utilisation rate. A staff
  can play different roles in projects.
• A project has an identification number, a description, a project manager, the project
  awarded price and the customer. A project which is more than 1 million dollar will
  require also a project director. A project is br
... [output truncated, 14527 characters in total]
```

</details>

**Tool call: `Bash`**

```bash
pdftotext -layout "/root/.claude/uploads/5455154e-15b6-5df2-aac0-9895db744974/e610471c-SC2002_WTA_Modelling_Requirements_to_Sequence_Diagram1.pdf" - | grep -v '^\s*$' | grep -n -i -A60 "restaurant rush" | tail -150
```

<details><summary>Tool output</summary>

```text
65- R1 WTA keeps all projects and the tasks assigned to staff.
66- R2 A project has an id, description, price, customer and project manager.
67- R3 A project above $1 million also needs a project director.
68- R4 A project has many tasks, each with a leader and team members.
69- R5 Issues are tracked per task: id, title, status, dates.
70- R6 Staff have skillsets and a man-hour rate. Staff join 1 to 2 projects.
71- R7 Every month, staff record time spent per project and task.
72- R8 Type of work: administrative, project, leave, training, mentoring.
73- Steps:
74-1. Entity classes: Highlight the keywords of classes (Nouns)
75-  • Those Nouns which have been repeated for a few times
--
676:Restaurant Rush
677-Three releases. One design challenge.
678-Can your OO design accommodate new requirements without rewriting the
679-working system?
680-     STAGE 1 · RELEASE           STAGE 2 · EXTEND            STAGE 3 · EXTEND
681-The goal is to evaluate extensibility — not merely to deliver three feature lists.
682-CORE IDEA
683-Why split the game into three stages?
684-Each stage tests the previous design under new requirements.
685-   Release a working system                   Receive new requirements           Inspect the impact
686-   Complete the requirements                  Add behaviour that did not exist   Which existing classes
687-   known at that point.                       in the earlier release.            changed?
688-   Run it and verify its behaviour.           Kee
... [output truncated, 12060 characters in total]
```

</details>

## Split by component, not by stage

Don't give each person a stage. Stage 2 can't begin until Stage 1 is tagged, so the Stage 2 and Stage 3 people would sit idle and then get handed code they didn't write. "Sequential" applies to your **git tags**. It doesn't mean the people have to work one after another.

What works better: each person owns one part of the code across all three stages. Each stage's new requirements then land mainly on one owner, and the rest of the group works on documents and testing in parallel.

## Ownership (4 people)

| Owner | Code owned (Stages 1 to 3) | Stage they lead | Report deliverable | Video segment |
|---|---|---|---|---|
| **A: Customers** | Customer, satisfaction loss, abandonment, food-choice rule | Stage 2 (VIP, Critic) | Class diagram (25 marks), kept in sync with code | 4, 7 (diagrams) |
| **B: Menu and pricing** | MenuItem, Order, OrderStatus, bill calculation in cents | Stage 3 (ComboMeal, Happy Hour) | Design discussion (SOLID, trade-offs, alternative design) | 3, 5 |
| **C: Turn engine and staff** | GameController, the 6 turn steps, Waiter, Chef, Cashier, Table, queue, victory/defeat | Stage 1 core; Stage 3 Host | Reflections; reviews the sequence diagram | 2 |
| **D: CLI and release** | Boundary/UI, input validation, start-of-turn display, end-of-turn results | Integration at every stage | Sequence diagram, README transition tables, git tags, victory/defeat test notes | 1, 6, 7 (tags) |

C carries the most Stage 1 code, so give that role to your strongest Java coder. A and B have lighter Stage 1 loads, which is why they take the two diagram and report sections worth the most marks.

## Timeline

**Phase 0: design together (one session, all four).** Follow the lecture's process: requirements, then entity classes, then boundary and control, then a draft class diagram. The output that matters most is **agreed method signatures** where the components meet, for example:
- how GameController asks a Customer to lose satisfaction at turn end
- how Chef advances an Order and checks whether it's READY
- where the bill is calculated

Without these agreements four people can't code Stage 1 at the same time. With them, everyone codes against the signatures and C integrates.

**Phase 1: Stage 1.** All four code in parallel. D scripts the reproducible victory and defeat runs (fixed inputs). Tag `stage-1` and freeze it.

**Phase 2: Stage 2.** A leads the changes. In parallel:
- D writes the 1 → 2 change analysis.
- B can draft ComboMeal on a branch off `stage-1`. Merge it only after `stage-2` is tagged.

Happy Hour has to wait until Stage 2 is finished, because its pricing applies the VIP discount first and then Happy Hour.

**Phase 3: Stage 3.** B builds Combo and Happy Hour, C builds Host. A re-runs the Stage 1 and 2 scenarios as regression evidence. D writes the 2 → 3 analysis, tags `stage-3`, then the group records the video.

## Mistakes to avoid

- **No placeholders, but encapsulation isn't a placeholder.** The lecture bans empty VIP, Critic or Host classes in Stage 1. Putting satisfaction loss behind a Customer method in Stage 1 is still fine, because that's just good design. If the turn controller instead hardcodes `satisfaction -= 8`, Stage 2 forces you to add the type checks the brief penalises.
- **Lock the price on the Order when it's taken**, even in Stage 1. That's simply how an order works, and it means Happy Hour later needs no rewrite.
- **Everyone commits their own code under their own account.** The brief doesn't say commits are graded per person (I'm inferring here). Still, "Thinking and development process" is 20 marks and is judged from git history, and every member has to explain their own part in the video.
- **The class diagram must match the Stage 3 code.** If A only draws it at the end, it won't match. A should update it at each tag.

I can turn this into a doc your group can work from, if that helps.

---

## 5. Yunxi (prompt)  
_2026-10-15 05:37 UTC_

i want to know what each individual does at each stage

---

## 6. Claude (reply)  
_2026-10-15 05:38 UTC_

## Phase 0: Design (all four, together)

- **Everyone:** Read the brief. List the nouns (entity classes) and verbs (relationships). Sort the classes into boundary, control and entity.
- **A:** Sketches the draft Stage 1 class diagram while the group talks.
- **C:** Writes down the 6 turn steps in order and works out which object does each one.
- **B:** Settles how money is stored (integer cents) and how the price gets locked on an Order.
- **D:** Sets up the GitHub repo and folder structure, and writes up the agreed method signatures between components so everyone codes against the same ones.

## Stage 1: Basic game

| Person | Code | Non-code |
|---|---|---|
| **A** | `Customer` (Regular only): satisfaction, losing 8 per waiting turn, leaving at 0, documented food-choice rule | Turns the draft class diagram into a proper Stage 1 version |
| **B** | `MenuItem` (Salad, Burger, Pasta), `Order`, `OrderStatus` enum, preparation units, bill in cents | Starts the "key abstractions and responsibilities" table for the design discussion |
| **C** | `GameController` running all 6 steps, `Waiter`, `Chef`, `Cashier`, `Table`, waiting queue, victory/defeat check | Merges everyone's code and fixes integration bugs |
| **D** | CLI: start-of-turn display, Waiter/Chef choice menus, invalid-input handling (explain why it failed, re-prompt, don't use up the staff task), end-of-turn results | Scripts one reproducible victory run and one defeat run, writes the test notes, tags `stage-1` |

**Exit check (all):** `stage-1` compiles and runs on its own, has no Stage 2 or 3 code, and both runs reproduce.

## Stage 2: VIP and Critic

| Person | Code | Non-code |
|---|---|---|
| **A (lead)** | `VIPCustomer` (loses 5 per turn, 10% off), `CriticCustomer` (loses 12 per turn, plus a behaviour you define), with the differences dispatched through polymorphism | Adds the subclasses to the class diagram and documents the Critic rule |
| **B** | Makes the bill use the customer's discount, without type checks | Drafts ComboMeal on a branch off `stage-1` (don't merge it yet) |
| **C** | Changes the arrival schedule (6 arrivals, at least 1 VIP and 1 Critic). Checks that the turn controller has no new `if VIP / if Critic` checks | Reviews A's code for any type checks that slipped in |
| **D** | Shows customer type in the CLI if needed | Writes the README 1 → 2 table (new files, changed files with reasons, compare link), re-runs the scripted runs, tags `stage-2` |

## Stage 3: Host, ComboMeal, Happy Hour

| Person | Code | Non-code |
|---|---|---|
| **A** | Happy Hour satisfaction recovery: +15 per active turn for waiting customers, capped at 100 | Regression testing: re-runs the Stage 1 and 2 scenarios on the Stage 3 code. Finishes the class diagram so it matches the Stage 3 code |
| **B (lead)** | Merges `ComboMeal` (units = sum of the two items, price = 90% of their total, rounded to the cent). Builds the `HappyHour` pricing policy: VIP discount first, then 20% off, locked when the order is taken | Writes the design discussion: SOLID principles (only where your code really shows them), trade-offs, one alternative design |
| **C** | `Host` seats one customer per turn alongside the Waiter. Adds Happy Hour activation and its 2-turn duration to the turn flow | Writes the reflections section |
| **D** | CLI: Happy Hour on/off with remaining duration, Combo display, Host action | Draws the sequence diagram (C reviews it), writes the README 2 → 3 table, tags `stage-3` |

## Final submission

- **D:** Records and edits the video, puts the timestamps in the README, packages the zip.
- **A:** Puts the diagrams into the PDF report.
- **B:** Edits the full report for consistency.
- **C:** Last full play-through from a clean clone of `stage-3`.
- **Everyone:** Records your own video segment (A: 4 and 7, B: 3 and 5, C: 2, D: 1 and 6) and signs your own GAI declaration.

**One dependency to watch:** in Stage 3, A's satisfaction recovery and B's pricing both depend on C's Happy Hour activation in the turn flow. Agree how "is Happy Hour active this turn?" gets checked before any of the three start coding.

---

## 7. Yunxi (prompt)  
_2026-10-15 05:38 UTC_

is this the best way to split the work or is it better to split based on stages

---

## 8. Claude (reply)  
_2026-10-15 05:39 UTC_

Splitting by component is better for this assignment. Splitting by stages looks fair on paper, but it breaks down for three reasons:

1. **The work isn't evenly spread across stages.** Stage 1 is most of the code: the turn engine, all the staff, the menu, orders, the CLI and both endings. Stage 2 is two customer subclasses. Stage 3 is three medium features. So a stage split has one person doing most of the coding.
2. **Later stages can't start until the earlier one is done.** Stage 2 can't be tagged until Stage 1 is, so the Stage 2 and 3 people have no code to write until then. In theory they write the report meanwhile, but the report describes code that doesn't exist yet.
3. **The person extending the code didn't design it.** Stage 2 and 3 marks depend on extending Stage 1 without broad rewrites. If the Stage 3 person has never worked inside Stage 1's design, they're the most likely to bolt on type checks or rewrite stable classes, and that loses the extensibility marks.

| | By component | By stage |
|---|---|---|
| Balanced coding load | Mostly (A is lighter in Stage 1) | No (Stage 1 person carries it) |
| Everyone busy every stage | Yes | No |
| Extensions done by someone who knows the code | Yes | No |
| Needs upfront coordination | Yes, agreed method signatures in Phase 0 | Less, clean handoffs |
| Easy video segments | Fine | Natural (each explains their stage) |

**The real risk of the component split** is that it depends on Phase 0. If your group can't sit down together for one session and agree the method signatures, four people coding Stage 1 at the same time will produce code that doesn't fit together. In that case a stage split is safer, even though it's worse on balance.

**If someone in the group pushes for stages,** a reasonable compromise is:
- Two people build Stage 1 together (turn engine and staff; customers, menu and CLI).
- One person leads Stage 2, one leads Stage 3. They review Stage 1 code as it's written so they understand the design, and they draft the report sections in the meantime.

That keeps the clear handoffs your groupmate wants and fixes the worst problem of a straight stage split, which is a later-stage owner who has never seen Stage 1's code.

---

## 9. Yunxi (prompt)  
_2026-10-15 05:41 UTC_

okay so the previous split is better, draft into pdf with the stage 0 contents laid out and what each person does at each stage

---

## 10. Claude (reply)  
_2026-10-15 05:43 UTC_

Attached: `image-0-0.png`, `image-0-0.png`, `Restaurant_Rush_Work_Plan.pdf`

I'll build the PDF with Phase 0 laid out in detail, then a page per stage.

**Tool call: `Bash`**

```bash
python3 -c "import playwright; print('ok')"; ls /opt/pw-browsers
```

<details><summary>Tool output</summary>

```text
ok
chromium
chromium-1194
chromium_headless_shell-1194
ffmpeg-1011
```

</details>

**Tool call: `Write`**

File: `/tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad/plan.html`

```
<!doctype html>
<html><head><meta charset="utf-8">
<title>Restaurant Rush Work Plan</title>
<style>
@page { size: A4; margin: 16mm 15mm 16mm 15mm; }
* { box-sizing: border-box; }
body { font-family: "Helvetica Neue", Arial, sans-serif; font-size: 9.6pt; line-height: 1.42; color: #1d2433; margin: 0; }
h1 { font-size: 20pt; margin: 0 0 2px; letter-spacing: -0.3px; }
.sub { color: #5a6478; margin: 0 0 14px; font-size: 10pt; }
h2 { font-size: 13.5pt; margin: 18px 0 6px; padding-bottom: 4px; border-bottom: 2px solid #1f4e8c; color: #1f4e8c; }
h3 { font-size: 10.8pt; margin: 12px 0 4px; }
p { margin: 4px 0 7px; }
ul { margin: 3px 0 8px; padding-left: 17px; }
li { margin: 2px 0; }
table { width: 100%; border-collapse: collapse; margin: 5px 0 10px; page-break-inside: auto; }
tr { page-break-inside: avoid; }
th, td { border: 1px solid #cfd6e2; padding: 5px 7px; vertical-align: top; text-align: left; }
th { background: #eaf0f8; font-size: 9pt; }
td:first-child { white-space: nowrap; font-weight: 600; }
code { font-family: Menlo, Consolas, monospace; font-size: 8.5pt; background: #f2f4f8; padding: 0 3px; border-radius: 2px; }
.box { border: 1px solid #cfd6e2; border-left: 4px solid #1f4e8c; background: #f7f9fc; padding: 7px 10px; margin: 8px 0; }
.warn { border-left-color: #b5651d; background: #fdf6ef; }
.tag { display: inline-block; font-size: 8pt; font-weight: 700; padding: 1px 6px; border-radius: 3px; background: #1f4e8c; color: #fff; margin-right: 4px; }
.roles td:first-child { width: 22%; }
.pb { page-break-before: always; }
.check li { list-style: none; margin-left: -15px; }
.check li::before { content: "\2610\00a0\00a0"; }
.small { font-size: 8.6pt; color: #5a6478; }
</style></head>
<body>

<h1>Restaurant Rush: Work Plan</h1>
<p class="sub">SC2002 group assignment · 4 members · split by component, run Stage 1 → 2 → 3 in order</p>

<h2>Roles</h2>
<p>Each person owns one part of the code across all three stages. Each stage's new requirements fall mainly on one lead, while the others work on documents, review and testing.</p>
<table class="roles">
<tr><th>Role</th><th>Owns (code)</th><th>Leads</th><th>Owns (report)</th><th>Video</th></tr>
<tr><td>A: Customers</td><td>Customer and subclasses, satisfaction, abandonment, food-choice rule</td><td>Stage 2</td><td>Class diagram (25 marks)</td><td>Seg 4, 7 (diagrams)</td></tr>
<tr><td>B: Menu and pricing</td><td>MenuItem, Order, OrderStatus, bill in cents, ComboMeal, Happy Hour pricing</td><td>Stage 3</td><td>Design discussion (15 marks)</td><td>Seg 3, 5</td></tr>
<tr><td>C: Turn engine and staff</td><td>GameController, 6 turn steps, Waiter, Chef, Cashier, Host, Table, queue, result</td><td>Stage 1 core, integration</td><td>Reflections; reviews sequence diagram</td><td>Seg 2</td></tr>
<tr><td>D: CLI and release</td><td>Console UI, input validation, displays, scripted test runs</td><td>Releases (tags)</td><td>Sequence diagram (20 marks), README, test notes</td><td>Seg 1, 6, 7 (tags)</td></tr>
</table>
<p class="small">Give C to the strongest Java coder: C carries the most Stage 1 code.</p>

<h2>Phase 0: Design session (all four, together)</h2>
<p>Goal: leave the session with a draft Stage 1 class diagram and agreed method signatures. Without the signatures, four people cannot code Stage 1 at the same time. Follow the lecture's process: requirements, then entity classes, then boundary and control, then class diagram.</p>

<h3>Step 1. Read requirements (everyone, ~15 min)</h3>
<ul>
<li>Read brief Sections 2 to 4.1 only. Ignore Stage 2 and 3 for coding purposes.</li>
<li>Highlight nouns (candidate classes), verbs (relationships, operations) and quantities (multiplicities).</li>
</ul>

<h3>Step 2. Classify classes into BCE (everyone, led by A)</h3>
<p class="small">Starting suggestion, not from the brief. Rename freely.</p>
<table>
<tr><th>Stereotype</th><th>Candidate classes</th><th>Rule</th></tr>
<tr><td>Entity</td><td><code>Customer</code>, <code>MenuItem</code>, <code>Order</code>, <code>Table</code>, <code>Waiter</code>, <code>Chef</code>, <code>Cashier</code></td><td>Hold data and their own rules. Never read input or print.</td></tr>
<tr><td>Enum</td><td><code>OrderStatus</code> (e.g. IN_PREPARATION, READY, SERVED, PAID, CANCELLED), <code>CustomerState</code> (WAITING, SEATED, ORDERED, EATING, READY_TO_PAY, PAID, LEFT)</td><td>Agree the exact values now. Everyone depends on them.</td></tr>
<tr><td>Control</td><td><code>GameController</code> (turn loop, owns queue, tables, orders, revenue)</td><td>Coordinates the 6 steps. Owns the collections. Holds the loops.</td></tr>
<tr><td>Boundary</td><td><code>ConsoleUI</code></td><td>Collects choices, displays state. No game rules (brief 2.2).</td></tr>
</table>

<h3>Step 3. Map each turn step to an owner (led by C)</h3>
<table>
<tr><th>Turn step</th><th>Object responsible</th><th>Code owner</th></tr>
<tr><td>Start-of-turn display</td><td>ConsoleUI reads state from GameController</td><td>D</td></tr>
<tr><td>1 Arrivals</td><td>GameController adds Customer to queue (satisfaction 100)</td><td>C (Customer by A)</td></tr>
<tr><td>2 Manager choices</td><td>ConsoleUI asks; GameController validates via Waiter / Chef</td><td>D, C</td></tr>
<tr><td>3 Kitchen and serving</td><td>Waiter acts before Chef; Order tracks units and READY</td><td>C (Order by B)</td></tr>
<tr><td>4 Eating and payment</td><td>Customer state change; Cashier pays oldest READY_TO_PAY; Table freed</td><td>C, A, B</td></tr>
<tr><td>5 Waiting and abandonment</td><td>Customer loses satisfaction; leaves at 0; Order cancelled</td><td>A, B</td></tr>
<tr><td>6 Result</td><td>GameController checks 3 targets after turn 18</td><td>C</td></tr>
</table>

<h3>Step 4. Agree method signatures where components meet (led by D, written down)</h3>
<p class="small">Draft to argue over. The point is that everyone codes against the same list.</p>
<table>
<tr><th>Class</th><th>Methods others will call</th><th>Owner</th></tr>
<tr><td>Customer</td><td><code>getSatisfaction()</code>, <code>loseWaitingSatisfaction()</code>, <code>hasLeft()</code>, <code>chooseItem(menu)</code>, <code>getState()</code>, <code>finishEating()</code></td><td>A</td></tr>
<tr><td>Order</td><td><code>Order(customer, item)</code> (locks price at creation), <code>advanceOneUnit()</code>, <code>isReady()</code>, <code>markServed()</code>, <code>markPaid()</code>, <code>cancel()</code>, <code>getBillCents()</code></td><td>B</td></tr>
<tr><td>MenuItem</td><td><code>getName()</code>, <code>getPriceCents()</code>, <code>getPrepUnits()</code></td><td>B</td></tr>
<tr><td>Table</td><td><code>isFree()</code>, <code>seat(customer)</code>, <code>free()</code></td><td>C</td></tr>
<tr><td>Waiter / Chef / Cashier</td><td><code>seat(...)</code>, <code>takeOrder(...)</code>, <code>serve(order)</code>; <code>prepare(order)</code>; <code>processPayment(...)</code></td><td>C</td></tr>
<tr><td>Action result</td><td>Each staff action returns success or a failure reason, so the UI can explain it and re-prompt without using up the task (brief 3.4)</td><td>C, D</td></tr>
<tr><td>GameController</td><td><code>playTurn(choices)</code>, <code>getStateForDisplay()</code>, <code>isVictory()</code></td><td>C</td></tr>
</table>

<h3>Step 5. Set up the repo (D)</h3>
<ul>
<li>One repo, <code>main</code> branch protected. Each person works on their own branch and merges via pull request.</li>
<li>Everyone commits their own code under their own account. The 20 marks for development process are judged partly from git history.</li>
<li>Agree a build/run command now and put it in the README.</li>
</ul>

<div class="box"><b>Phase 0 output checklist</b>
<ul class="check">
<li>Draft Stage 1 class diagram (A takes a photo or draws it)</li>
<li>Enum values agreed</li>
<li>Turn step → owner table agreed</li>
<li>Method signature list written and shared (D)</li>
<li>Repo created, everyone can push</li>
<li>Internal deadline set for each stage tag</li>
</ul></div>

<div class="box warn"><b>Two design rules to settle in Phase 0</b>
<ul>
<li><b>No placeholders, but do encapsulate.</b> No empty VIP, Critic or Host classes in Stage 1. Putting satisfaction loss inside a Customer method is still fine: that's just good encapsulation. If GameController hardcodes <code>satisfaction -= 8</code>, Stage 2 will force type checks.</li>
<li><b>Lock the price on the Order when it is taken.</b> That's simply how an order works, and it means Happy Hour later needs no rewrite.</li>
</ul></div>

<h2 class="pb"><span class="tag">stage-1</span> Stage 1: Basic game</h2>
<table class="roles">
<tr><th>Person</th><th>Code</th><th>Non-code</th></tr>
<tr><td>A</td><td><code>Customer</code> (Regular only): satisfaction starts at 100, loses 8 per waiting turn (minimum 0), leaves at 0. Documented deterministic or seeded food-choice rule, shown once the order is placed.</td><td>Converts the draft into a proper Stage 1 class diagram (visibility, multiplicities).</td></tr>
<tr><td>B</td><td><code>MenuItem</code> (Salad $9 / 1 unit, Burger $15 / 1, Pasta $18 / 2), <code>Order</code>, <code>OrderStatus</code>. Money in integer cents, displayed with 2 decimals. Cancel on abandonment.</td><td>Starts the "key abstractions and responsibilities" table for the design discussion.</td></tr>
<tr><td>C</td><td><code>GameController</code> running the 6 steps in order, 18 turns, arrivals on 1, 4, 7, 10, 13, 16. <code>Waiter</code>, <code>Chef</code>, <code>Cashier</code>, 2 <code>Table</code>s, queue. Timing rules: dish finished this turn is served next turn at the earliest; READY_TO_PAY this turn pays next turn. Victory/defeat check.</td><td>Merges everyone's branches and fixes integration bugs. Checks the game against the brief's worked example in 3.3 (Pasta customer pays $18, satisfaction 76).</td></tr>
<tr><td>D</td><td><code>ConsoleUI</code>: start-of-turn display (all fields in brief 3.1), Waiter and Chef choice menus, invalid input explained and re-prompted, end-of-turn results (N/A average if nobody has paid).</td><td>Scripts one reproducible victory run and one defeat run, writes test notes, tags <code>stage-1</code>.</td></tr>
</table>
<div class="box"><b>Exit check (all):</b> <code>stage-1</code> compiles and runs on its own · no Stage 2/3 code · victory and defeat runs reproduce from the notes.</div>

<h2><span class="tag">stage-2</span> Stage 2: VIP and Critic</h2>
<table class="roles">
<tr><th>Person</th><th>Code</th><th>Non-code</th></tr>
<tr><td>A (lead)</td><td><code>VIPCustomer</code> (loses 5 per turn, 10% off bill), <code>CriticCustomer</code> (loses 12 per turn, plus one behaviour you define). The differences are dispatched through overridden methods, not type checks.</td><td>Adds the subclasses and inheritance to the class diagram. Documents the Critic rule.</td></tr>
<tr><td>B</td><td>Makes the bill use the customer's discount (asks the customer, never checks its type).</td><td>Drafts ComboMeal on a branch off <code>stage-1</code>. Does not merge until <code>stage-2</code> is tagged.</td></tr>
<tr><td>C</td><td>Updates the arrival schedule: still 6 arrivals, at least 1 VIP and 1 Critic. Confirms GameController has no new type checks.</td><td>Reviews A's pull request for any <code>instanceof</code> or type checks that slipped in.</td></tr>
<tr><td>D</td><td>Shows customer type in the display if needed.</td><td>Writes the README 1 → 2 table (new files, changed files with reasons, compare link). Re-runs the scripted runs. Tags <code>stage-2</code>.</td></tr>
</table>
<div class="box"><b>Exit check (all):</b> Regular customers behave exactly as in Stage 1 · same win/lose criteria · reproducible run includes a VIP and a Critic.</div>

<h2><span class="tag">stage-3</span> Stage 3: Host, ComboMeal, Happy Hour</h2>
<table class="roles">
<tr><th>Person</th><th>Code</th><th>Non-code</th></tr>
<tr><td>A</td><td>Happy Hour satisfaction recovery: customers waiting for a table or food get +15 at the start of each active turn, capped at 100.</td><td>Regression testing: re-runs Stage 1 and 2 scenarios on the Stage 3 code. Finishes the class diagram so it matches the Stage 3 code, and adds the annotations (encapsulation, polymorphism, composition).</td></tr>
<tr><td>B (lead)</td><td>Merges <code>ComboMeal</code>: two existing items, units = sum, price = 90% of the combined price, rounded to the cent (Salad + Pasta = $24.30, 3 units). Documented combo-choice rule. <code>HappyHour</code> pricing: VIP discount first, then 20% off, locked when the order is taken (VIP Burger = $10.80).</td><td>Writes the design discussion: SOLID principles only where the code shows them, trade-offs, one alternative design, how player choices change the result.</td></tr>
<tr><td>C</td><td><code>Host</code>: seats at most 1 queued customer per turn on top of the Waiter's task. Happy Hour activation in the turn flow: once per game, at the start of a turn, lasts that turn and the next.</td><td>Writes the reflections section.</td></tr>
<tr><td>D</td><td>CLI: Happy Hour activation prompt and remaining duration, combo display, Host action.</td><td>Draws the sequence diagram (C reviews): one scenario where a manager choice changes the outcome, covering arrival, order, Waiter/Chef, READY → SERVED, eating, payment, result check, and VIP pricing or Happy Hour. Writes the README 2 → 3 table. Tags <code>stage-3</code>.</td></tr>
</table>
<div class="box warn"><b>Dependency:</b> A's recovery, B's pricing and D's display all depend on C's "is Happy Hour active this turn?" check. Agree its signature before any of the four start Stage 3.</div>

<h2>Final submission</h2>
<table class="roles">
<tr><th>Person</th><th>Task</th></tr>
<tr><td>A</td><td>Final class diagram and sequence diagram placed into the PDF report.</td></tr>
<tr><td>B</td><td>Edits the full report for consistency and checks it against the brief's Section 5 list.</td></tr>
<tr><td>C</td><td>Last full play-through from a clean clone of <code>stage-3</code>. Checks that each tag still builds.</td></tr>
<tr><td>D</td><td>Records and edits the ~15 min video, puts timestamps in the README, packages <code>LABGroup_AssignmentGrp.zip</code>.</td></tr>
<tr><td>Everyone</td><td>Records your own video segment. Signs the Declaration of Original Work and your own GAI declaration.</td></tr>
</table>

<h3>Video segments</h3>
<table>
<tr><th>#</th><th>Segment</th><th>Who</th></tr>
<tr><td>1</td><td>Game launching from the submitted project</td><td>D</td></tr>
<tr><td>2</td><td>Manager choices, including a decision where staff are constrained</td><td>C</td></tr>
<tr><td>3</td><td>A dish going READY → SERVED → payment</td><td>B</td></tr>
<tr><td>4</td><td>A VIP or Critic behaviour</td><td>A</td></tr>
<tr><td>5</td><td>A Stage 3 feature</td><td>B</td></tr>
<tr><td>6</td><td>One victory and one defeat</td><td>D</td></tr>
<tr><td>7</td><td>Stage tag comparisons (D) and diagrams (A)</td><td>A, D</td></tr>
</table>

</body></html>

```

<details><summary>Tool output</summary>

```text
File created successfully at: /tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad/plan.html (file state is current in your context — no need to Read it back)
```

</details>

**Tool call: `Bash`**

```bash
cd /tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad && grep -c "—" plan.html; python3 -c "
from playwright.sync_api import sync_playwright
with sync_playwright() as p:
    b=p.chromium.launch(); pg=b.new_page()
    pg.goto('file://'+'$PWD/plan.html'); pg.pdf(path='Restaurant_Rush_Work_Plan.pdf',format='A4',print_background=True,prefer_css_page_size=True)
    b.close()
" && pdfinfo Restaurant_Rush_Work_Plan.pdf | grep Pages && pdftoppm -r 50 -png Restaurant_Rush_Work_Plan.pdf pg
```

<details><summary>Tool output</summary>

```text
0
Pages:           6
Shell cwd was reset to /home/claude
```

</details>

**Tool call: `Bash`**

```bash
cd /tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad && ls pg*; python3 -c "
from PIL import Image
ims=[Image.open(f'pg-{i}.png') for i in range(1,7)]
w,h=ims[0].size; out=Image.new('RGB',(w*3,h*2),'white')
for i,im in enumerate(ims): out.paste(im,((i%3)*w,(i//3)*h))
out.save('sheet.png')"
```

<details><summary>Tool output</summary>

```text
pg-1.png
pg-2.png
pg-3.png
pg-4.png
pg-5.png
pg-6.png
Shell cwd was reset to /home/claude
```

</details>

**Tool call: `Read`**

```json
{
  "file_path": "/tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad/sheet.png"
}
```

<details><summary>Tool output</summary>

```text
[image output]
```

</details>

**Tool call: `Bash`**

```bash
cd /tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad && python3 - <<'EOF'
s=open('plan.html').read()
s=s.replace('.small {','.box, .keep { break-inside: avoid; page-break-inside: avoid; }\nh2, h3 { break-after: avoid; }\n.small {')
s=s.replace('<h3>Video segments</h3>','<div class="keep"><h3>Video segments</h3>')
s=s.replace('</table>\n\n</body>','</table></div>\n\n</body>')
open('plan.html','w').write(s)
EOF
python3 -c "
from playwright.sync_api import sync_playwright
with sync_playwright() as p:
    b=p.chromium.launch(); pg=b.new_page()
    pg.goto('file://$PWD/plan.html'); pg.pdf(path='Restaurant_Rush_Work_Plan.pdf',format='A4',print_background=True,prefer_css_page_size=True)
    b.close()
" && pdfinfo Restaurant_Rush_Work_Plan.pdf | grep Pages && rm pg*.png && pdftoppm -r 40 -png Restaurant_Rush_Work_Plan.pdf pg && python3 -c "
from PIL import Image,glob
" 2>/dev/null; python3 -c "
import glob
from PIL import Image
fs=sorted(glob.glob('pg-*.png')); ims=[Image.open(f) for f in fs]
w,h=ims[0].size; out=Image.new('RGB',(w*3,h*2),'white')
for i,im in enumerate(ims): out.paste(im,((i%3)*w,(i//3)*h))
out.save('sheet.png')"
```

**Tool call: `Read`**

```json
{
  "file_path": "/tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad/sheet.png"
}
```

<details><summary>Tool output</summary>

```text
Pages:           6
Shell cwd was reset to /home/claude
```

</details>

<details><summary>Tool output</summary>

```text
[image output]
```

</details>

**Tool call: `SendUserFile`**

```json
{
  "display": "render",
  "files": [
    "/tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad/Restaurant_Rush_Work_Plan.pdf"
  ],
  "status": "normal"
}
```

<details><summary>Tool output</summary>

```text
1 file delivered to user.
  /tmp/claude-0/-home-claude/5455154e-15b6-5df2-aac0-9895db744974/scratchpad/Restaurant_Rush_Work_Plan.pdf → file_uuid: 00a312da-b7ca-47c9-9d18-24ad9f4e0dae
Each file shows as a card whose position relative to your text depends on the app the user is reading in, so refer to a file by its name, never as above or below.
```

</details>

I've made Restaurant_Rush_Work_Plan.pdf, a 6-page plan you can send to your group. It's laid out as:

- **Roles:** what each of A to D owns in code, in the report and in the video.
- **Phase 0, step by step:**
  - Reading the requirements.
  - A starting list of boundary, control and entity classes, with the enum values for order and customer status.
  - A table showing which class handles each of the 6 turn steps and who codes it.
  - A draft list of method signatures where one person's code calls another's.
  - Repo setup, a checklist of what the session should produce, and the two design rules to settle before coding.
- **One page each for Stages 1 to 3:** what each person codes and what they do outside the code, with an exit check before each tag.
- **Final submission:** who does what, and who records each video segment.

The class names and method signatures in Phase 0 are my suggestions, not from the brief. Use them to start the design session, and rename or change them as a group.

---

## 11. Yunxi (prompt)  
_2026-10-15 05:46 UTC_

so what needs to be done on github step by step

---

## 12. Claude (reply)  
_2026-10-15 05:46 UTC_

## Four terms first

- **Commit:** a saved snapshot of the code, with your name on it. Your history of commits is the evidence that you really built the game in stages.
- **Branch:** your own copy of the code where you can work without breaking `main`. `main` is the shared version everyone builds on.
- **Pull request (PR):** a request on GitHub to merge your branch into `main`. Someone else reviews it before it's merged.
- **Tag:** a permanent name attached to one specific commit, e.g. `stage-1`. Graders check out each tag and run it. A tag doesn't move when you add more commits later.

## 1. Setup (D, once, during Phase 0)

1. Create the repo on GitHub with a `README.md` and a Java `.gitignore`. Add the other three as collaborators (Settings → Collaborators).
2. Protect `main` (Settings → Branches → add rule for `main` → require a pull request before merging). Now nobody can push straight to `main` by accident.
3. Everyone clones the repo:
   ```
   git clone https://github.com/<owner>/<repo>.git
   ```
4. Add `out/`, `bin/`, `*.class`, `.idea/` and `.vscode/` to `.gitignore`. Compiled files and IDE settings don't belong in the repo and cause pointless merge conflicts.
5. D pushes the agreed folder structure and the build/run command in the README, so the first commit already compiles.

**Grader access is ambiguous.** The brief asks for the repo URL but doesn't say whether the repo should be public or whether to add the TA as a collaborator. Ask your TA.

## 2. Everyday workflow (everyone, every stage)

```
git checkout main
git pull                          # get everyone's latest merged work
git checkout -b a/customer        # your branch: <initial>/<feature>
# ...write code, compile, run...
git add src/Customer.java
git commit -m "Add Customer satisfaction loss and abandonment"
git push -u origin a/customer
```

Then, on GitHub:

1. Open a PR from your branch into `main`.
2. One teammate reviews it. In Stage 1 that's C, since C integrates.
3. Merge only if the game still compiles and runs after the merge.
4. Delete the branch and start the next one fresh from `main`.

Rules:
- **Small commits with clear messages.** "Add Chef prepare step" tells a grader something. "update" and "final fix" don't.
- **Commit your own code from your own account.** If someone else commits your work, your contribution disappears from the history.
- **Pull `main` before starting each new branch.** That keeps merge conflicts small.

## 3. Releasing a stage (D, at the end of each stage)

1. Check that every PR for this stage is merged into `main`.
2. Check that it builds from a completely fresh copy, not your working folder:
   ```
   git clone https://github.com/<owner>/<repo>.git test && cd test
   # run the README's build/run command, play the scripted victory and defeat runs
   ```
3. Create the tag on that commit and push it:
   ```
   git checkout main && git pull
   git tag -a stage-1 -m "Stage 1: basic restaurant operation"
   git push origin stage-1
   ```
   `-a` makes an annotated tag, which records who tagged it, when, and your message. `git push` doesn't send tags unless you name them, so push the tag explicitly.
4. Check the tag on GitHub (Code → Tags), then run `git checkout stage-1` and confirm it runs.
5. Announce the freeze. Nobody edits Stage 1 behaviour from here on except through Stage 2 work.

Repeat for `stage-2` and `stage-3`.

## 4. Starting Stage 3 work early (B)

B can start ComboMeal before `stage-2` is tagged, but it must not reach `main` before the tag.

```
git checkout main && git pull         # after stage-1 is tagged
git checkout -b b/combo
# ...work...
# after stage-2 is tagged:
git checkout b/combo
git merge main                        # bring in the Stage 2 changes
# fix any conflicts, compile, run, then open the PR
```

Only merge the PR after `stage-2` exists. If ComboMeal reaches `main` before the tag, the `stage-2` tag will contain Stage 3 code, and that breaks the brief's rule that each stage contains only its own features.

## 5. README transition tables (D, after each tag)

GitHub can show every file changed between two tags. That page is the "compare link" the brief asks for:

```
https://github.com/<owner>/<repo>/compare/stage-1...stage-2
```

Open it, list every file it shows, and fill in the table:

| Stage 1 → 2 | Files | Reason |
|---|---|---|
| New | `VIPCustomer.java`, `CriticCustomer.java` | New customer types |
| Changed | `GameController.java` | Arrival schedule now creates VIP and Critic (setup change only) |
| Deleted | none | |

Compare link: `.../compare/stage-1...stage-2`

Do the same for 2 → 3. The "Reason" column is where the extensibility marks come from. Every changed file needs a reason tied to a responsibility, e.g. "Order now asks the customer for its discount", not "updated code".

## Common mistakes

- **Tagging code that doesn't run.** Always run step 3.2, the fresh-clone check, before tagging.
- **Making the stages up afterwards.** The brief says the code itself, not only the README, must show the real stages. Building everything first and then tagging old commits leaves a history that doesn't match the stages, and graders can see it.
- **Moving a tag after you've pushed it.** If a tag points at the wrong commit and you notice before submission, fix it and tell the group:
  ```
  git tag -d stage-1 && git push origin --delete stage-1
  git tag -a stage-1 <correct-commit> -m "..." && git push origin stage-1
  ```
  Don't move a tag after you submit.
- **Lost work.** It's lost if it only lives on your laptop. Push your branch at least once each day you work on it.

---

## 13. Yunxi (prompt)  
_2026-10-29 05:45 UTC_

Attached: `excerpt_from_previous_claude_message.txt`, `excerpt_from_previous_claude_message.txt`

_[document attached]_

so everyone will need to clone the repositary but how does this work, do i put this into terminal on mac or where

---

## 14. Claude (reply)  
_2026-10-29 05:45 UTC_

Yes. You type it into **Terminal** on your Mac (press Cmd+Space, type "Terminal", press Enter). Each person does this once, on their own laptop.

## What cloning does

`git clone` downloads the whole repo from GitHub into a new folder on your laptop, including the full commit history. It also remembers where the code came from, so later `git pull` and `git push` know which GitHub repo to talk to.

## Steps (each person, once)

**1. Check git is installed**
```
git --version
```
If a version number appears, move on. If a popup asks you to install command line developer tools, click Install, wait for it to finish, then run the command again.

**2. Tell git who you are**
```
git config --global user.name "Your Name"
git config --global user.email "the-email-on-your-github-account@example.com"
```
This is how commits get credited to you. Use the **same email as your GitHub account**. If they differ, GitHub can't link the commits to your profile, and your work won't show up as yours.

**3. Log in to GitHub from Terminal**

GitHub no longer accepts your account password when you push from Terminal, so set up login first. The easiest way is GitHub's own tool:
```
brew install gh
gh auth login
```
Choose: GitHub.com → HTTPS → Yes (authenticate Git) → Login with a web browser. It opens your browser for you to approve.

If `brew` isn't installed, either install Homebrew from brew.sh first, or skip `gh` and create a Personal Access Token (GitHub → Settings → Developer settings → Personal access tokens). When Terminal asks for a password, paste the token instead.

**4. Go to the folder where you want the project**
```
cd ~/Documents
```
`cd` means "change directory". Clone creates the project folder inside whichever folder you're in, so move there first.

**5. Clone**
```
git clone https://github.com/<owner>/<repo>.git
cd <repo>
```
Copy the real URL from the green **Code** button on the repo's GitHub page.

**6. Open it in your editor**

Open the new folder in IntelliJ or VS Code (File → Open). From then on, you can use the terminal built into the editor for the everyday `git pull`, `git commit` and `git push` commands.

## Common problems

- **"Repository not found" or permission denied:** D hasn't added you as a collaborator yet, or you haven't accepted the email invite.
- **Cloned into the wrong place:** just drag the folder somewhere else in Finder. It still works, because git keeps everything it needs inside the folder.
- **Don't clone again to get updates.** Clone once. To get teammates' merged work, run `git pull` inside the folder.

If you'd rather not use Terminal at all, **GitHub Desktop** (desktop.github.com) does clone, commit, push and pull with buttons. It's fine for this assignment. You'll still need step 2 (or its settings equivalent) so commits are credited to you.

---

## 15. Yunxi (prompt)  
_2026-10-29 05:45 UTC_

is that link just the github repo url

---

## 16. Claude (reply)  
_2026-10-29 05:45 UTC_

Yes. It's the repo's GitHub page address with `.git` added to the end.

If your repo page is
```
https://github.com/yunxi-tan/restaurant-rush
```
then you clone with
```
git clone https://github.com/yunxi-tan/restaurant-rush.git
```

The `.git` is optional. Pasting the page URL without it also works, because GitHub accepts both.

The safest way to get it is to copy it, not type it: on the repo page, click the green **Code** button, make sure the **HTTPS** tab is selected, and click the copy icon. That gives you the exact URL with `.git` already on the end.

Don't pick the **SSH** tab (the one starting `git@github.com:`). It needs a separate key setup, and the login steps I gave you are for HTTPS.

---

## 17. Yunxi (prompt)  
_2026-10-29 05:52 UTC_

Attached: `Screenshot 2026-10-29 at 1.52.13PM.png`

_[image attached]_

how does the cd<repo>, am i supposed to replace repo with something

---

## 18. Claude (reply)  
_2026-10-29 05:52 UTC_

Yes. `<repo>` is a placeholder: replace the whole thing, **angle brackets included**, with the name of the folder that clone just created.

The angle brackets are what caused the error. In Terminal, `<` and `>` aren't plain text; they mean "read from a file" and "write to a file". So zsh tried to read from a file and got confused.

Your clone worked ("Receiving objects: 100%... done"), so the folder exists. To find its name, run:
```
ls
```
`ls` lists what's in your current folder (Documents). One of the entries will match the repo name, which is the last part of the GitHub URL. For `https://github.com/someone/restaurant-rush.git`, the folder is `restaurant-rush`.

Then:
```
cd restaurant-rush
```
Use your folder's real name. You can also type the first few letters and press **Tab**, and Terminal will fill in the rest. That avoids typos.

To check it worked, run:
```
git status
```
If it prints `On branch main`, you're inside the repo. If it prints `not a git repository`, you're in the wrong folder.

---

## 19. Yunxi (prompt)  
_2026-10-29 05:53 UTC_

there is no such file though

---

## 20. Claude (reply)  
_2026-10-29 05:53 UTC_

You probably typed my example name, `restaurant-rush`. That was only an illustration: your folder has whatever name your repo has on GitHub.

Run this and look at what it prints:
```
ls
```

- **You see a folder matching your repo's name:** `cd` into that exact name. Spelling and capitals must match: `Restaurant-Rush` and `restaurant-rush` are different folders. The easiest way is to type `cd `, the first 2 or 3 letters, then press **Tab**.
- **Nothing in the list looks like your repo:** the clone landed in a different folder, because you weren't in Documents when you ran it. Run `cd ~` then `ls` to check your home folder, which is the usual place.
- **Still can't find it:** run this, which searches your home folder for it:
  ```
  find ~ -maxdepth 3 -type d -name ".git" 2>/dev/null
  ```
  Each result ends in `/.git`. The folder just before `/.git` is your repo.

If none of that works, screenshot the output of `ls` together with the URL you cloned, and I'll tell you the exact command.

---

## 21. Yunxi (prompt)  
_2026-10-29 05:53 UTC_

Attached: `Screenshot 2026-10-29 at 1.53.09PM.png`

_[image attached]_

---

## 22. Claude (reply)  
_2026-10-29 05:53 UTC_

Your folder is called **`sc2002-restaurant-rush`**: the clone output says `Cloning into 'sc2002-restaurant-rush'...`. So run:
```
cd sc2002-restaurant-rush
git status
```

**Why `ls` failed:** "Operation not permitted" isn't a git problem. macOS is blocking Terminal from listing your Documents folder, a privacy setting. To fix it:

1. Open System Settings → Privacy & Security → **Files and Folders**.
2. Find **Terminal** and switch on **Documents Folder**. If Terminal isn't listed there, add it under **Full Disk Access** instead.
3. Quit Terminal fully (Cmd+Q) and reopen it.

**Optional but safer:** if your Documents folder syncs to iCloud (System Settings → your name → iCloud → iCloud Drive → "Desktop & Documents Folders"), iCloud can corrupt git's hidden files while it syncs. If it does sync, keep the repo in a folder iCloud doesn't touch:
```
mkdir ~/code
mv ~/Documents/sc2002-restaurant-rush ~/code/
cd ~/code/sc2002-restaurant-rush
```

---

## 23. Yunxi (prompt)  
_2026-10-29 05:55 UTC_

Attached: `Screenshot 2026-10-29 at 1.55.32PM.png`

_[image attached]_

okay so i have opened it what should i do from now, give a tutorial

---

## 24. Claude (reply)  
_2026-10-29 05:56 UTC_

You're the repo owner (the URL is under `nyunxi`), so you're doing the one-time setup from the plan. Do the steps in order: step 5 locks `main`, and you need to push the skeleton before that.

## 1. Check Java is installed

Open VS Code's built-in terminal with **Ctrl+`** (backtick, the key below Esc). It opens already inside your repo folder.

```
java -version
javac -version
```

- `java` runs programs and `javac` compiles them. Both should print a version, 17 or higher.
- If you get "command not found", install a JDK. Temurin 21 from adoptium.net is fine. Then restart VS Code.
- Also install the **Extension Pack for Java** (Extensions icon on the left sidebar → search for it → Install). It gives you error highlighting and a Run button.

## 2. Create the folder structure and a first class

In the Explorer, create the folder `src`, then a folder `restaurantrush` inside it, then the file `Main.java` inside that:

```
sc2002-restaurant-rush/
├── src/
│   └── restaurantrush/
│       └── Main.java
├── .gitignore
└── README.md
```

`Main.java`:
```java
package restaurantrush;

public class Main {
    public static void main(String[] args) {
        System.out.println("Restaurant Rush");
    }
}
```

What each part does:
- `package restaurantrush;` says this class belongs to the package `restaurantrush`. **The package name must match the folder name**, or compiling fails. Every class your group writes goes in this folder with this same first line.
- `public class Main` means the class name must match the file name exactly, `Main.java`.
- `public static void main(String[] args)` is where Java starts running the program. Exactly one class has it.
  - `static`: Java calls it without creating a `Main` object first.
  - `String[] args`: holds command-line arguments. You won't use them.

## 3. Compile and run from the terminal

```
javac -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main
```

- `javac -d out ...` compiles every `.java` file in the package and puts the `.class` files into a folder called `out`.
- `java -cp out restaurantrush.Main` runs the program. `-cp out` (classpath) tells Java where the compiled classes are. Then you give the full name: package dot class.

It should print `Restaurant Rush`. Put these two commands in the README under a "Build and run" heading. Graders will use them.

## 4. Fix .gitignore, then commit and push

Open `.gitignore` and add these lines at the bottom:
```
out/
.vscode/
.idea/
.DS_Store
```
These are compiled output, editor settings and a Mac system file. None of them belong in the shared repo.

Then commit and push straight to `main`. This is the only time anyone does that.
```
git status
git add .
git commit -m "Set up project structure, Main class and build instructions"
git push
```

- `git status` shows what changed. Check that `out/` is **not** listed; if it is, the .gitignore isn't working.
- `git add .` stages every changed file in the current folder for the next commit.
- `git commit` saves the snapshot with your message.
- `git push` uploads it to GitHub. Refresh the repo page to see it.

## 5. GitHub settings (in your browser)

1. **Settings → Collaborators → Add people**: add your 3 groupmates. They need to accept the email invite before they can clone and push.
2. **Settings → Branches → Add branch ruleset** (or "Add rule") for `main`: tick **Require a pull request before merging**.

From now on, nobody pushes to `main` directly, you included.

## 6. Practise one full cycle

Do this once yourself before your groupmates start, so you can show them how it works. Make a trivial README change:

```
git checkout -b d/readme-setup
```
This creates a new branch called `d/readme-setup` and switches to it (`-b` means create). Your changes now stay off `main`.

Edit README.md (e.g. add your group members' names), then:
```
git add README.md
git commit -m "Add group members to README"
git push -u origin d/readme-setup
```
`-u origin d/readme-setup` uploads the new branch to GitHub and links it to your local one, so later pushes on this branch only need `git push`.

On GitHub you'll see a yellow banner, **Compare & pull request**. Click it → **Create pull request** → **Merge pull request** → **Delete branch**.

Back in VS Code, update your copy:
```
git checkout main
git pull
```
Your local `main` now includes the merged change. Every task your group does follows this cycle: branch → commit → push → PR → merge → pull.

## Doing it with buttons instead

The **Source Control** icon on the left sidebar (the branching-lines icon) does the same things without typing:
- the branch name at the bottom-left of the window switches or creates branches;
- the message box and **Commit** button commit;
- **Sync / Publish Branch** pushes.

Use whichever you prefer, but learn the commands once. When something goes wrong, the terminal shows the real error message, and the buttons often hide it.

**Next:** send your groupmates the repo link and the clone steps. Then hold the Phase 0 design session before anyone writes a Stage 1 class.

---

## 25. Yunxi (prompt)  
_2026-10-29 06:01 UTC_

Attached: `Screenshot 2026-10-29 at 2.01.27PM.png`

_[image attached]_

what does this mean for point 3

---

## 26. Claude (reply)  
_2026-10-29 06:01 UTC_

Everything worked except one typo. You typed `-c`, but the option is **`-cp`**:
```
java -cp out restaurantrush.Main
```

The `javac` line succeeded. When `javac` compiles without errors, it prints nothing, so no output is good news. The error came only from `java`, because it doesn't have a `-c` option, so it stopped before running anything.

**Java version:** you have Java 25. Your groupmates and the graders may have an older version. Code that uses newer Java features won't compile on an older JDK. To be safe, compile with:
```
javac --release 17 -d out src/restaurantrush/*.java
```
`--release 17` makes the compiler reject anything that doesn't exist in Java 17, so the code works on any JDK from 17 up. Use this version of the command in the README, and have everyone compile with it.

---

## 27. Yunxi (prompt)  
_2026-10-29 06:03 UTC_

Attached: `Screenshot 2026-10-29 at 2.03.29PM.png`

_[image attached]_

---

## 28. Claude (reply)  
_2026-10-29 06:03 UTC_

Compiling worked, but Java can't find a compiled class named `restaurantrush.Main`. There are two likely causes. Run these two commands to see which one it is:
```
cat src/restaurantrush/Main.java
ls -R out
```
`cat` prints the file as it is saved on disk. `ls -R` lists everything inside `out`, including subfolders.

**Cause 1: the file isn't saved (most likely).** `cat` prints nothing, or less than the full code. VS Code shows a white dot on the file's tab when it has unsaved changes. `javac` reads the saved file on disk, and an empty file compiles without any error but produces no class. Press **Cmd+S** (or turn on File → Auto Save), then compile and run again.

**Cause 2: the `package` line is missing or misspelled.** `ls -R out` shows `Main.class` directly inside `out`, instead of `out/restaurantrush/Main.class`. Java puts each compiled class in a folder named after its package, so without the line the class lands in the wrong place. Make sure the very first line is exactly:
```java
package restaurantrush;
```
Then clear the old output and rebuild:
```
rm -rf out
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main
```

If it still fails, screenshot the output of the `cat` and `ls -R out` commands.

---

## 29. Yunxi (prompt)  
_2026-10-29 06:04 UTC_

they say that there is no such directory or file

---

## 30. Claude (reply)  
_2026-10-29 06:04 UTC_

That means Terminal can't find the file at `src/restaurantrush/Main.java`. Either the file was created somewhere else, or the terminal is looking in a different folder. Run these three commands to find out which:
```
pwd
ls -la
find . -name "*.java"
```
- `pwd` (print working directory) shows which folder the terminal is in. It should end in `/sc2002-restaurant-rush`.
- `ls -la` lists everything in that folder, including hidden files. You should see `src`, `.gitignore` and `README.md`.
- `find . -name "*.java"` searches this folder and all its subfolders for Java files, and prints the path of each one it finds.

What the results mean:

| What you see | Problem | Fix |
|---|---|---|
| `find` prints nothing | Main.java was never saved, or was created outside the repo | In the VS Code Explorer, check Main.java appears under `src` → `restaurantrush`. If it isn't there, create it there and press Cmd+S |
| `find` prints a different path, e.g. `./Main.java` or `./src/Main.java` | The file is in the wrong folder | Drag it in the Explorer into `src/restaurantrush/` |
| `find` prints `./src/restaurantrush/Main.java`, but `cat` still fails | You probably typed the path differently (check capitals and spelling) | Type `cat src/` and press **Tab** to auto-complete the path, so it can't be mistyped |

Then compile and run again:
```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main
```

If none of these match what you see, screenshot the output of all three commands together with the VS Code Explorer panel, and I'll tell you exactly what's wrong.

---

## 31. Yunxi (prompt)  
_2026-10-29 06:05 UTC_

Attached: `Screenshot 2026-10-29 at 2.05.22PM.png`

_[image attached]_

i think i just didnt put the 2002 in front

---

## 32. Claude (reply)  
_2026-10-29 06:05 UTC_

That's not it. `pwd` shows you're in the right folder, `/Users/ngyunxi/code/sc2002-restaurant-rush`, and `find` found the file at the correct path.

The real problem is that **Main.java is empty**. Look at the `cat` line in your screenshot: it found the file (no error) but printed nothing. So the file exists on disk with no content, which means the code you typed in VS Code was never saved. It's cause 1 from before:
- `javac` compiled an empty file. That isn't an error, it just produces nothing.
- So `out` was never created, which is why `ls -R out` failed.
- And `java` had no class to run.

To fix it:
1. Click the Main.java tab in VS Code. A white dot instead of an × on the tab means it's unsaved.
2. Press **Cmd+S**. Turn on **File → Auto Save** too, so this can't happen again.
3. Check that the code is now on disk, then compile and run:
```
cat src/restaurantrush/Main.java
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main
```
`cat` should now print your code, and the last command should print `Restaurant Rush`.

---

## 33. Yunxi (prompt)  
_2026-10-29 06:06 UTC_

Attached: `Screenshot 2026-10-29 at 2.06.16PM.png`

_[image attached]_

yes it printed out the code

---

## 34. Claude (reply)  
_2026-10-29 06:06 UTC_

It worked: `Restaurant Rush` on the last line is your program's output. Step 3 is done. Continue with the rest:

**Finish step 3:** add the build/run commands to README.md, and save it.
````
## Build and run
```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main
```
````

**Step 4:** add these lines to the bottom of `.gitignore` and save:
```
out/
.vscode/
.idea/
.DS_Store
```
Then:
```
git status
```
Check the list: you should see `src/`, `README.md` and `.gitignore`, and **not** `out/`. If `out/` appears, the .gitignore isn't saved. Then:
```
git add .
git commit -m "Set up project structure, Main class and build instructions"
git push
```
Refresh the repo page on GitHub. You should see the `src` folder and the updated README.

After that, go to step 5 (add collaborators and protect `main`).

---

## 35. Yunxi (prompt)  
_2026-10-29 06:07 UTC_

step 3 to 4 is on terminal or in the readme.md

---

## 36. Claude (reply)  
_2026-10-29 06:07 UTC_

Some of it goes into files and some into the terminal. Here's which is which:

| What | Where |
|---|---|
| The "## Build and run" block | **Typed into README.md** in VS Code, then Cmd+S. You're writing instructions for graders, not running anything. |
| `out/`, `.vscode/`, `.idea/`, `.DS_Store` | **Typed into .gitignore** in VS Code, at the bottom, then Cmd+S. |
| `git status`, `git add .`, `git commit ...`, `git push` | **Terminal**, one at a time, after both files are saved. |

A quick way to tell from here on: a line that starts with `git`, `javac`, `java`, `cd` or `ls` is a command, so it goes in the terminal. Anything else I show you in a code block is file content, and I'll name the file it belongs in.

---

## 37. Yunxi (prompt)  
_2026-10-29 06:08 UTC_

my step 4 was denied from my terminal

---

## 38. Claude (reply)  
_2026-10-29 06:08 UTC_

I need the exact error message to be sure, so please screenshot it. These are the two most likely causes:

**1. "Permission denied" or "403"** (most likely). Git is pushing with saved login details that aren't connected to your `gh` login, e.g. another GitHub account saved in your Mac's Keychain. Run:
```
gh auth status
gh auth setup-git
git push
```
`gh auth status` shows which account `gh` is logged in as; it should say `nyunxi`. `gh auth setup-git` tells git to use that login whenever it talks to GitHub.

If it still says denied to a different username, open the **Keychain Access** app, search for `github.com`, delete those entries, and run `git push` again.

**2. "protected branch" or "Changes must be made through a pull request".** You already did step 5 and protected `main`, so even you can't push to it directly. Either:
- remove the rule temporarily (Settings → Branches/Rules → delete the rule), push, then add it back; or
- push on a branch instead and merge it through a PR:
  ```
  git checkout -b d/setup
  git push -u origin d/setup
  ```
  Then on GitHub: Compare & pull request → Create → Merge.

If the error doesn't mention "denied", "403" or "protected", send me the screenshot.

---

## 39. Yunxi (prompt)  
_2026-10-29 06:08 UTC_

Attached: `Screenshot 2026-10-29 at 2.08.35PM.png`

_[image attached]_

---

## 40. Claude (reply)  
_2026-10-29 06:08 UTC_

Nothing is broken. You typed `out/` into the terminal, but it's file content for `.gitignore`, not a command. zsh tried to run the `out` folder as if it were a program, and you can't run a folder, so it said "permission denied". It didn't change anything.

What to do instead:
1. In VS Code's Explorer (left panel), click **.gitignore** to open it.
2. Go to the very bottom of the file and add these four lines:
   ```
   out/
   .vscode/
   .idea/
   .DS_Store
   ```
3. Press **Cmd+S**.

Then run these in the terminal, one at a time:
```
git status
git add .
git commit -m "Set up project structure, Main class and build instructions"
git push
```

Before you run `git add .`, check that `out/` isn't in the `git status` list. If it isn't, the .gitignore worked.

---

## 41. Yunxi (prompt)  
_2026-10-29 06:10 UTC_

Attached: `Screenshot 2026-10-29 at 2.10.14PM.png`

_[image attached]_

so correct?

---

## 42. Claude (reply)  
_2026-10-29 06:10 UTC_

Yes, all correct:

- **`out/` isn't in the `git status` list**, so .gitignore is working. Only `.gitignore`, `README.md` and `src/` were committed.
- **The commit worked:** "3 files changed", including the new `src/restaurantrush/Main.java`.
- **The push worked:** `main -> main` at the end means GitHub now has your commit. Refresh the repo page and you should see the `src` folder.

The one mistake was `git add.` with no space. Git read it as a single command called `add.`, which doesn't exist. You fixed it on the next line.

**Next is step 5, in your browser on the repo page:**
1. **Settings → Collaborators → Add people:** add your 3 groupmates by GitHub username. Each needs to accept the email invite.
2. **Settings → Branches → Add branch ruleset** (or "Add rule") for `main`: tick **Require a pull request before merging**.

Then do the step 6 practice cycle (branch → commit → push → PR → merge → pull) once before your groupmates start.

---

## 43. Yunxi (prompt)  
_2026-10-29 06:10 UTC_

Attached: `Screenshot 2026-10-29 at 2.10.48PM.png`

_[image attached]_

give me the rule set

---

## 44. Claude (reply)  
_2026-10-29 06:11 UTC_

Before you set it up, read the warning at the top of your screenshot: **"Your rulesets won't be enforced on this private repository."** Free personal GitHub accounts can create rules on private repos, but GitHub won't actually block anything. You have three options:

1. **Get GitHub Pro free through the GitHub Student Developer Pack** (education.github.com/pack, verify with your NTU email). Pro enforces rules on private repos. This is the best option if you have a few days, because verification can take time.
2. **Make the repo public.** I don't recommend it: other SC2002 groups could see and copy your code, and that can become an academic integrity problem for you too. Check the course's rules before doing this.
3. **Create the rule anyway and treat it as a group agreement.** Nothing gets blocked, so everyone has to stick to branches and PRs by habit. This is fine for a 4-person group if everyone has agreed to it.

Create the ruleset now either way. It starts being enforced automatically if you get Pro later.

## Ruleset settings

| Field | Set to |
|---|---|
| Ruleset Name | `Protect main` |
| Enforcement status | Active |
| Bypass list | Leave empty |
| Target branches | **Add target → Include default branch** (that's `main`) |

Scroll down to **Rules** (under "Branch rules"):

| Rule | Setting | Why |
|---|---|---|
| Restrict deletions | ✅ On | Nobody can delete `main` |
| Block force pushes | ✅ On | Nobody can overwrite the shared history |
| Require a pull request before merging | ✅ On | All changes go through a PR |
| ↳ Required approvals | **1** | Someone other than the author reviews each PR |
| ↳ Everything else under it | Off | Not needed for this assignment |
| Require status checks, signed commits, linear history, deployments, code scanning | Off | Need extra setup; no marks for them |

Click **Create** at the bottom.

**One catch with requiring 1 approval:** GitHub won't let you approve your own PR. So for the step 6 practice run, ask a groupmate to approve it once they've accepted the invite. If you want to practise alone first, set approvals to 0, then raise it to 1 afterwards.

---

## 45. Yunxi (prompt)  
_2026-10-29 06:13 UTC_

Attached: `Screenshot 2026-10-29 at 2.12.58PM.png`

_[image attached]_

so should i create the pro account or should i just leave it as it is

---

## 46. Claude (reply)  
_2026-10-29 06:13 UTC_

Leave it as it is. Enforcing the rule isn't worth the setup:

- **No marks depend on it.** Graders look at your tags, commit history and README. They can't tell whether GitHub enforced the rule or your group just followed it.
- **Pro might not even fix it.** GitHub's documentation says Pro enforces rulesets on personal private repos, but your banner specifically says you need a **Team organization** account. I can't confirm which one is right without testing it, so you could get the Student Pack and still see the same warning.
- **Four people can keep to a habit.** The rule only stops accidents, and the plan already makes everyone work on branches and use PRs.

Instead of the block, do these two things:
1. When you send the repo link, tell the group: **"Never push to main. Always branch, PR, get one approval, then merge."**
2. Whoever merges a PR checks it was approved first. If someone pushes straight to `main` by mistake, it's still recoverable, because nothing gets deleted.

Getting the Student Developer Pack is still worth it for other reasons (free Copilot, JetBrains IDEs and more). Just don't wait on it for this.

Next: add your 3 groupmates under **Settings → Collaborators**.

---

## 47. Yunxi (prompt)  
_2026-10-29 06:14 UTC_

okay im done with the adding too, what else do i need to do before setting up the file

---

## 48. Claude (reply)  
_2026-10-29 06:14 UTC_

Setup is done. Two things are left before anyone writes a game class: get your groupmates set up, then hold the Phase 0 design session.

## 1. Get your groupmates set up (message them today)

Send them:
1. The repo link, with a reminder to accept the email invite.
2. Their setup steps:
   - Install git and JDK 17 or newer, and check both with `git --version` and `javac -version`.
   - Run `git config --global user.name` and `user.email`, using the **same email as their GitHub account**.
   - Run `gh auth login`.
   - Clone the repo into a folder that isn't synced to iCloud.
   - Build and run with the commands in the README, and confirm it prints `Restaurant Rush`.
3. The group rule: **never push to main; always branch, PR, get 1 approval, then merge.**

Each person should open one practice PR (e.g. adding their name to the README) and get it approved by someone else. That tests their login and shows them the workflow before any real code depends on it.

## 2. Phase 0 design session (all four, before any Stage 1 code)

Bring the work plan PDF. By the end of the session you should have:

- [ ] Who is A, B, C and D
- [ ] The class list, sorted into boundary, control and entity
- [ ] The enum values for order status and customer state, written down exactly
- [ ] Which class handles each of the 6 turn steps
- [ ] The **method signatures** where one person's code calls another's
- [ ] A photo or draft of the Stage 1 class diagram
- [ ] Internal deadlines for the `stage-1`, `stage-2` and `stage-3` tags
- [ ] The food-choice rule (deterministic or seeded random)

Commit the signatures and decisions to the repo, e.g. as `docs/design-decisions.md`, through a PR. Everyone then codes against that file, and it also counts as evidence for the "development process" marks.

## 3. One question for your TA

Ask whether graders need the repo to be public, or whether you should add them as collaborators. The brief doesn't say. Send this now so you aren't sorting it out on submission day.

After Phase 0, each person creates their own class files on their own branch. Nobody should create class files before then, because the names and signatures aren't agreed yet.

---

## 49. Yunxi (prompt)  
_2026-10-29 06:14 UTC_

i am D

---

## 50. Claude (reply)  
_2026-10-29 06:15 UTC_

As D, you own the CLI, the scripted test runs, the README, and every release tag. Here's what that means at each point.

## Before Phase 0

1. **Re-read brief sections 3.1, 3.4 and 3.5.** They define everything the UI has to display and how invalid input must behave, which is your part of the marks.
2. **Prepare `docs/design-decisions.md`** with empty headings: Roles, Classes (BCE), Enums, Turn step owners, Method signatures, Food-choice rule, Deadlines. Fill it in during the session, then commit it through a PR.

## In Phase 0, push for the decisions you depend on

Your UI can only show what the controller exposes, and only re-prompt correctly if staff actions report failure. Get these two agreed before the session ends:

- **How the UI reads game state.** For example, one `getStateForDisplay()` method on `GameController`, or a handful of getters. Without this, C changes something and your display breaks.
- **How a failed action reports why it failed.** For example, `seat(...)` returns a result object holding success/failure and a reason. The brief requires that an invalid choice explains why, lets the manager choose again, and doesn't use up the staff task. Two classes have to cooperate for that: the rule check happens in C's code, the message and re-prompt in yours.

## Stage 1: your code

`ConsoleUI` does three jobs:
- Prints the start-of-turn state (every field listed in 3.1).
- Reads the Waiter's and Chef's choices.
- Prints the end-of-turn results (3.5), showing `N/A` as the average if nobody has paid yet.

It must contain **no game rules**. "Is this table free?" is checked by the controller or staff classes. The UI only displays the answer.

**The most common input bug** (likely to come up in your own testing and in exam questions):
```java
Scanner sc = new Scanner(System.in);
int choice = sc.nextInt();   // user types "abc" → throws InputMismatchException, game crashes
```
Read whole lines and convert them yourself:
```java
String line = sc.nextLine().trim();
try {
    int choice = Integer.parseInt(line);
} catch (NumberFormatException e) {
    System.out.println("Please enter a number.");
    // ask again
}
```
- `Scanner` reads text from the keyboard (`System.in`).
- `nextLine()` reads everything the user typed up to Enter, so bad input can never stay stuck in the scanner.
- `Integer.parseInt` converts the text to an `int`. It throws `NumberFormatException` if the text isn't a number, and `catch` handles that so the game re-prompts instead of crashing.
- Avoid mixing `nextInt()` and `nextLine()`. `nextInt()` leaves the Enter keypress behind, so the next `nextLine()` returns an empty string immediately.

## Stage 1: reproducible runs

Because your UI reads from `System.in`, you can replay a whole game from a text file:
```
java -cp out restaurantrush.Main < tests/victory.txt
```
`<` feeds the file into the program as if someone were typing it, one line per prompt. This is the angle-bracket redirect that confused zsh earlier. Write `tests/victory.txt` and `tests/defeat.txt` once the game works. Anyone can then reproduce both endings exactly, and that covers both the test-notes requirement and video segment 6.

## Each stage tag (your job)

Before each tag:
1. All PRs for the stage are merged.
2. A fresh clone builds and runs.
3. Both scripted runs still give the same result.

Then:
1. `git tag -a stage-N -m "..."` and `git push origin stage-N`.
2. Fill in the README transition table, using the compare link.

From Stage 2 on, the scripted runs are your regression check. If `victory.txt` stops producing a victory, someone broke a Stage 1 rule.

---

## 51. Yunxi (prompt)  
_2026-10-29 06:15 UTC_

so how should i start

---

## 52. Claude (reply)  
_2026-10-29 06:15 UTC_

Start with the parts that don't depend on Phase 0, in this order.

## 1. Message your group (today)

Copy, edit, send:

> Repo is set up: [link]. Accept the GitHub invite in your email.
>
> Before our design session, please:
> 1. Install git and JDK 17+ (check with `git --version` and `javac -version`)
> 2. Run `git config --global user.name "Your Name"` and `git config --global user.email "your-github-email"`
> 3. Run `gh auth login`, then clone the repo
> 4. Build and run using the README commands. It should print "Restaurant Rush"
> 5. Open one practice PR adding your name to the README
>
> Rule: never push to main. Always branch, PR, 1 approval, then merge.
>
> Design session: [propose 2 to 3 time slots]. Please bring the work plan PDF.

## 2. Add the design-decisions file (your first real PR)

This also practises the branch and PR cycle.
```
git checkout -b d/design-doc
```
Create `docs/design-decisions.md` in VS Code, with these headings and nothing under them yet:
```
# Design decisions
## Roles
## Classes (Boundary / Control / Entity)
## Enums
## Turn step owners
## Method signatures
## Food-choice rule
## Stage deadlines
```
Save it, then:
```
git add docs/design-decisions.md
git commit -m "Add design decisions template"
git push -u origin d/design-doc
```
Open the PR on GitHub. Merge it once a groupmate approves.

## 3. Build the input reader (the part of your code that doesn't depend on Phase 0)

Every menu choice in the game needs "read a number in a range; if it's invalid, explain why and ask again". That doesn't depend on any game class, so you can write it now.
```
git checkout main
git pull
git checkout -b d/input-reader
```

Create `src/restaurantrush/InputReader.java`:
```java
package restaurantrush;

import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Input ended before the game finished.");
            }
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("'" + line + "' is not a number.");
            }
        }
    }
}
```

What each part does:
- **`import java.util.Scanner;`** lets you write `Scanner` instead of its full name, `java.util.Scanner`.
- **`private final Scanner scanner;`**
  - `private`: only this class can touch the field (encapsulation).
  - `final`: once the constructor sets it, it can't be pointed at a different Scanner.
- **The constructor takes the Scanner as a parameter** instead of creating one. Two reasons:
  - The whole program should share one Scanner on `System.in`. Two Scanners on the same input each read ahead and steal lines from each other.
  - It's an example of dependency injection, which you can cite under DIP in the design discussion.
- **`while (true)` with `return` inside** is the re-prompt loop. It keeps asking until the input is valid, and `return` both exits the loop and hands the number back to the caller.
- **`hasNextLine()`** matters for scripted runs. If `victory.txt` has too few lines, you get a clear message instead of a confusing `NoSuchElementException`.
- **`trim()`** removes stray spaces, so `" 2 "` still counts as 2.
- **Order of checks:** first "is it a number?" (`parseInt` throws if not), then "is it in range?". Each failure gets its own message, which is what the brief requires.

Test it with a temporary change to `Main`:
```java
public static void main(String[] args) {
    InputReader in = new InputReader(new Scanner(System.in));
    int x = in.readInt("Choose 1-3: ", 1, 3);
    System.out.println("You chose " + x);
}
```
Add `import java.util.Scanner;` under the `package` line. Then compile and run:
```
javac --release 17 -d out src/restaurantrush/*.java
printf "abc\n7\n2\n" | java -cp out restaurantrush.Main
```
`printf "abc\n7\n2\n"` produces three lines of input, and `|` feeds them into the program, the same idea as `< victory.txt`. You should see the "not a number" message, then the range message, then `You chose 2`.

Put `Main` back to printing "Restaurant Rush" before you commit. Then commit `InputReader.java`, push, and open a PR.

## 4. Then stop and wait for Phase 0

The rest of `ConsoleUI` (displaying state, the Waiter and Chef menus) depends on the signatures C agrees to. Don't write those parts until the design doc is filled in, or you'll be rewriting them.

---

## 53. Yunxi (prompt)  
_2026-10-29 12:04 UTC_

is the design-decisions very important?

---

## 54. Claude (reply)  
_2026-10-29 12:04 UTC_

The decisions in it matter a lot. The file itself is optional, and no marks depend on it.

**What actually matters is that the method signatures are agreed and written down somewhere everyone can see.** If they aren't, four people code Stage 1 at the same time against four different ideas of how the classes talk to each other, and C spends the integration week rewriting everyone's code. For you, that's the display and the invalid-input handling breaking whenever C changes something.

Where you write them down is up to you:

| Option | Good | Bad |
|---|---|---|
| `docs/design-decisions.md` in the repo | Sits next to the code; shows up in commit history; easy to update by PR | Takes 5 minutes to set up |
| Pinned message or shared doc | Faster | Gets out of date, and nobody knows which version is current |
| Photo of the whiteboard | Fastest | Hard to update; signatures get misread |

The repo file is still my recommendation, because it costs almost nothing. It also helps later:
- **README and design discussion:** when you explain why a class changed between stages, you can point back to what was decided in Phase 0.
- **Development process marks:** a design doc that changes over time is evidence of how your design evolved.

If your group would rather skip it, keep only the **method signatures** and **enum values** sections and drop the rest.

---

## 55. Yunxi (prompt)  
_2026-10-29 12:04 UTC_

how should it be done

---

## 56. Claude (reply)  
_2026-10-29 12:04 UTC_

## Process

1. **During the Phase 0 session, you type while the group talks.** Share your screen, or sit where everyone can see your laptop, so mistakes get caught on the spot.
2. **Go through the sections in order.** Each section depends on the one above it: you can't agree signatures before you've agreed the classes.
3. **At the end, commit it on a branch and open a PR.** Everyone looks through it and one person approves it. That approval is the group saying "this is what we're coding against".
4. **Later changes go through a PR too, with a reason in the commit message.** For example: "Change Order.advanceOneUnit to return boolean so Chef knows when it's READY." Changes made this way can be cited directly in your stage transition analyses.

## What each section should look like

These are illustrations of the format. The actual content comes from your session.

```markdown
# Design decisions

## Roles
| Member | Role | Leads |
|---|---|---|
| Yunxi | D: CLI and release | Releases |
| ... | A: Customers | Stage 2 |

## Classes
| Class | Type | Responsibility (one line) | Owner |
|---|---|---|---|
| GameController | Control | Runs the 6 turn steps; owns queue, tables, orders, revenue | C |
| Customer | Entity | Tracks satisfaction and state; decides its own loss per turn | A |
| ConsoleUI | Boundary | Displays state, reads choices. No game rules | D |

## Enums
- OrderStatus: IN_PREPARATION, READY, SERVED, PAID, CANCELLED
- CustomerState: WAITING, SEATED, ORDERED, EATING, READY_TO_PAY, PAID, LEFT

## Turn step owners
| Step | What happens | Class | Owner |
|---|---|---|---|
| 1 Arrivals | Scheduled customer joins queue at 100 | GameController | C |

## Method signatures
### Order (owner: B)
- `Order(Customer customer, MenuItem item)`: price locked here
- `void advanceOneUnit()`: called by Chef; sets READY when last unit is done
- `boolean isReady()`
- `int getBillCents()`

### ActionResult (owner: C, used by D)
- `boolean isSuccess()`
- `String getReason()`: shown by ConsoleUI when an action fails

## Food-choice rule
Customer i orders menu item (i % 3). Documented so runs are reproducible.

## Stage deadlines
- stage-1 tag: [date]
- stage-2 tag: [date]
- stage-3 tag: [date]
```

## Rules for the signatures section

It's the section that matters most. For each method, write:
- **Return type, name, parameters with their types.** "Order has an advance method" isn't enough. `void advanceOneUnit()` is.
- **One line on what it does**, if the name doesn't make it obvious, and any rule it enforces.
- **Which class owns it.** Who calls it is useful too, but optional.

Only list methods that **another person's code calls**. Methods a class uses only internally are the owner's own business, so leave them out.

Don't aim for perfect. Expect to find missing methods while coding (the lecture says the same about sequence diagrams). Add them by PR as they come up.

---

## 57. Yunxi (prompt)  
_2026-10-29 12:05 UTC_

run me through how it should be done

---

## 58. Claude (reply)  
_2026-10-29 12:05 UTC_

Here's how to run the Phase 0 session from start to finish. Plan for about 2 hours, with you facilitating and typing.

## Before the session (you, 10 min)

- Create the branch and open the file, so you're ready to type:
  ```
  git checkout main && git pull
  git checkout -b d/design-decisions
  ```
  Open `docs/design-decisions.md` with the empty headings.
- Ask everyone to bring the brief and the work plan PDF.
- Add one extra heading at the bottom, **Parking lot**. Any Stage 2 or 3 idea goes there instead of into Stage 1 code.

## 1. Roles (5 min)

Confirm who is A, B and C. Fill in the Roles table. Don't let this turn into a debate. If two people want the same role, give C to whoever is strongest at Java.

## 2. Classes (20 min)

1. Read brief sections 2.1, 3.2 and 4.1 aloud. Everyone calls out nouns, and you list them all without filtering.
2. Then go through the list and ask one question of each noun: **"Does it have its own data AND its own behaviour?"**
   - Yes: it's a class (Customer, Order, Table, MenuItem, Waiter, Chef, Cashier).
   - Data only: it's an attribute of another class. For example, satisfaction belongs to Customer, and revenue belongs to the controller.
   - Fixed set of values: it's an enum (order status).
3. Label each class Boundary, Control or Entity, and write its responsibility in one line.

**The debate you'll have:** are Waiter, Chef and Cashier entities or controllers? I suggest treating them as entities that carry out actions on other entities, while GameController decides when they act. Whatever you choose, write the reason down, because the design discussion section asks for exactly this kind of trade-off.

## 3. Enums (10 min)

Walk through the worked example in brief section 3.3 (the Pasta customer) and list every state the customer passes through, and every state the order passes through. Also add the states that only happen when things go wrong: LEFT and CANCELLED. Write down the exact names now, because everyone's code will use them.

## 4. Turn step owners (15 min)

For each of the 6 steps, ask: **"Which object already has the data needed to do this?"** That object does the work.
- Step 5 (losing satisfaction) belongs to Customer, because Customer holds the satisfaction value.
- Step 4 (payment) is Cashier: it picks the oldest READY_TO_PAY customer, Order supplies the bill, and Table gets freed.

That principle is called **information expert**. Expect it as an exam angle.

## 5. Method signatures (40 min, the main event)

Don't try to invent methods from nothing. **Walk through the section 3.3 example turn by turn, out loud.** Every time one object needs something from another, write that call down as a method:

| Turn | What happens | Call you write down |
|---|---|---|
| 1 | Waiter seats customer | `Table.seat(Customer)`, `Table.isFree()` |
| 1 end | Customer loses 8 | `Customer.loseWaitingSatisfaction()` |
| 2 | Waiter takes order | `Customer.chooseItem(Menu)`, `new Order(customer, item)` |
| 2 | Chef works | `Order.advanceOneUnit()` |
| 3 | Order finishes | `Order.isReady()` |
| ... | ... | ... |

Continue until payment in turn 6. Then go through the invalid cases: a full table, an order that isn't READY, an empty queue. Those give you `ActionResult` (success flag plus reason).

Also settle how ConsoleUI reads the game state for the display. That's your dependency, so make sure it ends up written down.

This walk-through **is** your sequence diagram in rough form. Keep your notes: they'll save you hours when you draw the real one in Stage 3.

## 6. Remaining decisions (15 min)

- **Food-choice rule:** something simple and deterministic, e.g. customer number mod 3 picks the menu item.
- **Stage deadlines:** work backwards from the submission date. Leave at least a week after `stage-3` for the report and video.
- **Who creates which files first:** each person makes their own classes on their own branch.

## 7. Close (5 min)

```
git add docs/design-decisions.md
git commit -m "Record Phase 0 design decisions"
git push -u origin d/design-decisions
```
Open the PR and ask everyone to read it and approve **within 24 hours**. Once it's merged, everyone starts their Stage 1 branch.

## Facilitation rules

- **Time-box each section.** If something isn't settled when time runs out, pick one option, mark it "revisit", and move on.
- **Don't argue about names.** `getBillCents` versus `getTotalCents` doesn't matter. Pick one.
- **Stage 2 and 3 ideas go in the Parking lot.** The no-placeholders rule applies to the design doc too.

---

## 59. Yunxi (prompt)  
_2026-10-29 12:06 UTC_

so i create one docs/design decision? or how can it be done on vs code

---

## 60. Claude (reply)  
_2026-10-29 12:06 UTC_

It's one folder called `docs` with one file inside it called `design-decisions.md`. The `/` in `docs/design-decisions.md` separates the folder from the file. Your repo will look like this:
```
sc2002-restaurant-rush/
├── docs/
│   └── design-decisions.md
├── src/
│   └── restaurantrush/
│       └── Main.java
├── .gitignore
└── README.md
```

## In VS Code

1. **Switch to a new branch first.** In the terminal:
   ```
   git checkout main
   git pull
   git checkout -b d/design-decisions
   ```
2. **Create the folder and the file in one go.**
   - In the Explorer, hover over the project name (`SC2002-RESTAURANT-RUSH`) and click the **New File** icon (the page with a +).
   - Type `docs/design-decisions.md` and press Enter.
   - VS Code creates the `docs` folder and the file inside it, and opens the file.
3. **Paste in the headings**, then press **Cmd+S**:
   ```markdown
   # Design decisions

   ## Roles

   ## Classes (Boundary / Control / Entity)

   ## Enums

   ## Turn step owners

   ## Method signatures

   ## Food-choice rule

   ## Stage deadlines

   ## Parking lot (Stage 2/3 ideas, not for Stage 1 code)
   ```
4. **Optional: see the formatted version.** Press **Cmd+Shift+V** to open a preview that shows the headings and tables as they'll look on GitHub.

## Then decide when to commit

- **Commit the empty template now.** Then during the session you just fill it in and open the PR at the end:
  ```
  git add docs/design-decisions.md
  git commit -m "Add design decisions template"
  git push -u origin d/design-decisions
  ```
- **Or leave it uncommitted** and commit once, after the session, with everything filled in.

Either works. Committing the template now means it's safely on GitHub if anything happens to your laptop before the session.

---

## 61. Yunxi (prompt)  
_2026-10-29 12:24 UTC_

Attached: `Screenshot 2026-10-29 at 8.24.24PM.png`

_[image attached]_

but i do have the file so what is going on

---

## 62. Claude (reply)  
_2026-10-29 12:24 UTC_

The file exists, but in the wrong place. Your screenshot shows `docs` inside `src/restaurantrush`, so its real path is `src/restaurantrush/docs/design-decisions.md`. Git looked for `docs/design-decisions.md` at the top level of the repo, found nothing, and gave the error.

It happened because when you clicked New File, the `restaurantrush` folder was selected, so VS Code created the new path inside that folder.

Move it to the top level:
```
mv src/restaurantrush/docs docs
```
`mv` moves the folder. `src/restaurantrush/docs` is where it is now, and `docs` is where it goes (the top level, since that's where the terminal is).

If that fails with "Directory not empty" or similar, you already created a second `docs` folder with my `mkdir`/`touch` commands. In that case, paste your content into the top-level `docs/design-decisions.md` and delete the one inside `src/restaurantrush`.

Check the structure:
```
find . -name "design-decisions.md" -not -path "./.git/*"
```
It should print exactly one line: `./docs/design-decisions.md`. Then:
```
git add docs/design-decisions.md
git commit -m "Add draft design decisions for Phase 0"
git push -u origin d/design-decisions
```

To avoid this in future, click on empty space in the Explorer (or on the project name at the top) before you click New File. That way nothing is selected, and the path you type starts from the top of the repo.

---

## 63. Yunxi (prompt)  
_2026-10-29 12:25 UTC_

but shldnt this file be housed inside restaurant rush

---

## 64. Claude (reply)  
_2026-10-29 12:25 UTC_

It is still inside Restaurant Rush. There are two folders with similar names, and they do different jobs:

```
sc2002-restaurant-rush/          ← the project (the whole repo). Everything lives in here
├── docs/
│   └── design-decisions.md      ← documents for the group
├── src/
│   └── restaurantrush/          ← the Java package. Only .java files go here
│       └── Main.java
├── .gitignore
└── README.md
```

- **`sc2002-restaurant-rush`** is the repo, the whole project. `docs` at the top level is inside it, so it's on GitHub with everything else.
- **`src/restaurantrush`** is the Java package, and it holds source code only. It needs to match the `package restaurantrush;` line in each class, and `javac src/restaurantrush/*.java` compiles whatever is in there.

So `docs` sits next to `src`, not inside it: it's part of the project, but it isn't code. The README and the scripted test runs (`tests/`) also go at the top level, for the same reason.

---

## 65. Yunxi (prompt)  
_2026-10-29 12:25 UTC_

ohhh ok

---

## 66. Claude (reply)  
_2026-10-29 12:25 UTC_

Run the `mv` command, check that `find` prints only `./docs/design-decisions.md`, then add, commit and push.

After that, open the PR on GitHub and message your group with a few times for the Phase 0 session.

---

## 67. Yunxi (prompt)  
_2026-10-29 12:32 UTC_

okay now what should i do for d

---

## 68. Claude (reply)  
_2026-10-29 12:32 UTC_

Two pieces of your Stage 1 code don't depend on Phase 0, so do them now. Everything else waits for the session.

## 1. InputReader (if you haven't yet)

Follow the step 3 instructions from earlier: a new branch `d/input-reader`, then `InputReader.java`, the `printf` test, a commit and a PR. Do this first, because every menu in your UI is built on it.

## 2. Money formatting

The brief says money is stored as integer cents and displayed as dollars with 2 decimals. That conversion is display work, so it belongs to you. Put it in the same branch or a new one, inside `ConsoleUI.java`:

```java
package restaurantrush;

public class ConsoleUI {

    public static String formatCents(int cents) {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }
}
```

What each part does:
- **`cents / 100`** gives the dollars. Dividing two `int`s in Java throws away the remainder, so `1830 / 100` is `18`, not `18.3`.
- **`cents % 100`** gives the leftover cents. `%` is remainder, so `1830 % 100` is `30`.
- **`String.format("$%d.%02d", ...)`** builds the string.
  - `%d` is replaced by an integer.
  - `%02d` is an integer padded to 2 digits with a leading zero, so 5 cents prints as `05`, not `5`.
- **`static`** means you call it without a ConsoleUI object: `ConsoleUI.formatCents(1800)`.

**Bug to avoid:** writing `%d` instead of `%02d` turns 905 cents into `$9.5` when it should be `$9.05`. Test these values: `0` → `$0.00`, `905` → `$9.05`, `1800` → `$18.00`, `2430` → `$24.30`.

**Why not use `double`?** Doubles can't store most decimals exactly (`0.1 + 0.2` is `0.30000000000000004`). That's exactly why the brief requires cents, and it's a likely exam question.

## 3. Then wait for Phase 0

Don't write the rest of ConsoleUI yet: the start-of-turn display, the Waiter and Chef menus, and the end-of-turn results. All of them call `GameController` methods that the group hasn't confirmed. After the session:
1. The PR for `design-decisions.md` gets merged.
2. You build the display and menus against the confirmed signatures.
3. Once C's `GameController` runs, you write `tests/victory.txt` and `tests/defeat.txt`.

Right now, the most useful thing you can do as D is get the session booked. Everything after this depends on it.

---

## 69. Yunxi (prompt)  
_2026-10-29 12:37 UTC_

Attached: `excerpt_from_previous_claude_message.txt`, `excerpt_from_previous_claude_message.txt`

_[document attached]_

how to create in the terminal

---

## 70. Claude (reply)  
_2026-10-29 12:37 UTC_

You create the file in the terminal, then open it in VS Code to type the code. The terminal can create files, but it's not a good place to write Java.

**1. Start a fresh branch from `main`:**
```
git checkout main
git pull
git checkout -b d/input-reader
```
Branch off `main`, not `d/design-decisions`. Otherwise this PR would also carry the design doc.

**2. Create the empty file:**
```
touch src/restaurantrush/InputReader.java
```
The path has to be exact: `src/restaurantrush/`, so the file sits in the package folder next to `Main.java`.

**3. Open it in VS Code:**
```
code src/restaurantrush/InputReader.java
```
If you get `command not found: code`, either click the file in the Explorer, or enable the command once: Cmd+Shift+P → type `shell command` → choose **Install 'code' command in PATH**.

**4. Paste in the InputReader code and press Cmd+S.**

**5. Check it's saved and in the right place:**
```
cat src/restaurantrush/InputReader.java
```
This should print your code. If it prints nothing, the file isn't saved. That's the same problem `Main.java` had.

Then do the temporary `Main` test, the `printf` run, and the commit and PR from the earlier instructions.

---

## 71. Yunxi (prompt)  
_2026-10-29 12:45 UTC_

can you explain each line of the input reader to me and what is the purpose of it

---

## 72. Claude (reply)  
_2026-10-29 12:46 UTC_

## The whole class

```java
package restaurantrush;

import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Input ended before the game finished.");
            }
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("'" + line + "' is not a number.");
            }
        }
    }
}
```

**Its purpose:** every menu in the game ("choose 1 to 3", "pick a table") needs the same behaviour. Read a number. If it isn't a number, or is out of range, say why and ask again. Never crash. This class does that once, so ConsoleUI never repeats the logic.

## Setup lines

```java
package restaurantrush;
```
Declares which package this class belongs to. It must match the folder `src/restaurantrush/`. Classes in the same package can use each other without importing.

```java
import java.util.Scanner;
```
`Scanner` lives in Java's `java.util` package, not yours. `import` lets you write `Scanner` instead of `java.util.Scanner` every time. Without this line you get "cannot find symbol: Scanner".

```java
public class InputReader {
```
Defines the class. `public` means classes in any package can use it. The file must be named `InputReader.java`, matching exactly, capitals included.

## The field

```java
    private final Scanner scanner;
```
A **field**: data each InputReader object keeps for as long as it exists.
- **`Scanner`** is the type. A Scanner object reads text from an input source; here that's the keyboard.
- **`private`** means only code inside InputReader can access `scanner`. That's **encapsulation**: other classes use `readInt` and never touch the Scanner directly.
- **`final`** means once it's set (in the constructor), it can never point to a different Scanner. The compiler blocks `scanner = new Scanner(...)` anywhere else.

## The constructor

```java
    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }
```
A **constructor** runs once, when someone writes `new InputReader(...)`. Its job is to set up the object's fields.
- It has the **same name as the class and no return type**, not even `void`. Exam angle: if you write `public void InputReader(...)`, it's no longer a constructor but an ordinary method that happens to share the class's name.
- **`this.scanner = scanner;`** The parameter and the field are both called `scanner`. Inside the method, plain `scanner` means the parameter (the closer one wins). `this.scanner` means "the field of this object". So the line reads: store the Scanner I was given into my field.
  - Exam angle: if you write `scanner = scanner;`, the parameter is assigned to itself. The field stays `null`, and the first `readInt` call throws a `NullPointerException`.

**Why take the Scanner as a parameter instead of creating one here?**
1. The whole program must share **one** Scanner on `System.in`. Each Scanner reads ahead into its own buffer, so two Scanners on the same input steal lines from each other. That breaks the scripted runs especially.
2. Whoever creates the InputReader chooses the input source: the keyboard now, a file or a string for testing. That's **dependency injection**, and you can cite it under DIP in the design discussion: InputReader doesn't decide where its input comes from.

## The method signature

```java
    public int readInt(String prompt, int min, int max) {
```
- **`public`**: ConsoleUI can call it.
- **`int`** is the return type: the method promises to hand back an `int`. Every way out of the method must either return an `int` or throw an exception.
- **The parameters:** `prompt` is the text shown to the user, and `min` and `max` are the valid range, inclusive. One method serves every menu: `readInt("Waiter task (0-3): ", 0, 3)`, `readInt("Table (1-2): ", 1, 2)`.

## The loop

```java
        while (true) {
```
An **infinite loop**: the condition is always true, so it repeats until something inside forces an exit. There are only two exits: `return` (valid input) or `throw` (input ran out). Each invalid input reaches the end of the loop body and goes round again. That's the "let the manager choose again" requirement.

Exam angle: the compiler accepts this method even though there's no `return` after the loop. It knows `while (true)` can only be left by `return` or `throw`, so the end of the method can never be reached.

```java
            System.out.print(prompt);
```
Shows the prompt. `print`, not `println`, so the cursor stays on the same line and the user types right after "Choose 1-3: ".

## The end-of-input guard

```java
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Input ended before the game finished.");
            }
```
- **`hasNextLine()`** returns `true` if there's another line to read. At the keyboard it waits for the user to type, so it's practically always true. It only returns `false` when the input has run out. That happens when input comes from a file (`< victory.txt`) and the file has fewer lines than the game needs.
- **`!`** means "not". So the condition reads: if there is no next line.
- **`throw new IllegalStateException(...)`** creates an exception object and throws it. That immediately exits the method, and the program stops with your message.

Why the guard is there: without it, `nextLine()` on exhausted input throws `NoSuchElementException`, with a confusing stack trace. With it, you know immediately that your test file is too short.

## Reading the line

```java
            String line = scanner.nextLine().trim();
```
Two method calls chained together, run left to right:
1. **`scanner.nextLine()`** reads everything the user typed up to Enter and returns it as a `String`. The Enter keypress itself is consumed and not included.
2. **`.trim()`** is called on that String. It returns a new String with spaces removed from both ends, so `" 2 "` becomes `"2"`.

**Why `nextLine()` instead of `nextInt()`?** `nextInt()` reads only the number and leaves the Enter keypress sitting in the input. The next `nextLine()` call then grabs that leftover Enter and returns `""` immediately. Also, if the user types `abc`, `nextInt()` throws `InputMismatchException` and doesn't consume the bad text, so re-prompting reads the same `abc` forever. Reading whole lines avoids both bugs.

## Converting and validating

```java
            try {
                int value = Integer.parseInt(line);
```
- **`try { ... }`** means "run this code, and if a listed exception happens, jump to the matching `catch` instead of crashing".
- **`Integer.parseInt(line)`** converts text to a number: `"2"` becomes `2`. If the text isn't a whole number (`"abc"`, `""`, `"2.5"`), it throws `NumberFormatException`, and the remaining lines inside `try` are skipped.

```java
                if (value >= min && value <= max) {
                    return value;
                }
```
The range check. `&&` means both conditions must be true. If the value is in range, **`return value`** hands it back to the caller and exits the method and the loop at once. That's the only normal way out.

```java
                System.out.println("Enter a number from " + min + " to " + max + ".");
```
Only reached when the input is a number but out of range. `+` between a String and an `int` converts the `int` to text and joins them: `"Enter a number from 1 to 3."`. Execution then reaches the end of the loop body and goes round again.

## Handling non-numbers

```java
            } catch (NumberFormatException e) {
                System.out.println("'" + line + "' is not a number.");
            }
```
Runs only if `parseInt` threw. `e` is the exception object; it isn't used here, but Java requires you to name it. The message echoes the bad input back, so the user sees exactly what was wrong. That's the brief's "explain why it failed". Then the loop repeats.

Exam angle: `NumberFormatException` is an **unchecked** exception (it extends `RuntimeException`), so Java doesn't force you to catch it. You catch it here by choice, because crashing on a typo would fail the brief's robustness requirement.

## Trace: user types `abc`, then `7`, then `2`, for `readInt("Choose 1-3: ", 1, 3)`

| Loop pass | `line` | What happens | Output |
|---|---|---|---|
| 1 | `"abc"` | `parseInt` throws, so `catch` runs | `'abc' is not a number.` |
| 2 | `"7"` | Parses to 7, fails the range check | `Enter a number from 1 to 3.` |
| 3 | `"2"` | Parses to 2, in range, so `return 2` | (method ends, caller gets 2) |

Two separate checks give two different messages: "not a number" and "out of range". The brief requires the manager to be told *why* a choice failed. A single "invalid input" message for both cases wouldn't fully meet that.

## What this class deliberately doesn't do

It knows nothing about tables, customers or the game. "Is table 2 free?" is a game rule, so it's checked in C's code and reported through `ActionResult`. InputReader only checks **format** ("is this a number from 1 to 2?"). That matches the lecture's boundary-class rule: check format, not business rules.

---

## 73. Yunxi (prompt)  
_2026-10-29 13:26 UTC_

how to commit the input reader to git

---

## 74. Claude (reply)  
_2026-10-29 13:26 UTC_

Run these in the terminal, from the project folder.

**1. Check you're on the right branch:**
```
git branch
```
The line with the `*` is your current branch. It should be `d/input-reader`. If it isn't, run `git checkout d/input-reader`.

**2. Undo the test change to Main**, if you made one:
```
git restore src/restaurantrush/Main.java
```
This puts `Main.java` back to its last committed version, the one that prints "Restaurant Rush". Only InputReader belongs in this commit.

**3. Make sure it still compiles:**
```
javac --release 17 -d out src/restaurantrush/*.java
```
No output means it compiled. Never commit code that doesn't compile: it breaks everyone who pulls it.

**4. Check what will be committed:**
```
git status
```
You should see only `src/restaurantrush/InputReader.java`, under "Untracked files". It's untracked because git has never seen this file before.

**5. Commit and push:**
```
git add src/restaurantrush/InputReader.java
git commit -m "Add InputReader for validated numeric menu input"
git push -u origin d/input-reader
```

**6. Open the PR on GitHub:**
1. On the repo page, click **Compare & pull request**.
2. Check that the base is `main` and the compare branch is `d/input-reader`.
3. Write one line in the description, e.g. "Reads a number in a range and re-prompts on invalid input. Used by ConsoleUI for every menu."
4. Click **Create pull request**.
5. Ask a groupmate to approve it, then **Merge**.

**7. After it's merged, update your local copy:**
```
git checkout main
git pull
```

---

## 75. Yunxi (prompt)  
_2026-10-29 13:33 UTC_

so is this money formatting a new one or legit inside the file of consoleUI.java

---

## 76. Claude (reply)  
_2026-10-29 13:33 UTC_

It goes inside `ConsoleUI.java`. That file doesn't exist yet, so you create it, and `formatCents` is the first method in it. After Phase 0, you'll add the display and menu methods to the same file. It's one class that grows over time, not a separate class for money.

Steps:
```
git checkout main
git pull
git checkout -b d/console-ui
touch src/restaurantrush/ConsoleUI.java
```

Open the file in VS Code, paste this in, and press Cmd+S:
```java
package restaurantrush;

public class ConsoleUI {

    public static String formatCents(int cents) {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }
}
```

Test it with a temporary change to `Main`:
```java
public static void main(String[] args) {
    System.out.println(ConsoleUI.formatCents(0));
    System.out.println(ConsoleUI.formatCents(905));
    System.out.println(ConsoleUI.formatCents(1800));
    System.out.println(ConsoleUI.formatCents(2430));
}
```
```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.Main
```
You should see `$0.00`, `$9.05`, `$18.00` and `$24.30`.

Then undo the test, and commit only ConsoleUI:
```
git restore src/restaurantrush/Main.java
git add src/restaurantrush/ConsoleUI.java
git commit -m "Add ConsoleUI with cents-to-dollars formatting"
git push -u origin d/console-ui
```

Open the PR and get it approved, the same way as InputReader.

If InputReader's PR isn't merged yet, that's fine. Both branches start from `main` and touch different files, so they won't conflict.

---

## 77. Yunxi (prompt)  
_2026-10-29 13:40 UTC_

Attached: `Screenshot 2026-10-29 at 9.40.37PM.png`

_[image attached]_

wut has happened

---

## 78. Claude (reply)  
_2026-10-29 13:40 UTC_

Nothing is lost. Git refused to switch branches to protect a file. This is what happened:

1. `InputReader.java` is committed on `d/input-reader`. When you switched to `main`, git removed it from your folder, because `main` doesn't have that file yet. That's normal: each branch shows its own files.
2. But VS Code still had `InputReader.java` open, and Auto Save wrote it back to disk. So now there's a copy git doesn't track, sitting in your folder while you're on `d/console-ui`.
3. When you tried to go back to `d/input-reader`, git saw that switching would overwrite this untracked copy with the committed version. It stopped rather than risk losing whatever is in the copy.

Fix it by moving the copy out of the way, switching, then checking whether it contains anything you need:
```
mv src/restaurantrush/InputReader.java ~/InputReader-backup.java
git checkout d/input-reader
diff ~/InputReader-backup.java src/restaurantrush/InputReader.java
```
- `mv` moves the stray copy to your home folder, so git has nothing to overwrite.
- `diff` compares the two files and prints the lines that differ. If it prints nothing, they're identical.

**If `diff` prints nothing:** delete the backup with `rm ~/InputReader-backup.java`. Then add the min > max check, commit and push as planned.

**If `diff` shows your min > max check in the backup** (you'd already typed it in VS Code): copy it over, then commit:
```
cp ~/InputReader-backup.java src/restaurantrush/InputReader.java
rm ~/InputReader-backup.java
```

Two habits that stop this from happening again:
- **Before switching branches, close the tabs of files that only exist on the branch you're leaving.**
- **Run `git status` before every `git checkout`.** If it lists modified or untracked files, commit them or move them first.

Your empty `ConsoleUI.java` will come with you to `d/input-reader`, because untracked files aren't tied to a branch. That's harmless as long as you commit with `git add src/restaurantrush/InputReader.java`, naming the file, and not `git add .`.

---

## 79. Yunxi (prompt)  
_2026-10-29 13:42 UTC_

so after the rm is there still a need to commit

---

## 80. Claude (reply)  
_2026-10-29 13:42 UTC_

Yes. `rm` only deleted the backup copy in your home folder. That copy was outside the repo, so git never knew about it, and deleting it changes nothing in the repo.

Your PR still has the old `InputReader` without the min > max check. To update it:

1. Make sure the check is in `src/restaurantrush/InputReader.java`: either you copied it in with `cp`, or type it now. Then press Cmd+S.
2. Then:
   ```
   javac --release 17 -d out src/restaurantrush/*.java
   git status
   git add src/restaurantrush/InputReader.java
   git commit -m "Reject min greater than max in readInt"
   git push
   ```

`git status` should list `InputReader.java` as **modified**. If it doesn't, the check isn't in the file yet, or the file isn't saved.

After the push, refresh the PR page: the new commit appears there, and you can resolve the bot's comment.

---

## 81. Yunxi (prompt)  
_2026-10-29 13:48 UTC_

Attached: `Screenshot 2026-10-29 at 9.48.05PM.png`

_[image attached]_

i cant find the design decisions file

---

## 82. Claude (reply)  
_2026-10-29 13:48 UTC_

The file is on a different branch. `docs/design-decisions.md` was committed on `d/design-decisions`, and you're currently on `d/input-reader`, which doesn't have it. Each branch shows only its own files, so VS Code's Explorer changes when you switch.

You can check which branch you're on in the bottom-left corner of VS Code, or with `git branch`.

**Before switching, deal with InputReader.** It's shown in red in your Explorer, which usually means it has uncommitted changes, probably your min > max fix. Run:
```
git status
```
- **If `InputReader.java` is listed as modified:** commit it first, so the change isn't left behind on the wrong branch:
  ```
  git add src/restaurantrush/InputReader.java
  git commit -m "Reject min greater than max in readInt"
  git push
  ```
- **If `.gitignore` is also listed as modified:** check what changed with `git diff .gitignore`. If you didn't mean to change it, undo it with `git restore .gitignore`.
- **`ConsoleUI.java` marked U (untracked)** is your empty file from earlier. Leave it alone: untracked files come with you when you switch branches, and you'll commit it on `d/console-ui`.

**Then switch:**
```
git checkout d/design-decisions
```
The `docs` folder will appear in the Explorer. Paste in the updated file, press Cmd+S, then commit and push as planned.

**Also close the `.class` tabs** under "Open Editors" (`InputReader.class`, `Main.class`, `ConsoleUI.class`). Those are compiled files from `out/`, not your code, and they aren't meant to be read. Your source files are the `.java` ones in `src/restaurantrush`.

---

## 83. Yunxi (prompt)  
_2026-10-29 13:54 UTC_

Attached: `Screenshot 2026-10-29 at 9.53.35PM.png`

_[image attached]_

anyways so this hygiene is okay then when my teammates add their staff where should it fall under

---

## 84. Claude (reply)  
_2026-10-29 13:54 UTC_

Yes, the repo is in good shape: both PRs are merged into `main`, and the history is clean.

Your teammates' files go here:
```
sc2002-restaurant-rush/
├── src/restaurantrush/        ← ALL .java files, from everyone
│   ├── Main.java              (D)
│   ├── ConsoleUI.java         (D)
│   ├── InputReader.java       (D)
│   ├── GameController.java    (C)
│   ├── Waiter.java, Chef.java, Cashier.java, Table.java, ActionResult.java  (C)
│   ├── Customer.java, CustomerState.java   (A)
│   └── MenuItem.java, Menu.java, Order.java, OrderStatus.java   (B)
├── tests/                     ← victory.txt, defeat.txt, test notes (D)
├── docs/
│   ├── design-decisions.md
│   └── diagrams/              ← class and sequence diagram files (A, D)
├── .gitignore
└── README.md
```

Rules to tell them:
- **Every class goes in `src/restaurantrush/`**, with `package restaurantrush;` as its first line. Then the build command in the README compiles everyone's code with no changes.
- **One class per file, with the file name matching the class name exactly.** Enums count as classes, so `OrderStatus` goes in `OrderStatus.java`.
- **Each person creates their files on their own branch**, e.g. `a/customer`, `b/order`, `c/game-controller`. Because different people's files don't overlap, merging rarely conflicts.

**Why not separate `entity/`, `control/` and `boundary/` packages?** Separate packages would show the BCE structure in the folders, but they cost you:
- **More `import` lines:** every class would need imports to use classes from the other packages.
- **More `public` methods:** methods that today can stay visible only within the package would have to be made `public`, which weakens encapsulation.
- **A new build command:** the README's compile line would have to change.

One package is simpler for a 4-person project. The BCE labels already appear in `design-decisions.md` and will appear in the class diagram. If your group wants the packages anyway, decide it in Phase 0, before anyone writes a class, because moving files between packages later touches every file.

`design-decisions.md` is merged, but it's still marked DRAFT. Changes from the session go in as a new PR, which is the evidence trail for how the design evolved.

---

## 85. Yunxi (prompt)  
_2026-10-29 13:57 UTC_

so can i not code the consoleUI now

---

## 86. Claude (reply)  
_2026-10-29 13:57 UTC_

You can, as long as you accept two limits.

**What's changed since I said to wait:** you now have written signatures in `design-decisions.md`. Before, coding the UI would have been guesswork. Now you're coding against a draft that might change in the session, which is a smaller risk.

**Limit 1: it won't compile yet.** ConsoleUI calls `GameController`, `Customer`, `Table` and `ActionResult`, and none of those exist yet. Two ways around that:
- **Write it now and compile later**, once C's classes are merged. Mistakes only show up at that point.
- **Create temporary stub classes locally**: the same method signatures, with dummy bodies like `return 0;`. That lets you compile and test your UI. **Never commit the stubs**, because C's real classes will replace them, and committed stubs would clash with C's files.

**Limit 2: possible rework.** If the session renames methods or changes `ActionResult`, you'll update your calls. That's usually find-and-replace, so the cost is small.

**What's safe to build now:**
- The `run()` loop: start turn → show state → Waiter choice → Chef choice → end turn → show results → Victory or Defeat.
- The Waiter menu flow: pick a task, pick the target, call the controller. If the result is a failure, print the reason and ask again. If the manager chooses Wait, make no call.
- The Chef menu flow, the same way.
- The layout of the start-of-turn and end-of-turn displays.

**What to hold back:** don't open the PR until both the Phase 0 session has confirmed the signatures and C's classes exist. Keep your work on the `d/console-ui` branch and push it regularly so it's backed up, but don't merge it.

**One thing to watch:** if your UI is finished before the session, the group may just accept whatever it calls, because changing the signatures would mean reworking your code. Present it in the session as a draft, so A, B and C still shape their own classes.

---