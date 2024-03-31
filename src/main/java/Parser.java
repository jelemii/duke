public class Parser {
    public static Task parseTaskFromFile(String line) {
        String type = line.substring(1, 2);
        boolean isDone = line.charAt(4) == 'X';

        switch (type) {
            case "T" -> {
                String description = line.substring(7);
                Task todo = new Todo(description);
                if (isDone){
                    todo.markAsDone();
                }
                return todo;
            }
            case "D" -> {
                int descEndIndex = line.indexOf(' ', 7);
                String description = line.substring(7, descEndIndex);
                int byIndex = line.indexOf("(by: ");
                String by = line.substring(byIndex + 5, line.indexOf(')', byIndex));
                Task deadline = new Deadline(description,by);
                if (isDone){ deadline.markAsDone(); }
                return deadline;
            }
            case "E" -> {
                int descEndIndex = line.indexOf(' ', 7);
                String description = line.substring(7, descEndIndex);
                int fromIndex = line.indexOf("(from: ");
                int toIndex = line.indexOf("to: ");
                String from = line.substring(fromIndex + 7, line.indexOf("to:", fromIndex));
                String to = line.substring(toIndex + 4, line.indexOf(')', fromIndex));
                Task event = new Event(description,from,to);
                if (isDone){ event.markAsDone(); }
                return event;
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }

    }

    public static void parseInput(String input)  {
       try{
           if (input.equals("list")) {
               HandleCommandExceptions.checkIfEmpty();
               Ui.showList();
           }
           else if(input.startsWith("mark")){
               HandleCommandExceptions.markTask(input);
           }
           else if(input.startsWith("unmark")){
               HandleCommandExceptions.unmarkTask(input);
           }
           else if(input.startsWith("todo")) {
               HandleCommandExceptions.handleTodo(input);
               AddTodoCommand.addTodo(input.substring(5));
           }
           else if(input.startsWith("deadline")) {
               HandleCommandExceptions.handleDeadLine(input);
               AddDeadlineCommand.addDeadline(input);
           }
           else if(input.startsWith("event")) {
               HandleCommandExceptions.handleEvent(input);
               AddEventCommand.addEvent(input);
           }
           else if(input.startsWith("delete")) {
               HandleCommandExceptions.handleDeletion(input);
           }
           else {
               throw new DukeException("Please input a valid command to add new task " +
                       "or mark/unmark a current task.");
           }
       } catch(DukeException e) {
           System.out.println(e.getMessage());
    }
    }
}
