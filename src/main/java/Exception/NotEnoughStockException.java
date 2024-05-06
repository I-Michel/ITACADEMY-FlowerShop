package Exception;

public class NotEnoughStockException extends Exception {
    public NotEnoughStockException() {
        super("There's not enough stock.");
    }
}