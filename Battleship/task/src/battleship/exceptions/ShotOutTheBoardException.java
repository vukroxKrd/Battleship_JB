package battleship.exceptions;

public class ShotOutTheBoardException extends Throwable {

    private String message;

    public ShotOutTheBoardException(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
