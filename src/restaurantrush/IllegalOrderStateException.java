package restaurantrush;

public class IllegalOrderStateException extends IllegalStateException {

    private static final long serialVersionUID = 1L;

    public IllegalOrderStateException(String message) {
        super(message);
    }
}
