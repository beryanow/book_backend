package beryanov.exception.book;

public class UnavailableBookException extends RuntimeException {
    private static final String UNAVAILABLE_BOOK_MESSAGE = "Не найдена книга с id: %s";

    public UnavailableBookException(String id) {
        super(String.format(UNAVAILABLE_BOOK_MESSAGE, id));
    }
}
