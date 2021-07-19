package beryanov.exception.file;

public class EmptyFileException extends RuntimeException {
    private static final String EMPTY_FILE_MESSAGE = "Передан пустой файл";

    public EmptyFileException() {
        super(EMPTY_FILE_MESSAGE);
    }
}
