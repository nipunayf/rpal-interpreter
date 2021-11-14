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

class UniversalOpNodeTest {
    @ParameterizedTest(name = "should return {2} for {3} operator")
    @CsvSource({
            "1, 1, true, equal, eq",
            "1, 2, false, equal, eq",
            "1, 2, true, not equal, ne",
            "1, 1, false, not equal, ne",
    })
    void shouldProcessIntegers(ArgumentsAccessor arguments) {
        processUniversalOpAssertion(arguments, new UniversalOpNode(0, arguments.getString(4)), DataDictionary.Data.INTEGER);
    }

    @ParameterizedTest(name = "should return {2} for {3} operator")
    @CsvSource({
            "true, true, true, equal, eq",
            "true, false, false, equal, eq",
            "true, false, true, not equal, ne",
            "false, false, false, not equal, ne",
    })
    void shouldProcessBooleans(ArgumentsAccessor arguments) {
        processUniversalOpAssertion(arguments, new UniversalOpNode(0, arguments.getString(4)), DataDictionary.Data.BOOLEAN);
    }

    @ParameterizedTest(name = "should return {2} for {3} operator")
    @CsvSource({
            "xax, xax, true, equal, eq",
            "xax, xbx, false, equal, eq",
            "xax, xbx, true, not equal, ne",
            "xbx, xbx, false, not equal, ne",
    })
    void shouldProcessStrings(ArgumentsAccessor arguments) {
        processUniversalOpAssertion(arguments, new UniversalOpNode(0, arguments.getString(4)), DataDictionary.Data.STRING);
    }

    private void processUniversalOpAssertion(ArgumentsAccessor arguments, Node node, DataDictionary.Data type) {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, arguments.getString(1), type));
            push(new DataNode(1, arguments.getString(0), type));
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
    void shouldReturnAnErrorForInvalid() {
        Node node = new BooleanOpNode(0, "-");

        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "w", DataDictionary.Data.IDENTIFIER));
            push(new DataNode(1, "w", DataDictionary.Data.IDENTIFIER));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            node.execute(stack);
        });
    }

    @Test
    void shouldReturnAnErrorForMismatch() {
        Node node = new BooleanOpNode(0, "-");

        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "1", DataDictionary.Data.INTEGER));
            push(new DataNode(1, "'w'", DataDictionary.Data.STRING));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            node.execute(stack);
        });
    }
}