package nipunayf.rpalinterpreter.csemachine.environment;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {
    @Test
    void shouldConstrueIdentifierInCurrentEnv() {
        Node identifier = new DataNode(0, "x", DataDictionary.Data.IDENTIFIER);
        DataNode value = new DataNode(0, "3", DataDictionary.Data.INTEGER);

        Environment pe = new PreliminaryEnvironment(new HashMap<>());
        Environment ce = new ExtendingEnvironment(new HashMap<>() {{
            put(identifier.getValue(), value);
        }}, pe);

        assertEquals(ce.construe(identifier), value);
    }

    @Test
    void shouldConstrueIdentifierIn2ndAncestor() {
        Node identifier = new DataNode(0, "x", DataDictionary.Data.IDENTIFIER);
        DataNode value = new DataNode(0, "3", DataDictionary.Data.INTEGER);

        Environment pe = new PreliminaryEnvironment(new HashMap<>());
        Environment ce = new ExtendingEnvironment(new HashMap<>() {{
            put(identifier.getValue(), value);
        }}, pe);
        Environment ce2 = new ExtendingEnvironment(new HashMap<>(), ce);

        assertEquals(ce2.construe(identifier), value);
    }

    @Test
    void shouldConstrueIdentifierInPreliminaryEnv() {
        Node identifier = new DataNode(0, "x", DataDictionary.Data.IDENTIFIER);
        DataNode value = new DataNode(0, "3", DataDictionary.Data.INTEGER);

        Environment pe = new PreliminaryEnvironment(new HashMap<>() {{
            put(identifier.getValue(), value);
        }});
        Environment ce = new ExtendingEnvironment(new HashMap<>(), pe);

        assertEquals(ce.construe(identifier), value);
    }

    @Test
    void shouldPrioritizeIdentifierInCurrentEnv() {
        Node identifier = new DataNode(0, "x", DataDictionary.Data.IDENTIFIER);
        DataNode value = new DataNode(0, "3", DataDictionary.Data.INTEGER);
        DataNode peValue = new DataNode(0, "4", DataDictionary.Data.INTEGER);

        Environment pe = new PreliminaryEnvironment(new HashMap<>() {{
            put(identifier.getValue(), peValue);
        }});
        Environment ce = new ExtendingEnvironment(new HashMap<>() {{
            put(identifier.getValue(), value);
        }}, pe);

        assertEquals(ce.construe(identifier), value);
    }

    @Test
    void shouldNotConstrueIdentifierInSiblingEnv() {
        Node identifier = new DataNode(0, "x", DataDictionary.Data.IDENTIFIER);
        DataNode value = new DataNode(0, "3", DataDictionary.Data.INTEGER);

        Environment pe = new PreliminaryEnvironment(new HashMap<>());
        Environment ce = new ExtendingEnvironment(new HashMap<>() {{
            put(identifier.getValue(), value);
        }}, pe);
        Environment ce2 = new ExtendingEnvironment(new HashMap<>(), pe);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            ce2.construe(identifier);
        });
    }
}