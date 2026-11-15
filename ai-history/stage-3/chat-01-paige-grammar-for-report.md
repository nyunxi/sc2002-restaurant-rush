# AI Interaction Log — Grammar and Syntax Refinement

**Project:** Restaurant Rush (SC2002)
**Date:** 15 November 2026
**Purpose:** Improve grammar, syntax, clarity, and academic tone in the software design report while preserving the original technical explanations and structure.

## 1. User Prompt (Full Original, Unedited)

The following is the complete original user message, including its original wording, spacing, and table formatting.

````text
help me to refine grammer and syntax:



Restaurant Rush uses the Boundary-Control-Entity approach to separate user interaction, game coordination and restaurant operations. Each class is assigned a specific responsibility to improve maintainability and reduce unnecessary dependencies.&#x20;

| &#x20;&#x20;&#x20; |
| ------------------ |

**Class/Abstraction**

| &#x20;&#x20;&#x20; |
| ------------------ |

**Type**

| &#x20;&#x20;&#x20; |
| ------------------ |

**Key Responsibilities**

| &#x20;&#x20;&#x20; |
| ------------------ |

ConsoleUI,   InputReader&#x20;

| &#x20;&#x20;&#x20; |
| ------------------ |

Boundary

| &#x20;&#x20;&#x20; |
| ------------------ |

To   display the game information, receive player choices and handle invalid   inputs.

| &#x20;&#x20;&#x20; |
| ------------------ |

GameController

| &#x20;&#x20;&#x20; |
| ------------------ |

Control

| &#x20;&#x20;&#x20; |
| ------------------ |

To   coordinate the 18-turn game, customer arrivals, staff actions, Happy Hour,   and conditions for victory.

| &#x20;&#x20;&#x20; |
| ------------------ |

Customer

| &#x20;&#x20;&#x20; |
| ------------------ |

Entity

| &#x20;&#x20;&#x20; |
| ------------------ |

To   manage customer satisfaction, state changes and food selection.

| &#x20;&#x20;&#x20; |
| ------------------ |

VIPCustomer,   CriticCustomer

| &#x20;&#x20;&#x20; |
| ------------------ |

Entity

| &#x20;&#x20;&#x20; |
| ------------------ |

To   extend customer behaviour through overridden waiting-loss, discount and food   selection methods&#x20;

| &#x20;&#x20;&#x20; |
| ------------------ |

MenuItem,   ComboMeal, Menu

| &#x20;&#x20;&#x20; |
| ------------------ |

Entity

| &#x20;&#x20;&#x20; |
| ------------------ |

To   define dishes, prices, preparation units and combination meals.

| &#x20;&#x20;&#x20; |
| ------------------ |

Order

| &#x20;&#x20;&#x20; |
| ------------------ |

Entity

| &#x20;&#x20;&#x20; |
| ------------------ |

To   track preparation progress, order status and the bill fixed at order   creation.

| &#x20;&#x20;&#x20; |
| ------------------ |

Waiter,   Chef, Host

| &#x20;&#x20;&#x20; |
| ------------------ |

Entity

| &#x20;&#x20;&#x20; |
| ------------------ |

To   perform restaurant operations while enforcing staff action limits.

| &#x20;&#x20;&#x20; |
| ------------------ |

Cashier,   Table

| &#x20;&#x20;&#x20; |
| ------------------ |

Entity

| &#x20;&#x20;&#x20; |
| ------------------ |

To   process customer payments and manage table occupancy.

| &#x20;&#x20;&#x20; |
| ------------------ |

HappyHour

| &#x20;&#x20;&#x20; |
| ------------------ |

Entity

| &#x20;&#x20;&#x20; |
| ------------------ |

To   manage promotional activation, duration and discounts.

| &#x20;&#x20;&#x20; |
| ------------------ |

ActionResult  &#x20;

| &#x20;&#x20;&#x20; |
| ------------------ |

Value object

| &#x20;&#x20;&#x20; |
| ------------------ |

To   communicate the success or failure of staff actions, and the reason for   failure if failure occurs.

| &#x20;&#x20;&#x20; |
| ------------------ |

Money

| &#x20;&#x20;&#x20; |
| ------------------ |

Utility

| &#x20;&#x20;&#x20; |
| ------------------ |

To   store every amount as integer cents, format it with two decimals and apply   percentage discounts rounded to the nearest cent.

