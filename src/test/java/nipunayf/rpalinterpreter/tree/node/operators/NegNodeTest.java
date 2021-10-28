package nipunayf.rpalinterpreter.tree.node.operators;

import com.sun.jdi.InvalidTypeException;
import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class NegNodeTest {
    Node negNode;

    @BeforeEach
    void setUp() {
        negNode = new NegNode(0, "neg", SymbolDictionary.Symbol.OPERATOR);
    }

    @Test
    void shouldMakeNegativeAPositiveValue() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "3", SymbolDictionary.Symbol.INTEGER));
        }};

        try {
            negNode.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals("-3", stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldMakePositiveANegativeValue() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "-3", SymbolDictionary.Symbol.INTEGER));
        }};

        try {
            negNode.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals("3", stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldReturnAnErrorForNonInteger() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "w", SymbolDictionary.Symbol.IDENTIFIER));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            negNode.execute(stack);
        });
    }
}