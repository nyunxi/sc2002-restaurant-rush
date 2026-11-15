# **Restaurant Rush: understanding / brainstorming exchanges** 

## **1. ActionResult: does the success reason depend on the class?** 

**My prompt:** doesn't the reason depend on the class  specific item **AI reply:** 

You’re right that failure reasons depend on the class, and the design already handles that. Failure: the reason is class-specific. Waiter might say “no free table” and Cashier might say “no customer ready to pay”. The failure factory method takes the reason as a parameter, so each class supplies its own message. ActionResult never needs to know the details. Success: there’s nothing to explain, so no class-specific reason is needed. The success factory takes no parameter and fills in a fixed placeholder. 

For that placeholder, pick between null , an empty string, or a short fixed message like "OK" . Think about what happens when the UI prints getReason() on a success result. Which of those three could crash or look wrong? 

If you later want success to carry data (e.g. a payment amount), that would be a different design, such as a field on ActionResult . For Stage 1 you don’t need it. 

## **2. Is GameController the 'big boss'?** 

**My prompt:** Is the GameController class the big boss 

**AI reply:** 

Yes in terms of coordination, but not in terms of knowing everything. 

What makes it the “boss” 

It owns the collections (queue, tables, orders, customers) and the turn flow. 

It decides the order things happen (Waiter before Chef, pay before eating, the 6 steps). It’s the only class the UI talks to for the game’s state and actions. 

What it should not do 

It shouldn’t know how a Waiter seats someone, how an Order advances, or how the Cashier tallies money. It delegates to Waiter , Order and Cashier . 

It shouldn’t read input or print. That’s the UI’s job. 

It shouldn’t check customer types ( instanceof ), so VIP and Critic can be added later without editing it. 

In design terms this is the control class in the Boundary-Control-Entity split: the UI (boundary) talks to it, and it directs the entity classes. It’s meant to coordinate, not contain all the rules. Watch for the “god class” trap. If it ends up holding the pricing, satisfaction rules and payment details itself, it would break the Single Responsibility principle. In the version I gave you, each rule sits in its own class (e.g. the Cashier does payment, Customer does satisfaction loss), and GameController only decides when to call them. That’s the balance to aim for, and 

it’s worth a line in your reflections. 

## **3. What does ARRIVAL_TURNS mean?** 

**My prompt:** private static final int[] ARRIVAL_TURNS  = {1, 4, 7, 10, 13, 16}; ?? **AI reply:** 

That line lists the turns on which a customer arrives. The brief says one customer arrives at the start of turns 1, 4, 7, 10, 13 and 16. 

Part Meaning 

private Only GameController can use it. 

static One shared copy belongs to the class, not to each GameController object. final The variable can’t be reassigned to a different array. (The array’s contents could still be changed by code, but nothing does.) 

int[] An array of integers. 

ARRIVAL_TURNS Capital letters and underscores are the Java convention for constants. = {1, 4, 7, 10, The values, written as an array initialiser. 13, 16} 

Why it’s there: it keeps “when do customers arrive?” in one named place, instead of scattering the numbers through the code (or writing turn % 3 == 1 ). If the schedule changed, you’d edit only this line. 

How you use it (blank 2 in startTurn ): loop over the array, and if an entry equals the current turn , create and queue a customer. java for (int t : ARRIVAL_TURNS) { if (t == turn) { ... } } 

That’s the enhanced for loop from your tutorial: “for each t in the array.” 

## **4. Does git pull override my work?** 

**My prompt:** If i pull a github repository, does it  override my work? 

**AI reply:** 

Usually no. git pull fetches the others’ changes and merges them in, and it won’t silently erase your work. But the details matter: 

Files only you changed, or new files you created: these are untouched. 

A file changed by both you and your teammates: you may get a merge conflict. Git marks the conflicting lines and asks you to resolve them. Nothing is lost. 

Uncommitted changes to a file the pull would also change: Git refuses and stops with an error (“your local changes would be overwritten”). It doesn’t overwrite them. A new file of yours with the same name as one in the repo (e.g. you created your own CustomerState.java ): Git refuses until you move or delete yours. 

Safest routine 

1. Commit your work first on your own branch: git add . , then git commit -m "WIP: staff classes" . A commit is a safe snapshot you can always get back to. 

