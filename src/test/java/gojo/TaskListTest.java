package gojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

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

    @Test
    public void isDuplicate_duplicateTask_returnsTrue() {
        TaskList taskList = new TaskList();
        Task t1 = new Todo("read book");
        taskList.add(t1);
        Task t2 = new Todo("read book");
        assertEquals(true, taskList.isDuplicate(t2));
    }

    @Test
    public void isDuplicate_differentTask_returnsFalse() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));
        assertEquals(false, taskList.isDuplicate(new Todo("write code")));
    }

    @Test
    public void delete_validIndex_removesTask() throws ChatbotExceptions {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));
        assertEquals(1, taskList.size());

        taskList.delete(0);
        assertEquals(0, taskList.size());
    }
}
