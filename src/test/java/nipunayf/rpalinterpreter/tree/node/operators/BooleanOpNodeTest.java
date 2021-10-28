package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
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
        processIntegerOpAssertion(arguments, new BooleanOpNode(0, "or", SymbolDictionary.Symbol.OPERATOR));
    }

    @ParameterizedTest(name = "should process {3} numbers")
    @CsvSource({
            "true, true, true, both true",
            "true, false, false, true and false",
            "false, false, false, both false",
    })
    void shouldProcessAND(ArgumentsAccessor arguments) {
        processIntegerOpAssertion(arguments, new BooleanOpNode(0, "&", SymbolDictionary.Symbol.OPERATOR));
    }

    private void processIntegerOpAssertion(ArgumentsAccessor arguments, Node node) {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, arguments.getString(1), SymbolDictionary.Symbol.INTEGER));
            push(new DataNode(1, arguments.getString(0), SymbolDictionary.Symbol.INTEGER));
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
    void shouldReturnAnErrorForNonInteger() {
        Node node = new BooleanOpNode(0, "-", SymbolDictionary.Symbol.OPERATOR);

        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "w", SymbolDictionary.Symbol.IDENTIFIER));
            push(new DataNode(1, "w", SymbolDictionary.Symbol.IDENTIFIER));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            node.execute(stack);
        });
    }
}