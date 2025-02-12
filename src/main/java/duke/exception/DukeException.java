package duke.exception;

public class DukeException extends Exception {
    /**
     * Default constructor with error message.
     *
     * @param msg Error message
     */
    public DukeException(String msg) {
        super(msg);
    }
}
