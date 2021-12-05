package ticketservice.exception;

public class NotAdminException extends Exception {
    public NotAdminException() {
        super("Not authorized!");
    }
}
