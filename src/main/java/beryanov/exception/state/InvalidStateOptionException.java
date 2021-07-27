package beryanov.exception.state;

public class InvalidStateOptionException extends RuntimeException {
    private static final String INVALID_STATE_OPTION_MESSAGE = "Выбрана неправильная опция изменения состоянии книги с id: %s";

    public InvalidStateOptionException(String id) {
        super(String.format(INVALID_STATE_OPTION_MESSAGE, id));
    }
}