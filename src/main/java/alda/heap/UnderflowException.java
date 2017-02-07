package alda.heap;

public class UnderflowException extends RuntimeException {
    public UnderflowException(String error) {
        super(error);
    }
}
