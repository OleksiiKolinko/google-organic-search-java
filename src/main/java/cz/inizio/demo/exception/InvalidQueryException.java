package cz.inizio.demo.exception;

public class InvalidQueryException extends RuntimeException {
    public InvalidQueryException() {
        super("Zadejte klíčové slovní spojení.");
    }
}
