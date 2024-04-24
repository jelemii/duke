package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void deadlineTest(){
        String description = "Clean room";
        String by = "May 01 2024 23:59pm";
        Deadline task = new Deadline(description, by);
        assertEquals("[D][ ] Clean room (by: May 01 2024 23:59pm)",task.toString());

        task.markAsDone();
        assertEquals("[D][X] Clean room (by: May 01 2024 23:59pm)",task.toString());

        task.unmarkAsDone();
        assertEquals("[D][ ] Clean room (by: May 01 2024 23:59pm)",task.toString());
    }
}
