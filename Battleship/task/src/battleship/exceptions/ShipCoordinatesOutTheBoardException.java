package battleship.exceptions;

public class ShipCoordinatesOutTheBoardException extends Exception {

    private String message;

    public ShipCoordinatesOutTheBoardException(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
