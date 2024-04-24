package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void toDoTest(){
        String description = "Clean room";
        Todo task = new Todo(description);
        assertEquals("[T][ ] Clean room",task.toString());

        task.markAsDone();
        assertEquals("[T][X] Clean room",task.toString());

        task.unmarkAsDone();
        assertEquals("[T][ ] Clean room",task.toString());
    }
}
