package gojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void testParseCommand_standardCommands() throws ChatbotExceptions {
        assertEquals(Command.TODO, Parser.parseCommand("todo read book"));
        assertEquals(Command.DEADLINE, Parser.parseCommand("deadline submit assignment"));
        assertEquals(Command.EVENT, Parser.parseCommand("event meeting"));
        assertEquals(Command.LIST, Parser.parseCommand("list"));
        assertEquals(Command.BYE, Parser.parseCommand("bye"));
        assertEquals(Command.MARK, Parser.parseCommand("mark 1"));
        assertEquals(Command.UNMARK, Parser.parseCommand("unmark 1"));
        assertEquals(Command.DELETE, Parser.parseCommand("delete 1"));
        assertEquals(Command.SCHEDULE, Parser.parseCommand("schedule 2023-10-10"));
        assertEquals(Command.FIND, Parser.parseCommand("find book"));
    }

    @Test
    public void testParseCommand_caseInsensitivity() throws ChatbotExceptions {
        assertEquals(Command.TODO, Parser.parseCommand("TODO read book"));
        assertEquals(Command.TODO, Parser.parseCommand("ToDo read book"));
        assertEquals(Command.DEADLINE, Parser.parseCommand("DEADLINE submit"));
        assertEquals(Command.LIST, Parser.parseCommand("List"));
    }

    @Test
    public void testParseCommand_aliases() throws ChatbotExceptions {
        assertEquals(Command.TODO, Parser.parseCommand("t read book"));
        assertEquals(Command.DEADLINE, Parser.parseCommand("d submit"));
        assertEquals(Command.EVENT, Parser.parseCommand("e meeting"));
        assertEquals(Command.LIST, Parser.parseCommand("l"));
        assertEquals(Command.BYE, Parser.parseCommand("b"));
        assertEquals(Command.BYE, Parser.parseCommand("quit"));
        assertEquals(Command.MARK, Parser.parseCommand("m 1"));
        assertEquals(Command.UNMARK, Parser.parseCommand("u 1"));
        assertEquals(Command.DELETE, Parser.parseCommand("del 1"));
        assertEquals(Command.DELETE, Parser.parseCommand("- 1"));
    }

    @Test
    public void testParseCommand_invalidCommand() {
        assertThrows(ChatbotExceptions.class, () -> Parser.parseCommand("blah"));
        assertThrows(ChatbotExceptions.class, () -> Parser.parseCommand(""));
    }
}
