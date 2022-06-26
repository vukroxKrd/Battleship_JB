package battleship.exceptions;

public class WrongShipLocationException extends Exception {

    private String message;

    public WrongShipLocationException(String msg) {
        this.message = msg;
    }


    @Override
    public String getMessage() {
        return message;
    }

}
