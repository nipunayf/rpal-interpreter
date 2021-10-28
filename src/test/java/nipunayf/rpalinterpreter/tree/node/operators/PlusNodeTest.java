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

class PlusNodeTest {
    Node plusNode;

    @BeforeEach
    void setUp() {
        plusNode = new PlusNode(0, "neg", SymbolDictionary.Symbol.OPERATOR);
    }

    @ParameterizedTest(name = "should process {3} numbers")
    @CsvSource({
            "1, 2, 3, both positive",
            "-1, -2, -3, both negative",
            "-1, 2, 1, negative and positive",
            "-1, 1, 0, negative and positive and both equal",
    })
    void shouldProcessBothPositive(ArgumentsAccessor arguments) {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, arguments.getString(0), SymbolDictionary.Symbol.INTEGER));
            push(new DataNode(1, arguments.getString(1), SymbolDictionary.Symbol.INTEGER));
        }};

        try {
            plusNode.execute(stack);
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
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "w", SymbolDictionary.Symbol.IDENTIFIER));
            push(new DataNode(1, "w", SymbolDictionary.Symbol.IDENTIFIER));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            plusNode.execute(stack);
        });
    }

}