package gojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parseCommand_validCommands_success() throws ChatbotExceptions {
        assertEquals(Command.TODO, Parser.parseCommand("todo read book"));
        assertEquals(Command.DEADLINE, Parser.parseCommand("deadline submit report /by ..."));
        assertEquals(Command.EVENT, Parser.parseCommand("event meeting /from ..."));
        assertEquals(Command.LIST, Parser.parseCommand("list"));
        assertEquals(Command.BYE, Parser.parseCommand("bye"));
    }

    @Test
    public void parseCommand_mixedCase_success() throws ChatbotExceptions {
        assertEquals(Command.TODO, Parser.parseCommand("ToDo read book"));
        assertEquals(Command.LIST, Parser.parseCommand("LiSt"));
    }

    @Test
    public void parseCommand_extraSpaces_success() throws ChatbotExceptions {
        assertEquals(Command.TODO, Parser.parseCommand("   todo    read book"));
    }

    @Test
    public void parseCommand_invalidCommand_throwsException() {
        assertThrows(ChatbotExceptions.class, () -> Parser.parseCommand("notacommand"));
    }

    @Test
    public void getArguments_validInput_returnsArguments() {
        assertEquals("read book", Parser.getArguments("todo read book"));
        assertEquals("submit report /by tomorrow", Parser.getArguments("deadline submit report /by tomorrow"));
    }

    @Test
    public void getArguments_extraSpaces_returnsTrimmedArguments() {
        // parser handles splitting by regex \s+, so arguments should be correctly
        // extracted
        // However, getArguments implementation: fullCommand.trim().split("\\s+", 2)
        // input: "todo read book"
        // split[0] = "todo"
        // split[1] = "read book"
        assertEquals("read book", Parser.getArguments("todo    read book"));
    }

    @Test
    public void parseIndex_validInput_success() throws ChatbotExceptions {
        // 1-based index 1 -> 0-based index 0
        assertEquals(0, Parser.parseIndex("1"));
        assertEquals(9, Parser.parseIndex("10"));
    }

    @Test
    public void parseIndex_invalidInput_throwsException() {
        assertThrows(ChatbotExceptions.class, () -> Parser.parseIndex("abc"));
        assertThrows(ChatbotExceptions.class, () -> Parser.parseIndex(""));
    }
}
