package gojo;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void findTasks_matchingKeyword_returnsCorrectTasks() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("write code"));
        taskList.add(new Todo("return book"));

        List<Task> result = taskList.findTasks("book");
        assertEquals(2, result.size());
        assertEquals("[T][ ] read book", result.get(0).toString());
        assertEquals("[T][ ] return book", result.get(1).toString());
    }

    @Test
    public void findTasks_noMatch_returnsEmptyList() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));

        List<Task> result = taskList.findTasks("gym");
        assertEquals(0, result.size());
    }
}
