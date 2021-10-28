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

class ArithmeticOpNodeTest {
    Node minusNode;

    @ParameterizedTest(name = "should process {3} numbers")
    @CsvSource({
            "1, 2, 3, both positive",
            "-1, -2, -3, both negative",
            "-1, 2, 1, negative and positive",
            "-1, 1, 0, negative and positive and both equal",
    })
    void shouldProcessPlus(ArgumentsAccessor arguments) {
        processIntegerOpAssertion(arguments, new ArithmeticOpNode(0, "+", SymbolDictionary.Symbol.OPERATOR));
    }

    @ParameterizedTest(name = "should process {3} numbers")
    @CsvSource({
            "1, 2, -1, both positive",
            "-1, -2, 1, both negative",
            "-1, 2, -3, negative and positive",
            "-1, 1, -2, negative and positive and both equal",
    })
    void shouldProcessMinus(ArgumentsAccessor arguments) {
        processIntegerOpAssertion(arguments, new ArithmeticOpNode(0, "-", SymbolDictionary.Symbol.OPERATOR));
    }

    @ParameterizedTest(name = "should process {3} numbers")
    @CsvSource({
            "1, 2, 2, both positive",
            "-1, -2, 2, both negative",
            "-1, 2, -2, negative and positive",
            "-1, 1, -1, negative and positive and both equal",
    })
    void shouldProcessMultiplication(ArgumentsAccessor arguments) {
        processIntegerOpAssertion(arguments, new ArithmeticOpNode(0, "*", SymbolDictionary.Symbol.OPERATOR));
    }

    @ParameterizedTest(name = "should process {3} numbers")
    @CsvSource({
            "2, 1, 2, both positive",
            "-2, -1, 2, both negative",
            "-2, 1, -2, negative and positive",
            "-1, 1, -1, negative and positive and both equal",
            "-2, 3, 0, denominator is higher",
    })
    void shouldProcessDivision(ArgumentsAccessor arguments) {
        processIntegerOpAssertion(arguments, new ArithmeticOpNode(0, "/", SymbolDictionary.Symbol.OPERATOR));
    }

    @ParameterizedTest(name = "should process {3} numbers")
    @CsvSource({
            "1, 2, 1, power of 1",
            "2, -2, 0, negative power",
            "2, 3, 8, both positive",
            "2, 0, 1, power of 0",
    })
    void shouldProcessExponential(ArgumentsAccessor arguments) {
        processIntegerOpAssertion(arguments, new ArithmeticOpNode(0, "**", SymbolDictionary.Symbol.OPERATOR));
    }

    private void processIntegerOpAssertion(ArgumentsAccessor arguments, Node node) {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, arguments.getString(0), SymbolDictionary.Symbol.INTEGER));
            push(new DataNode(1, arguments.getString(1), SymbolDictionary.Symbol.INTEGER));
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
        minusNode = new ArithmeticOpNode(0, "-", SymbolDictionary.Symbol.OPERATOR);

        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "w", SymbolDictionary.Symbol.IDENTIFIER));
            push(new DataNode(1, "w", SymbolDictionary.Symbol.IDENTIFIER));
        }};

        Assertions.assertThrows(InvalidCSEMachineException.class, () -> {
            minusNode.execute(stack);
        });
    }
}