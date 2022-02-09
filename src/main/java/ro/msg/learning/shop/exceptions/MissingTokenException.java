package ro.msg.learning.shop.exceptions;

public class MissingTokenException extends RuntimeException {
    public MissingTokenException() {
        super("no token found!");
    }
}
