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

class StringOpNodeTest {

    @ParameterizedTest(name = "should process {2}")
    @CsvSource({
            "aa, a, same elements",
            "abc, bc, multiple elements",
    })
    void shouldRemoveFirstElement(ArgumentsAccessor arguments) {
        processSingleOpAssertion(arguments, new StringOpNode(0, "Stern"));
    }

    @ParameterizedTest(name = "should process {2}")
    @CsvSource({
            "aa, a, same elements",
            "abc, a, multiple elements",
    })
    void shouldRemoveLastElement(ArgumentsAccessor arguments) {
        processSingleOpAssertion(arguments, new StringOpNode(0, "Stem"));
    }

    private void processSingleOpAssertion(ArgumentsAccessor arguments, Node node) {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "'" + arguments.getString(0) + "'", SymbolDictionary.Symbol.STRING));
        }};

        try {
            node.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals(arguments.getString(1), stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldReturnAnErrorForInvalid() {
        Node node = new StringOpNode(0, "-");

        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "'1'", SymbolDictionary.Symbol.INTEGER));
            push(new DataNode(1, "'w'", SymbolDictionary.Symbol.STRING));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            node.execute(stack);
        });
    }
}