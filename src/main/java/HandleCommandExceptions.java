public class HandleCommandExceptions {
    public static void handleTodo(String input) throws DukeException {
        if(input.substring(4).trim().isEmpty()) {
            throw new DukeException("Input task is empty. Input the task you want to add " +
                    "after the \"todo\" command. e.g. \"todo task\"");
        }
        else if(input.charAt(4) != ' ') {
            throw new DukeException("Invalid format. Please add a space after the \"todo\" command.");
        }
        else {
            String desc = input.substring(5);
            checkDuplicate(desc);
        }
    }
    public static void handleDeadLine(String input) throws DukeException {
        if(input.substring(8).trim().isEmpty()) {
            throw new DukeException("Input task is empty. Input the task you want to add " +
                    "after the \"deadline\" command.\ne.g. \"deadline task /by date\"");
        }
        else if (input.charAt(8) != ' ') {
            throw new DukeException("Invalid format. Please add a space after the \"deadline\" " +
                    "command.");
        }
        else if(!input.contains("/by")) {
            throw new DukeException("Invalid format. Please add a \"/by\" for deadline tasks.");
        }

        int byIndex = input.indexOf("/by");
        String desc = input.substring(9, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();


        if(desc.isEmpty() || by.isEmpty()) {
            throw new DukeException("Task or date is empty. Input a task and a date for the \n" +
                    "deadline you want after the \"/by\" command.\ne.g. \"deadline task /by date\"");
        }

        else if (!input.substring(byIndex+3).startsWith(" ") || input.charAt(byIndex - 1) != ' ') {
            throw new DukeException("Invalid format. Please add a space between the \"/by\" command.");
        }
        else {
            checkDuplicate(desc);
        }
    }
    public static void handleEvent(String input) throws DukeException {
        if(input.substring(5).trim().isEmpty()) {
            throw new DukeException("Input task is empty. Input the task you want to add " +
                    "after the \"event\" command" +
                    "\ne.g. \"event task /from start-date /to end-date\"");
        }
        else if (input.charAt(5) != ' ') {
            throw new DukeException("Invalid format. Please add a space after the \"event\" " +
                    "command.");
        }
        else if(!(input.contains("/from") && input.contains("/to"))) {
            throw new DukeException("Invalid format. Please add a \"/from\" and \"/to\" " +
                    "for event tasks.");
        }

        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        String desc = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();


        if(desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new DukeException("""
                    Task or date is empty. Input a task and 2 dates for the\s
                    start-date and end-date you want for the task after the "/event" command.
                     e.g. "event task /from start-date /to end-date\"""");
        }

        else if ((!input.substring(fromIndex+5).startsWith(" ") || input.charAt(fromIndex - 1) != ' ') ||
                (!input.substring(toIndex+3).startsWith(" ") || input.charAt(toIndex - 1) != ' ')) {
            throw new DukeException("Invalid format. Please add a space between the \"/from\" and \"/to\" command.");
        }

        else {
            checkDuplicate(desc);
        }
    }
    public static void handleDeletion(String input) {
        try {
            if(input.substring(6).trim().isEmpty()) {
                throw new DukeException("No index found. Please input the index of the task" +
                        " you want to delete.");
            }
            else if (input.charAt(6) != ' ') {
                throw new DukeException("Invalid format. Please add a space after the \"delete\" " +
                        "command.");
            }
            int getIndex = (Integer.parseInt(input.substring(7))) - 1;
            if(getIndex < 0 || getIndex >= TaskList.getSize()) {
                throw new IndexOutOfBoundsException();
            }
            DeleteCommand.deleteTask(getIndex);


        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Index does not exist. ");
            Ui.showList();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void markTask(String input) {
        try {
            checkIfEmpty();
            if(input.substring(4).trim().isEmpty()) {
                throw new DukeException("Please input a number.");
            }
            else if(input.charAt(4) != ' ') {
                throw new DukeException("Invalid format. Please add a space after the command.");
            }
            int getIndex = (Integer.parseInt(input.substring(5))) - 1;
            if(getIndex >= 0 && getIndex < TaskList.tasks.size()) {
                Task task = TaskList.tasks.get(getIndex);
                if(task.getStatusIcon().equals("X")){
                    throw new DukeException("Task has already been marked.");
                }
                MarkCommand.markTask(TaskList.tasks.get(getIndex));
                Ui.showTaskMarked(TaskList.tasks.get(getIndex));
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Index does not exist. ");
            Ui.showList();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void unmarkTask(String input){
        try {
            checkIfEmpty();
            if(input.substring(6).trim().isEmpty()) {
                throw new DukeException("Please input a number.");
            }
            if(input.charAt(6) != ' ') {
                throw new DukeException("Invalid format. Please add a space after the command.");
            }
            int getIndex = (Integer.parseInt(input.substring(7))) - 1;
            if (getIndex >= 0 && getIndex < TaskList.tasks.size()) {
                Task task = TaskList.tasks.get(getIndex);
                if(!task.getStatusIcon().equals("X")){
                    throw new DukeException("Task is currently unmarked already.");
                }
                UnmarkCommand.unmarkTask(TaskList.tasks.get(getIndex));
                Ui.showTaskUnmarked(TaskList.tasks.get(getIndex));
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Index does not exist. ");
            Ui.showList();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkIfEmpty() throws DukeException {
        if(TaskList.tasks.isEmpty()) {
            throw new DukeException("List is empty. Please add a task first.");
        }
    }
    public static void checkDuplicate(String desc) throws DukeException {
        for (Task task : TaskList.tasks) {
            if (task.description.equals(desc)) {
                throw new DukeException("Task already exists in the list.");
            }
        }
    }
}
