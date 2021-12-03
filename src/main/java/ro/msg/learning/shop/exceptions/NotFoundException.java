package ro.msg.learning.shop.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("The requested entity was not found!");
    }
}
