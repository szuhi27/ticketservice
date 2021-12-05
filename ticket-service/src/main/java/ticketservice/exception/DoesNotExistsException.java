package ticketservice.exception;

public class DoesNotExistsException extends Exception {
    public DoesNotExistsException(String string){
        super(string);
    }
}
