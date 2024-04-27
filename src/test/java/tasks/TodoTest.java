package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        String tag = "#important";
        task.addTag(tag);
        assertEquals("[T][ ] Clean room #important",task.toString());
        assertTrue(task.hasTag());
        assertEquals("#important",task.getTag());

        task.unTag();
        assertEquals("[T][ ] Clean room",task.toString());
        assertFalse(task.hasTag());
        assertNull(task.getTag());
    }
}
