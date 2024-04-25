package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TaskListTest {
    private TaskList tasks;

    public TaskListTest() {
        this.tasks = new TaskList();
    }

    @Test
    public void testAddTask() {
        Todo todo = new Todo("test add todo");
        tasks.addTask(todo);
        assertEquals(todo, tasks.getTask(0));

        Deadline deadline = new Deadline("test add deadline", "Apr 30 2024 12:00pm");
        tasks.addTask(deadline);
        assertEquals(deadline, tasks.getTask(1));

        Event event = new Event("test add event", "Apr 01 2024 12:00pm" , "Apr 02 2024 12:00pm");
        tasks.addTask(event);
        assertEquals(event, tasks.getTask(2));
        //test getSize()
        assertEquals(3, tasks.getSize());
    }

    @Test
    public void testDeleteTask() {
        Todo todo = new Todo("test delete task");
        tasks.addTask(todo);
        assertEquals(1, tasks.getSize());
        tasks.deleteTask(0);
        assertEquals(0, tasks.getSize());
    }

}
