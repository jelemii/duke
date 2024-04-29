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
        //use -1 as a null because if statement will make the variable out of scope
        int tagIndex = line.contains("#") ? line.indexOf("#") : -1;

        switch (type) {
        case "T":
            return getTodo(line, tagIndex, isDone);
        case "D":
            return getDeadline(line, tagIndex, isDone);
        case "E":
            return getEvent(line, tagIndex, isDone);
        default:
            throw new IllegalStateException("Unexpected value: " + type);
        }

    }

    private static Task getTodo(String line, int tagIndex, boolean isDone) {
        //if tagIndex is -1(null) means there is no tag
        String description = (tagIndex != -1) ?
                line.substring(7, tagIndex).trim() : line.substring(7).trim();
        Task todo = new Todo(description);
        if (isDone) {
            todo.markAsDone();
        }
        if (tagIndex != -1) {
            String tag = line.substring(tagIndex).trim();
            todo.addTag(tag);
        }
        return todo;
    }

    private static Task getDeadline(String line, int tagIndex, boolean isDone) {
        int byIndex = line.indexOf("(by: ");
        //if there is a tag, the description's end index will be the tagIndex. Else if no tag will be byIndex.
        String description = (tagIndex != -1) ?
                line.substring(7, tagIndex).trim() : line.substring(7, byIndex).trim();
        String by = line.substring(byIndex + 5, line.indexOf(')', byIndex)).trim();
        Task deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        if (tagIndex != -1) {
            String tag = line.substring(tagIndex,byIndex).trim();
            deadline.addTag(tag);
        }
        return deadline;
    }

    private static Task getEvent(String line, int tagIndex, boolean isDone) {
        int fromIndex = line.indexOf("(from: ");
        //if there is a tag, the description's end index will be the tagIndex. Else if no tag will be fromIndex.
        String description = (tagIndex != -1) ?
                line.substring(7, tagIndex).trim() : line.substring(7, fromIndex).trim();
        int toIndex = line.indexOf("to: ");
        String from = line.substring(fromIndex + 7, line.indexOf("to:", fromIndex)).trim();
        String to = line.substring(toIndex + 4, line.indexOf(')', fromIndex)).trim();
        Task event = new Event(description, from, to);
        if (isDone) {
            event.markAsDone();
        }
        if (tagIndex != -1) {
            String tag = line.substring(tagIndex,fromIndex).trim();
            event.addTag(tag);
        }
        return event;
    }

    /**
     * Checks for duplicates in the current task list.
     *
     * @param arguments Contains the description of the task to be compared to the tasks in the task list.
     * @return True if the task already exists in the list. If not, return false
     */
    public boolean isDuplicate(String arguments) {
        for (Task task : taskList.getAllTasks()) {
            if (task.getDescription().equals(arguments)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parse the input from the user to interpret the command to be executed.
     *
     * @param input The input by the user
     * @return The command to be executed.
     * @throws DukeException When the user inputs an invalid command, missing input or incorrect format.
     */

    //grouping white spaces as a delimiter adapted from https://stackoverflow.com/a/225360
    public Command parseInput(String input) throws DukeException {
        if(input.isBlank()) {
            throw new DukeException("Input cannot be empty. Please enter a command.");
        }
        String[] parts = input.trim().split("\\s+", 2);
        CommandType commandType = Command.commands(parts[0]);
        //use ternary operator to check if there is a 2nd part, if there is, assign argument to parts[1]
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandType) {
        case TODO:
            if (arguments.isBlank()) {
                throw new DukeException("""
                        Input task is empty. Please follow the input format in the menu.
                        e.g. "todo read book\"""");
            } else if (isDuplicate(arguments)) {
                throw new DukeException("This task already exists in the list.");
            }
            return new AddTodoCommand(arguments);
        case DEADLINE:
            if (arguments.isBlank()) {
                throw new DukeException("""
                        Input task is empty. Please follow the input format in the menu.
                        e.g. "deadline read book /by 01-01-2020 00:00"\"""");
            } else if (!arguments.contains("/by")) {
                throw new DukeException("""
                        Invalid format. Please follow the input format in the menu.
                        e.g. "deadline read book /by 01-01-2020 00:00\"""");
            }

            String[] deadlineParts = arguments.split("/by", 2);

            if (deadlineParts.length < 2 || deadlineParts[0].isBlank() || deadlineParts[1].isBlank()) {
                throw new DukeException("""
                        Input task or date is empty. Please follow the input format in the menu.
                        e.g. "deadline read book /by 01-01-2020 00:00\"""");
            } else if (isDuplicate(deadlineParts[0].trim())) {
                throw new DukeException("This task already exists in the list.");
            }

            String byDate = DateParser.formatDateInput(deadlineParts[1].trim());

            return new AddDeadlineCommand(deadlineParts[0].trim(), byDate);
        case EVENT:
            if (arguments.isBlank()) {
                throw new DukeException("""
                        Input task is empty. Please follow the input format in the menu.
                        e.g. "event book sale /from 01-01-2020 00:00 /to 01-01-2020 00:00\"""");
            } else if (!(arguments.contains("/from") && arguments.contains("/to"))) {
                throw new DukeException("""
                        Invalid format. Please follow the input format in the menu.
                        e.g. "event book sale /from 01-01-2020 00:00 /to 01-01-2020 00:00\"""");
            }

            int fromIndex = arguments.indexOf("/from");
            int toIndex = arguments.indexOf("/to");
            String description = arguments.substring(0, fromIndex).trim();
            String from = arguments.substring(fromIndex + 5, toIndex).trim();
            String to = arguments.substring(toIndex + 3).trim();


            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new DukeException("""
                        Invalid format. Please follow the input format in the menu.
                        e.g. "event book sale /from 01-01-2020 00:00 /to 01-01-2020 00:00\"""");
            } else if (isDuplicate(description.trim())) {
                throw new DukeException("This task already exists in the list.");
            }

            String fromDate = DateParser.formatDateInput(from);
            String toDate = DateParser.formatDateInput(to);
            if (!DateParser.isBefore(fromDate, toDate)) {
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
                throw new DukeException("Index does not exist. Please enter a valid index.");
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
                    throw new DukeException("Index does not exist. Please enter a valid index.");
                } else if (task.getStatusIcon().equals("X")) {
                    throw new DukeException("Task is currently marked already.");
                }
                return new MarkCommand(getIndex);

            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Index does not exist. Please enter a valid index.");
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
                    throw new DukeException("Index does not exist. Please enter a valid index.");
                } else if (task.getStatusIcon().trim().isEmpty()) {
                    throw new DukeException("Task is currently unmarked already.");
                }
                return new UnmarkCommand(getIndex);

            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Index does not exist. Please enter a valid index.");
            }
        case LIST:
            if (taskList.getSize() == 0) {
                throw new DukeException("List is empty. Please add a task first.");
            }
            return new ListCommand();
        case FIND:
            if (taskList.getSize() == 0) {
                throw new DukeException("Task list is currently empty. Please add a task first.");
            } else if (arguments.isBlank()) {
                throw new DukeException("Keyword to search is empty. Please enter a keyword.");
            }
            return new FindCommand(arguments);
        case TAG:
            if (taskList.getSize() == 0) {
                throw new DukeException("Task list is currently empty. Please add a task first.");
            }
            String[] argumentParts = arguments.trim().split("\\s+", 2);
            if (argumentParts.length < 2 || !argumentParts[1].contains("#")) {
                throw new DukeException("""
                        Invalid format. Please follow the input format in the menu.
                        e.g. "tag 1 #fun\"""");
            }

            try {

            int index = Integer.parseInt(argumentParts[0]) - 1;
            Task task = taskList.getTask(index);
            String tag = argumentParts[1].trim();

            if(index < 0 || index >= taskList.getSize()) {
                throw new DukeException("Index does not exist. Please enter a valid index.");
            } else if (task.hasTag()) {
                throw new DukeException("This task is already tagged.");
            }

            return new TagCommand(index, tag);

            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Index does not exist. Please enter a valid index.");
            }

        case UNTAG:
            if (taskList.getSize() == 0) {
                throw new DukeException("Task list is currently empty. Please add a task first.");
            } else if (arguments.isBlank()) {
                throw new DukeException("No index found. Please input the index of the task"
                        + " you want to untag.");
            }

            try {
                int index = Integer.parseInt(arguments) - 1;
                Task task = taskList.getTask(index);
                String tag = task.getTag();

                if(index < 0 || index >= taskList.getSize()) {
                    throw new DukeException("Index does not exist. Please enter a valid index.");
                } else if (tag == null) {
                    throw new DukeException("This task does not have a tag to untag.");
                }

                return new UntagCommand(index, tag);

            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Index does not exist. Please enter a valid index.");
            }
        case BYE:
            return new ExitCommand();
        default:
            throw new DukeException("Invalid command. Please try again."
                    + " Remember to leave a space after each command.");

        }
    }
}
