package Exception;

public class EmptyStockException extends Exception {
    public EmptyStockException() {
        super("Stock you are trying to use is empty.");
    }
}
