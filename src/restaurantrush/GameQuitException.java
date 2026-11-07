package restaurantrush;

// Thrown by InputReader when the player types q, or when input runs out (Ctrl+D, or a test file that is too short).
// Main catches it and stops the program. It is not a game ending: no Victory or Defeat is given.
// It has its own type so Main does not also catch IllegalStateExceptions that signal real bugs.
public class GameQuitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GameQuitException(String reason) {
        super(reason);
    }
}