2. Then run git pull (or git pull origin main to bring in the latest main ). 

3. Compile with javac restaurantrush/*.java , and fix any conflicts or errors. If you made a stand-in CustomerState.java , delete it before pulling, so it doesn’t collide with Paige’s file. Check before you pull: run git status to see what’s modified, and make sure you’re on your own branch ( git branch ), not main . 

## **5. Is the rule check matrix what the assignment asks for?** 

**My prompt:** Just a sense check, this si what the assignment  is asking for right? **AI reply:** 

� 

It fits, but the brief doesn’t ask for it by name. Here is the comparison. 

What the brief requires: 

The report must contain “final diagrams, design discussion, functional evidence and reflections” (6.1). 

The test notes must include one reproducible victory run and one reproducible defeat run (4.1). 

Implementation is marked on four things: Stage 1 core game, Stage 2 and 3 features, code quality and reproducibility (20 marks). 

What it doesn’t say: nothing in the brief mentions a “rule check matrix” or screenshots. Both are our group’s way of presenting “functional evidence”. That’s fine, because the brief leaves the format open, and a table that traces each rule to the code and to a run is a strong way to show features and reproducibility. 

Two limits: 

Code quality: the matrix doesn’t cover it. The design discussion, class diagram and clean code do. 

Where the marks sit: the evidence supports the implementation criterion (20 marks). It doesn’t carry marks of its own. 

So I’d keep the matrix and screenshots, but not spend extra hours polishing them. Time is better spent making sure the video, the reflections and the other report sections are done well. If your instructor said what format to use for functional evidence, follow that. I only have the brief. 

## **6. Are the matrix section headers important?** 

**My prompt:** Are the headers important (setup and turn  sequence) **AI reply:** 

No, the headers aren’t required. The brief doesn’t ask for them. They’re only grouping rows that I added to make a 33-row table easier to scan, so you can drop them if you want a plain table. 

Why they help: a grader can skip to “Stage 3” or “Rules that always apply” instead of reading 33 rows. They also follow the brief’s own section numbers (2.1, 3.2, 3.4, 3.5, 4.2, 4.3), so a marker can match rows to the specification. 

If you remove them, nothing else breaks, because the row numbers and figure references stay the same. I’d keep at least the Stage 2 and Stage 3 headers, since they show which 

rows are the extensions. 

## **7. Why must ActionResult be its own class?** 

**My prompt:** I don't think Action Result is hard to  understand, it's just that I did not realise it needed to be its own class instead of the other class methods just declaring success/failure. Why can't it be the sewcond way? 

### **AI reply:** 

It could be done the second way, so it isn’t that you can’t. It would just work worse in this project. If each method only declared success or failure, there are three options, and each has a problem: 

Return a boolean . The caller learns that it failed but not why. The UI couldn’t tell the player “Table 2 is occupied” or “the Cashier has already taken a payment this turn.” Return a String message. This carries the reason, but nobody can tell success from failure without comparing text, which is fragile. 

Print the message inside the method. This puts display code inside the game logic, so the rules and the UI are mixed together. Yun Xi’s reflection makes exactly this point about keeping them apart. 

A Java method returns one value, but here each action has to report two things: whether it worked, and why not. A small class bundles both, with isSuccess() and a reason. It also gives every method the same return type ( Table.seat , Waiter , Cashier , 

GameController ), so the UI handles every action the same way. 

It’s the “return one object that carries everything the caller needs” idea. You can still decide that for a tiny project a boolean is enough, and that is a reasonable trade-off. For a game that needs to explain failures to the player, the class is worth it. 

## **8. Why must each action report its failure reason?** 

**My prompt:** why (follow-up on "and why not") **AI reply:** 

Because the player chooses the actions, many of those choices will be invalid: seating at an occupied table, taking an order from someone who isn’t seated, or paying twice in one turn. When that happens the game shouldn’t just ignore it or crash. It should tell the player what went wrong and ask again, which is why GameController ’s comment says “UI calls the methods below and re-prompts on failure.” 

The reason (“Table 2 is occupied”) is what makes that possible. A bare success/failure flag would let the UI say only “that didn’t work”, and the player wouldn’t know what to change. The reason also shows up in your rejects.txt and failures.txt test runs, where invalid actions are rejected with a message and the game continues. 

