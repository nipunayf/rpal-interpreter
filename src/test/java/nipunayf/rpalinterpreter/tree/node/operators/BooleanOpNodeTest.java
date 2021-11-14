package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class BooleanOpNodeTest {
    @ParameterizedTest(name = "should process {3} booleans")
    @CsvSource({
            "true, true, true, both true",
            "true, false, true, true and false",
            "false, false, false, both false",
    })
    void shouldProcessOR(ArgumentsAccessor arguments) {
        processBooleanOpAssertion(arguments, new BooleanOpNode(0, "or"));
    }

    @ParameterizedTest(name = "should process {3} numbers")
    @CsvSource({
            "true, true, true, both true",
            "true, false, false, true and false",
            "false, false, false, both false",
    })
    void shouldProcessAND(ArgumentsAccessor arguments) {
        processBooleanOpAssertion(arguments, new BooleanOpNode(0, "&"));
    }

    private void processBooleanOpAssertion(ArgumentsAccessor arguments, Node node) {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, arguments.getString(1), DataDictionary.Data.BOOLEAN));
            push(new DataNode(1, arguments.getString(0), DataDictionary.Data.BOOLEAN));
        }};

        try {
            node.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals(arguments.getString(2), stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldReturnAnErrorForNonBoolean() {
        Node node = new BooleanOpNode(0, "-");

        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "w", DataDictionary.Data.IDENTIFIER));
            push(new DataNode(1, "w", DataDictionary.Data.IDENTIFIER));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            node.execute(stack);
        });
    }
}