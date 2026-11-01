package restaurantrush;

/**
 * Thrown when someone asks an {@link Order} to do something its current
 * status does not allow, e.g. serving a dish that is still IN_PREPARATION or
 * charging for one that has not been SERVED.
 *
 * It is unchecked (extends {@link IllegalStateException}, a RuntimeException),
 * because a correct turn engine checks first ({@code isReady()},
 * {@code canAdvance()}, {@code canCancel()}) and never triggers it. If it does
 * fire, the message says exactly why, so the turn engine can also catch it and
 * pass {@code getMessage()} to the UI as the failure reason (brief 3.4).
 *
 * Owner: B (Menu and pricing)
 */
public class IllegalOrderStateException extends IllegalStateException {

    private static final long serialVersionUID = 1L;

    public IllegalOrderStateException(String message) {
        super(message);
    }
}
