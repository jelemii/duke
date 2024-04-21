package commands;
import duke.DukeException;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;

public abstract class Command {
    public static CommandType commands(String command) throws DukeException {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException("Invalid command. Please try again."
                    + " Remember to leave a space after each command.");
        }
    }

    public abstract void executeCommand(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException;

    public boolean isExit() {
        return false;
    }

    public enum CommandType {
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        MARK,
        UNMARK,
        LIST,
        BYE
    }
}

