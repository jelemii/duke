package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EventTest {
    @Test
    public void eventTest(){
        String description = "Clean room";
        String from = "May 01 2024 12:00pm";
        String to = "May 01 2024 23:59pm";
        Event task = new Event(description, from, to);
        assertEquals("[E][ ] Clean room (from: May 01 2024 12:00pm " +
                "to: May 01 2024 23:59pm)",task.toString());

        task.markAsDone();
        assertEquals("[E][X] Clean room (from: May 01 2024 12:00pm " +
                "to: May 01 2024 23:59pm)",task.toString());

        task.unmarkAsDone();
        assertEquals("[E][ ] Clean room (from: May 01 2024 12:00pm " +
                "to: May 01 2024 23:59pm)",task.toString());

        String tag = "#important";
        task.addTag(tag);
        assertEquals("[E][ ] Clean room #important (from: May 01 2024 12:00pm " +
                "to: May 01 2024 23:59pm)",task.toString());
        assertTrue(task.hasTag());
        assertEquals("#important",task.getTag());

        task.unTag();
        assertEquals("[E][ ] Clean room (from: May 01 2024 12:00pm " +
                "to: May 01 2024 23:59pm)",task.toString());
        assertFalse(task.hasTag());
        assertNull(task.getTag());
    }
}
