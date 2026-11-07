package restaurantrush;

// Thrown by InputReader when the player types q at any prompt.
// Main catches it and stops the program. It is not a game ending: no Victory or Defeat is given.
public class GameQuitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GameQuitException() {
        super("The player quit the game.");
    }
}
