package commands;
import duke.DukeException;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * An abstract class which is extended by all command types in the application. \
 * Represents a command to be executed such as actions and modifications on the tasks and task lists.
 */
public abstract class Command {
    /**
     * Converts a string command to uppercase and match it to its enum CommandType.
     * @param command The string command to be converted.
     * @return The CommandType enum which matches the command.
     * @throws DukeException If the string command does not match to any of the CommandType enum.
     */
    public static CommandType commands(String command) throws DukeException {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException("Invalid command. Please try again."
                    + " Remember to leave a space after each command.");
        }
    }

    public abstract void executeCommand(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException;

    /**
     * A default exit boolean set as false for the application to continue the interaction loop.
     * @return false as long as the command is not to exit the application.
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Represents all possible commands to be used in the application.
     */

}

