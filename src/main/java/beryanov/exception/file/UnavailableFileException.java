package beryanov.exception.file;

public class UnavailableFileException extends RuntimeException {
    private static final String UNAVAILABLE_FILE_MESSAGE = "Не найден файл с именем: %s";

    public UnavailableFileException(String name) {
        super(String.format(UNAVAILABLE_FILE_MESSAGE, name));
    }
}
