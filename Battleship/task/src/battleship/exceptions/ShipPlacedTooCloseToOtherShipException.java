package battleship.exceptions;

public class ShipPlacedTooCloseToOtherShipException extends Exception {

    private String message;

    public ShipPlacedTooCloseToOtherShipException(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
