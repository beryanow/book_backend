package beryanov.exception.critique;

public class UnavailableCritiqueException extends RuntimeException {
    private static final String UNAVAILABLE_QUOTE_MESSAGE = "Не найдена рецензия с id: %s";

    public UnavailableCritiqueException(String id) {
        super(String.format(UNAVAILABLE_QUOTE_MESSAGE, id));
    }
}
