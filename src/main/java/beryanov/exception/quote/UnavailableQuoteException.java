package beryanov.exception.quote;

public class UnavailableQuoteException extends RuntimeException {
    private static final String UNAVAILABLE_QUOTE_MESSAGE = "Не найдена цитата с id: %s";

    public UnavailableQuoteException(String id) {
        super(String.format(UNAVAILABLE_QUOTE_MESSAGE, id));
    }
}
