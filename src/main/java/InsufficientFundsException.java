/**
 * Thrown when an account does not have enough balance
 * to complete a withdrawal or transfer.
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Creates a new InsufficientFundsException with a message.
     *
     * @param message error message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
