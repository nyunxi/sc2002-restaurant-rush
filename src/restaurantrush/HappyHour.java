package restaurantrush;

public class HappyHour {
  public static final int DISCOUNT_PERCENT = 20;
  public static final int DURATION_TURNS = 2;
  public static final int SATISFACTION_RECOVERY = 15;

  private boolean used;
  private int turnsRemaining;

  public boolean canActivate() {
    return !used;
  }

  public void activate() {
    if (used) {
      throw new IllegalStateException("Happy Hour can only be activated once per game.");
    }
    used = true;
    turnsRemaining = DURATION_TURNS;
  }

  public boolean isActive() {
    return turnsRemaining > 0;
  }

  public int getTurnsRemaining() {
    return turnsRemaining;
  }

  public boolean hasBeenUsed() {
    return used;
  }

  public void endTurn() {
    if (turnsRemaining > 0) {
      turnsRemaining--;
    }
  }

  public int applyTo(int cents) {
    return isActive() ? Money.percentOff(cents, DISCOUNT_PERCENT) : cents;
  }

  @Override
  public String toString() {
    if (isActive()) {
      return "Happy Hour: ACTIVE, " + turnsRemaining + " turn(s) left inluding this one";
    }
    return used ? "Happy Hour: used" : "Happy Hour: available (once per game)";
  }
}
