public class ActionResult {
    // Needed to explain failed actions
    // Exceptions not to be used since this is a game (except for invalid order exception)
    private final boolean success;
    private final String reason;

    public ActionResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public static ActionResult ok() {
        return new ActionResult(true, "OK");
    }

    public static ActionResult fail (String reason) {
        return new ActionResult(false, reason);
    }

    public String getReason() {
        return this.reason;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
