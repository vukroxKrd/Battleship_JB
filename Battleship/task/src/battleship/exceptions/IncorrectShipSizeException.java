package battleship.exceptions;

public class IncorrectShipSizeException extends Exception {

    private String message;

    public IncorrectShipSizeException(String msg) {
        this.message = msg;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
