public class AddTodoCommand {
    public static void addTodo(String input){
        Task task = new Todo(input);
        TaskList.addTask(task);
        Ui.showTaskAdded(task, TaskList.getSize());
    }
}
