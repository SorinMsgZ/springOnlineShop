package ro.msg.learning.shop.exceptions;

public class NoSuitableLocationsFound extends RuntimeException{
    public NoSuitableLocationsFound() {
        super("The selected strategy is unable to find a suitable set of locations from which the wished/ordered products should be taken!");
    }
}