| &#x20;&#x20;&#x20; |
| ------------------ |

OrderStatus,   CustomerState

| &#x20;&#x20;&#x20; |
| ------------------ |

Enum

| &#x20;&#x20;&#x20; |
| ------------------ |

To   define the agreed life cycles of an order and a customer. OrderStatus also   provides display labels and which statuses can be cancelled.

| &#x20;&#x20;&#x20; |
| ------------------ |

IllegalOrderStateException,   GameQuitException

| &#x20;&#x20;&#x20; |
| ------------------ |

Exception

| &#x20;&#x20;&#x20; |
| ------------------ |

To   signal a skipped order step (a programming error, never a player mistake) and   the player quitting or input running out.



The separation of the various classes allows GameController to coordinate the main sequence of the actions occurring in the game, while delegating specific operations to the relevant classes. For example, Chef.prepare() handles food preparation, while Cashier.processPayment() manages payment. Meanwhile, ConsoleUI interacts with the controller rather than directly changing the restaurant data.&#x20;

The design also uses encapsulation to protect important attributes. For example, Customer stores satisfaction and state as private fields, and Order stores the final bill as a private field too. This prevents unauthorized changes and preserves consistent gameplay rules.&#x20;



**2.2 Application of SOLID Principles&#x20;**

Single Responsibility Principle

Our code has a clear separation of concerns with distinct classes taking on different responsibilities. ConsoleUI is the class that deals with user interaction and GameController is the one that manages the game flow. The operational responsibilities are split between Waiter, Chef, Cashier and Host.&#x20;

Using the example from the above section, Chef.prepare() deals with the food preparation and Cashier.processPayment() with the payment and the updating of revenue. That separation enables you to change one operation without having to go and change the other unrelated staff classes.&#x20;

However GameController still takes care of a number of the gameplay elements (arrivals, staff co-ordination, victory checks etc). That was a compromise we made between having enough separation and not making the game simulation itself too complex.



Open/Closed Principle

The clearest example is ComboMeal in Stage 3. As ComboMeal extends MenuItem the existing ordering, preparation and payment code all work via the public methods of Menuitem (getName(), getPriceCents() and getPrepUnits()). Adding in the combos made no change to the Order's ordering logic, no change to Chef, Waiter, Cashier or CriticCustomer. The only change was to register the two combos in Menu's constructor (Section 2.5).

