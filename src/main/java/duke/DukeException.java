package duke;

/**
 * Handles exceptions specific to Duke application such as validating user input and file input format
 */
public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }
}
