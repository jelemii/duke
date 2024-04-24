package parser;

import commands.*;
import duke.DukeException;
import tasks.*;

/**
 * A parser class that takes in a string line from a file and convert it to tasks.
 * It can also take in string input from user and parse it as a command to be executed.
 */
public class Parser {
    private final TaskList taskList;

    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Parse a string line from a file which contains the task type and contents
     * and reconstruct the correct task type.
     *
     * @param line A string from the file which contains the tasks type and contents of the task
     * @return The task that is reconstructed from the string line
     */
    public static Task parseTaskFromFile(String line) {
        String type = line.substring(1, 2);
        boolean isDone = line.charAt(4) == 'X';

        switch (type) {
        case "T" -> {
            String description = line.substring(7);
            Task todo = new Todo(description);
            if (isDone) {
                todo.markAsDone();
            }
            return todo;
        }
        case "D" -> {
            int descEndIndex = line.indexOf(' ', 7);
            String description = line.substring(7, descEndIndex);
            int byIndex = line.indexOf("(by: ");
            String by = line.substring(byIndex + 5, line.indexOf(')', byIndex));
            Task deadline = new Deadline(description, by);
            if (isDone) {
                deadline.markAsDone();
            }
            return deadline;
        }
        case "E" -> {
            int descEndIndex = line.indexOf(' ', 7);
            String description = line.substring(7, descEndIndex);
            int fromIndex = line.indexOf("(from: ");
            int toIndex = line.indexOf("to: ");
            String from = line.substring(fromIndex + 7, line.indexOf("to:", fromIndex));
            String to = line.substring(toIndex + 4, line.indexOf(')', fromIndex));
            Task event = new Event(description, from, to);
            if (isDone) {
                event.markAsDone();
            }
            return event;
        }
        default -> throw new IllegalStateException("Unexpected value: " + type);
        }

    }

    /**
     * Parse the input from the user to interpret the command to be executed.
     *
     * @param input The input by the user
     * @return The command to be executed.
     * @throws DukeException When the user inputs an invalid command, missing input or incorrect format.
     */
    public Command parseInput(String input) throws DukeException {
        String[] parts = input.trim().split("\\s+", 2); //https://stackoverflow.com/a/225360
        Command.CommandType commandType = Command.commands(parts[0]);
        String arguments = parts.length > 1 ? parts[1] : ""; //use ternary operator to check if there is a 2nd part, assign argument to parts[1]

        switch (commandType) {
        case TODO:
            if (arguments.isBlank()) {
                throw new DukeException("Input task is empty. Input the task you want to add "
                        + "after the \"todo\" command. e.g. \"todo task\"");
            } else if (taskList.isDuplicate(arguments)) {
                throw new DukeException("This task already exists in the list.");
            }
            return new AddTodoCommand(arguments);
        case DEADLINE:
            if (arguments.isBlank()) {
                throw new DukeException("Input task is empty. Input the task you want to add "
                        + "after the \"deadline\" command.\n"
                        + "e.g. \"deadline task /by date\"");
            } else if (!arguments.contains("/by")) {
                throw new DukeException("Invalid format. Please add a \"/by\" for deadline tasks.");
            }

            String[] deadlineParts = arguments.split("/by", 2);

            if (deadlineParts.length < 2 || deadlineParts[0].isBlank() || deadlineParts[1].isBlank()) {
                throw new DukeException("""
                        Task or date is empty. Input a task and a date for the
                        deadline you want after the "/by" command.
                        e.g. "deadline task /by date\"""");
            } else if (taskList.isDuplicate(deadlineParts[0])) {
                throw new DukeException("This task already exists in the list.");
            }

            String byDate = DateParser.formatDateInput(deadlineParts[1].trim());

            return new AddDeadlineCommand(deadlineParts[0].trim(), byDate);
        case EVENT:
            if (arguments.isBlank()) {
                throw new DukeException("Input task is empty. Input the task you want to add "
                        + "after the \"event\" command\n"
                        + "e.g. \"event task /from start-date /to end-date\"");
            } else if (!(arguments.contains("/from") && arguments.contains("/to"))) {
                throw new DukeException("Invalid format. Please add a \"/from\" and \"/to\" "
                        + "for event tasks.");
            }

            int fromIndex = arguments.indexOf("/from");
            int toIndex = arguments.indexOf("/to");
            String description = arguments.substring(0, fromIndex).trim();
            String from = arguments.substring(fromIndex + 5, toIndex).trim();
            String to = arguments.substring(toIndex + 3).trim();


            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new DukeException("""
                        Task, start-date-time or end-date-time is empty. Input a task and 2 dates for
                        the start-date and end-date you want for the task after the "/event" command.
                         e.g. "event task /from start-date-time /to end-date-time\"""");
            } else if (taskList.isDuplicate(description)) {
                throw new DukeException("This task already exists in the list.");
            }

            String fromDate = DateParser.formatDateInput(from);
            String toDate = DateParser.formatDateInput(to);
            if(!DateParser.isBefore(fromDate, toDate)) {
                throw new DukeException("The end date-time cannot be earlier than the start date time");
            }
            return new AddEventCommand(description, fromDate, toDate);
        case DELETE:
            if (taskList.getSize() == 0) {
                throw new DukeException("Task list is currently empty. Please add a task first.");
            } else if (arguments.isBlank()) {
                throw new DukeException("No index found. Please input the index of the task"
                        + " you want to delete.");
            }
            try {
                int getIndex = (Integer.parseInt(arguments.trim()) - 1);
                if (getIndex < 0 || getIndex >= taskList.getSize()) {
                    throw new IndexOutOfBoundsException();
                }
                return new DeleteCommand(getIndex);

            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Index does not exist.");
            }
        case MARK:
            if (taskList.getSize() == 0) {
                throw new DukeException("Task list is currently empty. Please add a task first.");
            } else if (arguments.isBlank()) {
                throw new DukeException("No index found. Please input the index of the task"
                        + " you want to mark.");
            }

            try {
                int getIndex = (Integer.parseInt(arguments.trim()) - 1);
                Task task = taskList.getTask(getIndex);
                if (getIndex < 0 || getIndex >= taskList.getSize()) {
                    throw new DukeException("Index does not exist.");
                } else if (task.getStatusIcon().equals("X")) {
                    throw new DukeException("Task is currently marked already.");
                }
                return new MarkCommand(getIndex);

            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Index does not exist.");
            }

        case UNMARK:
            if (taskList.getSize() == 0) {
                throw new DukeException("Task list is currently empty. Please add a task first.");
            } else if (arguments.isBlank()) {
                throw new DukeException("No index found. Please input the index of the task"
                        + " you want to unmark.");
            }

            try {
                int getIndex = (Integer.parseInt(arguments.trim()) - 1);
                Task task = taskList.getTask(getIndex);
                if (getIndex < 0 || getIndex >= taskList.getSize()) {
                    throw new DukeException("Index does not exist.");
                } else if (task.getStatusIcon().trim().isEmpty()) {
                    throw new DukeException("Task is currently unmarked already.");
                }
                return new UnmarkCommand(getIndex);

            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Index does not exist.");
            }
        case LIST:
            if (taskList.getSize() == 0) {
                throw new DukeException("List is empty. Please add a task first.");
            }
            return new ListCommand();
        case BYE:
            return new ExitCommand();
        default:
            throw new DukeException("Invalid command. Please try again."
                    + " Remember to leave a space after each command.");
        }

    }


}