The customer hierarchy is open for extension from stage 2 onwards (though it wasn't originally). In stage 1 the 8 waiting loss was simply written into Customer. Stage 2 therefore had to modify Customer to add the overridable getWaitingLoss() and getDiscountRate() (section 2.5). From that point on VIPCustomer returns 5 and CriticCustomer returns 12 without having to duplicate loseWaitingSatisfaction(), and a further customer type would require no change to Customer.

Adding a customer type would still require one change outside of the new class (ie GameController.createCustomer()) which is the only place where the subclasses are referenced by name.



Liskov Substitution Principle

The Liskov Substitution Principle is illustrated by the ability of a subclass to replace its parent class without any of the existing operations being affected.

For example GameController has customers of the Customer type regardless of whether they're a Regular, a VIP or a Critic customer. When loseWaitingSatisfaction() is called, Java's dynamic method dispatch will pick the appropriate overridden getWaitingLoss() method.

Similarly CriticCustomer overrides chooseItem() to select the most expensive menu item. The Waiter will just keep on calling customer.chooseItem(menu) and won't need to work out which subclass of customer it is.

ComboMeal also stands in for MenuItem, enabling the Chef and the ordering system to deal with combo meals via the existing operations. These examples illustrate how you can extend the behaviour of a subclass whilst still maintaining the expected operations of the parent class.



Interface Segregation Principle (ISP) and Dependency Inversion Principle (DIP)

Our code is predominantly based on concrete classes rather than having a separate interface for each client's specific operations.&#x20;

For example GameController itself creates and depends upon concrete staff classes (Waiter, Chef and Cashier) rather than depending upon interfaces.

We thought that by introducing interfaces we'd be adding another layer of abstraction to a fixed set of staff roles. So we went for the simple implementation and clear class responsibilities rather than a more complex interface based architecture.&#x20;

For ISP we considered the idea of having the UI see read only views of the entities (e.g. a CustomerView interface with only getters) and discarded that as being yet another type that does nothing to change the behaviour. The UI's access is restricted in other ways: getQueue(), getTables(), getOrders() and getCustomers() all return unmodifiable lists and Happy Hour is only exposed via the query methods (isHappyHourActive(), getHappyHourTurnsRemaining() and isHappyHourUsed()), so ConsoleUI can never call activate() and thus never be able to circumvent the start of turn rule. The downside is that the Customer state changing methods are still visible to the UI. Having ConsoleUI use only getters is just a team convention, not something the compiler enforces.

For DIP each staff role and the pricing policy has exactly one implementation so an abstraction would just add indirection with nothing to swap. The point at which DIP would actually pay off is in the pricing. If we were to add a third promotion we'd introduce a PricingRule interface and have Order depend on the abstraction rather than on HappyHour directly.
````

## 2. Refined Output

### 2.1 Class Responsibilities and Design

Restaurant Rush adopts the **Boundary-Control-Entity (BCE)** approach to separate user interaction, game coordination and restaurant operations. Each class is assigned a specific responsibility to improve maintainability and minimise unnecessary dependencies.

| Class/Abstraction | Type | Key Responsibilities |
|---|---|---|
| ConsoleUI, InputReader | Boundary | Display game information, receive player choices and handle invalid inputs. |
| GameController | Control | Coordinate the 18-turn game, customer arrivals, staff actions, Happy Hour and victory conditions. |
| Customer | Entity | Manage customer satisfaction, state transitions and food selection. |
| VIPCustomer, CriticCustomer | Entity | Extend customer behaviour by overriding methods for satisfaction loss while waiting, discounts and food selection. |
| MenuItem, ComboMeal, Menu | Entity | Define dishes, prices, preparation units and combo meals. |
| Order | Entity | Track preparation progress, order status and the bill calculated and fixed at order creation. |
| Waiter, Chef, Host | Entity | Perform restaurant operations while enforcing staff action limits. |
| Cashier, Table | Entity | Process customer payments and manage table occupancy. |
| HappyHour | Entity | Manage promotional activation, duration and discounts. |
| ActionResult | Value object | Communicate whether staff actions succeed or fail, including the reason for any failure. |
| Money | Utility | Store monetary amounts as integer cents, format them to two decimal places and apply percentage discounts rounded to the nearest cent. |
| OrderStatus, CustomerState | Enum | Define the valid life cycles of orders and customers. OrderStatus also provides display labels and identifies which statuses allow cancellation. |
| IllegalOrderStateException, GameQuitException | Exception | Signal invalid order state transitions (programming errors rather than player mistakes), player-initiated quitting or exhausted input. |

This separation allows GameController to coordinate the overall sequence of gameplay while delegating specific operations to the relevant classes. For example, `Chef.prepare()` handles food preparation, while `Cashier.processPayment()` manages payments. Meanwhile, ConsoleUI interacts with GameController rather than directly modifying restaurant data.

The design also applies encapsulation to protect important attributes. For example, Customer stores satisfaction and state as private fields, while Order stores the final bill as a private field. This prevents unauthorised modifications and helps maintain consistent gameplay rules.

### 2.2 Application of SOLID Principles

#### Single Responsibility Principle (SRP)

Our code demonstrates a clear separation of concerns, with different classes responsible for distinct aspects of the game. ConsoleUI handles user interaction, while GameController manages the overall game flow. Operational responsibilities are distributed among Waiter, Chef, Cashier and Host.

As mentioned in Section 2.1, `Chef.prepare()` handles food preparation, while `Cashier.processPayment()` processes payments and updates revenue. This separation allows changes to be made to one operation without unnecessarily affecting unrelated staff classes.

However, GameController still manages several gameplay elements, including customer arrivals, staff coordination and victory checks. This reflects a deliberate trade-off between maintaining separation of responsibilities and avoiding unnecessary complexity in the game simulation.

#### Open/Closed Principle (OCP)

The clearest example of the Open/Closed Principle is the introduction of ComboMeal in Stage 3. Since ComboMeal extends MenuItem, the existing ordering, preparation and payment logic continues to operate through MenuItem's public methods: `getName()`, `getPriceCents()` and `getPrepUnits()`.

Adding combo meals did not require modifications to Order's ordering logic or to Chef, Waiter, Cashier or CriticCustomer. The only change required was registering the two combo meals in Menu's constructor (Section 2.5).

The customer hierarchy also became open for extension from Stage 2 onwards. In Stage 1, the waiting satisfaction loss of 8 was hardcoded in Customer. Stage 2 therefore required modifications to Customer to introduce the overridable methods `getWaitingLoss()` and `getDiscountRate()` (Section 2.5).

Following this change, VIPCustomer could return a waiting satisfaction loss of 5, while CriticCustomer could return 12, without duplicating the logic in `loseWaitingSatisfaction()`. Additional customer types can therefore introduce different waiting-loss behaviours without modifying Customer.

However, introducing a new customer type would still require a modification to `GameController.createCustomer()`, as this is the only location where the customer subclasses are explicitly referenced.

#### Liskov Substitution Principle (LSP)

The Liskov Substitution Principle is demonstrated by the ability of subclasses to replace their parent classes without disrupting existing operations or expected behaviour.

For example, GameController manages customers through the Customer type, regardless of whether they are regular, VIP or Critic customers. When `loseWaitingSatisfaction()` is called, Java's dynamic method dispatch selects the appropriate overridden `getWaitingLoss()` method based on the customer's actual type.

Similarly, CriticCustomer overrides `chooseItem()` to select the most expensive menu item. Waiter continues to call `customer.chooseItem(menu)` without needing to identify the specific customer subclass.

ComboMeal can also be used wherever MenuItem is expected, allowing Chef and the ordering system to process combo meals through existing methods. These examples demonstrate how subclasses can extend or specialise behaviour while preserving the expected functionality of their parent classes.

#### Interface Segregation Principle (ISP) and Dependency Inversion Principle (DIP)

Our code primarily uses concrete classes rather than defining separate interfaces for each client's specific operations.

For example, GameController creates and depends directly on concrete staff classes, such as Waiter, Chef and Cashier, rather than relying on interfaces.

We considered introducing interfaces but concluded that doing so would add another layer of abstraction to a fixed set of staff roles. Therefore, we prioritised a simpler implementation with clearly defined class responsibilities over a more complex interface-based architecture.

**Interface Segregation Principle (ISP)**

For ISP, we considered introducing read-only interfaces for entities accessed by the UI, such as a CustomerView interface containing only getter methods. However, we decided against this approach because it would introduce additional types without changing the underlying gameplay behaviour.

Instead, UI access is restricted through existing methods. The methods `getQueue()`, `getTables()`, `getOrders()` and `getCustomers()` return unmodifiable lists, preventing ConsoleUI from directly modifying these collections.

Additionally, Happy Hour information is exposed only through query methods such as `isHappyHourActive()`, `getHappyHourTurnsRemaining()` and `isHappyHourUsed()`. Consequently, ConsoleUI cannot directly call `activate()`, preventing it from bypassing the start-of-turn activation rule.

However, methods that modify Customer's state remain accessible to the UI. Therefore, the practice of using only getter methods in ConsoleUI is enforced through team conventions rather than compiler-level restrictions.

**Dependency Inversion Principle (DIP)**

For DIP, each staff role and the pricing policy currently has only one implementation. Introducing interfaces at this stage would therefore add an extra layer of indirection without providing a clear practical benefit.

However, DIP would become more valuable if the pricing system were expanded. For example, if a third promotion were introduced, we could define a PricingRule interface and make Order depend on this abstraction rather than directly on HappyHour.

This would allow additional pricing rules to be introduced with fewer changes to the existing ordering logic.

## 3. Changes Made by AI

- Corrected grammar, punctuation, agreement, and awkward syntax.
- Restructured the malformed responsibility table into valid Markdown.
- Made tone more consistent and suitable for a university technical report.
- Removed unnecessary repetition and tightened explanations.
- Preserved class names, method names, stage references, and design trade-offs.
- Clarified the limits of SOLID compliance instead of claiming full adherence.

## 4. AI Feedback and Verification Points

1. **OCP:** Registering ComboMeal in Menu's constructor still modifies existing code; document this limitation accurately.
2. **ISP:** Unmodifiable lists prevent collection modification, but not necessarily mutation of objects inside the lists.
3. **DIP:** Verify whether Order actually depends directly on HappyHour before describing that dependency. Adjust the example if pricing is calculated through another class.

## 5. Human Review and Responsibility

The AI was used for **language refinement and editorial feedback**, not as the source of the project's implementation or design. The team remains responsible for verifying that all statements about the code correspond to the final implementation.
