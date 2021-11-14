package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class NegNodeTest {
    Node negNode;

    @BeforeEach
    void setUp() {
        negNode = new NegNode(0);
    }


    @ParameterizedTest(name = "should swap the sign of a {2} number")
    @CsvSource({
            "1, -1, positive",
            "-1, 1, negative",
    })
    void shouldSwapTheSign(ArgumentsAccessor arguments) {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, arguments.getString(0) , DataDictionary.Symbol.INTEGER));
        }};

        try {
            negNode.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals(arguments.getString(1), stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldNotSwapTheSignOf0() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "0", DataDictionary.Symbol.INTEGER));
        }};

        try {
            negNode.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals("0", stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldReturnAnErrorForNonInteger() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "w", DataDictionary.Symbol.IDENTIFIER));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            negNode.execute(stack);
        });
    }
}