import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    protected static ArrayList<Task> taskList = new ArrayList<>();
  //  protected static int index = 0;

    public static void handleTodo(String input) throws DukeException {
        if(input.substring(4).trim().isEmpty()) {
            throw new DukeException("Input task is empty. Input the task you want to add " +
                    "after the \"todo\" command. e.g. \"todo task\"");
        }
        else if(input.charAt(4) != ' ') {
            throw new DukeException("Invalid format. Please add a space after the \"todo\" command.");
        }
        else {
            String desc = input.substring(5).trim();
            checkDuplicate(desc);
        }
        addTodo(input);
    }
    public static void addTodo(String input){

       taskList.add(taskList.size(), new Todo(input));
       System.out.println("Got it. I've added this task:");
       System.out.println((taskList.size()) + ". " + taskList.get(taskList.size() - 1).toString());
       System.out.println("Now you have " + taskList.size() + " tasks in the list.");
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
        addDeadline(desc,by);
    }
    public static void addDeadline(String desc, String by) {
        taskList.add(taskList.size(), new Deadline(desc, by));
        System.out.println("Got it. I've added this task:");
        System.out.println((taskList.size()) + ". " + taskList.get(taskList.size() - 1).toString());
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
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
        addEvent(desc, from, to);
    }
    public static void addEvent(String desc, String from, String to) {
        taskList.add(taskList.size(), new Event(desc, from, to));
        System.out.println("Got it. I've added this task:");
        System.out.println((taskList.size()) + ". " + taskList.get(taskList.size() - 1).toString());
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
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
            if(getIndex < 0 || getIndex >= taskList.size()) {
                throw new IndexOutOfBoundsException();
            }

            System.out.println("Noted. I've removed this task:");
            System.out.println((getIndex + 1) + ". " + taskList.get(getIndex).toString());
            taskList.remove(getIndex);
            System.out.println("Now you have " + taskList.size() + " tasks in the list.");

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Index does not exist. ");
            showList();
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
            if(getIndex >= 0 && getIndex < taskList.size()) {
                Task task = taskList.get(getIndex);
                if(task.getStatusIcon().equals("X")){
                    throw new DukeException("Task has already been marked.");
                }
                task.markAsDone();
                System.out.println("Nice! I've marked this task as done: ");
                System.out.println((taskList.indexOf(task) + 1) + ". " + taskList.get(getIndex).toString());
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Index does not exist. ");
            showList();
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
            if (getIndex >= 0 && getIndex < taskList.size()) {
                Task task = taskList.get(getIndex);
                if(!task.getStatusIcon().equals("X")){
                    throw new DukeException("Task is currently unmarked already.");
                }
                task.unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet: ");
                System.out.println((taskList.indexOf(task) + 1) + ". " + taskList.get(getIndex).toString());
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Index does not exist. ");
            showList();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkIfEmpty() throws DukeException {
        if(taskList.isEmpty()) {
            throw new DukeException("List is empty. Please add a task first.");
        }
    }
    public static void checkDuplicate(String desc) throws DukeException {
        for (Task task : taskList) {
            if (task.description.equals(desc)) {
                throw new DukeException("Task already exists in the list.");
            }
        }
    }
    public static void showList(){
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i+1) + ". " + taskList.get(i).toString());
        }
    }
    private static void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }
    public static void main(String[] args) {
        String greetUser = "Hello! I'm jelemiiBot\n"
                + "What can I do for you?\n";
        System.out.println(greetUser);

        String file2 = "temp/lines.txt";
        try {
            writeToFile(file2, "first line" + System.lineSeparator() + "second line");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        boolean getInput = true;
        Scanner in = new Scanner(System.in);


        while (getInput) {
            String input = in.nextLine().trim().toLowerCase();
            try {
                if (input.equals("list")) {
                    checkIfEmpty();
                    showList();

                }
                else if(input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    getInput = false;
                }
                else if(input.startsWith("mark")){
                    markTask(input);
                }
                else if(input.startsWith("unmark")){
                    unmarkTask(input);
                }
                else if(input.startsWith("todo")) {
                    handleTodo(input);
                }
                else if(input.startsWith("deadline")) {
                    handleDeadLine(input);
                }
                else if(input.startsWith("event")) {
                   handleEvent(input);
                }
                else if(input.startsWith("delete")) {
                    handleDeletion(input);
                }
                else {
                    throw new DukeException("Please input a valid command to add new task " +
                            "or mark/unmark a current task.");
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
