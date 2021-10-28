package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
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

class NotNodeTest {
    Node notNode;

    @BeforeEach
    void setUp() {
        notNode = new NotNode(0, "not", SymbolDictionary.Symbol.OPERATOR);
    }


    @ParameterizedTest(name = "should swap {0} boolean")
    @CsvSource({
            "true, false",
            "false, true"
    })
    void shouldSwapTheBooleanValue(ArgumentsAccessor arguments) {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, arguments.getString(0) , SymbolDictionary.Symbol.BOOLEAN));
        }};

        try {
            notNode.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals(arguments.getString(1), stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldReturnAnErrorForNonBoolean() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "w", SymbolDictionary.Symbol.IDENTIFIER));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            notNode.execute(stack);
        });
    }
}