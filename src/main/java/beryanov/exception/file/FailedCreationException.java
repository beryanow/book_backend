package beryanov.exception.file;

public class FailedCreationException extends RuntimeException {
    private static final String EMPTY_FILE_MESSAGE = "Произошла ошибка при создании директории";

    public FailedCreationException() {
        super(EMPTY_FILE_MESSAGE);
    }
}
