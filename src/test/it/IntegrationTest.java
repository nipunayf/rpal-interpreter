import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.Interpreter;
import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class IntegrationTest {
    @ParameterizedTest(name = "should process {0} AST tree")
    @CsvSource({
            "fn1, 4",
            "fn2, irst letter missing in this sentence?",
            "fn3, 15",
            "abs, 3",
            "infix, 13",
            "infix2, 18",
            "add, 15",
            "ftst, 1",
            "substring, ell",
            "dummy, true"
    })
    void shouldEvaluateSingleOutputAST(ArgumentsAccessor arguments) {
        runProgram(arguments.getString(0), arguments.getString(1));
    }

    @ParameterizedTest(name = "should process {0} AST tree")
    @MethodSource("provideParameters")
    public void testParametersFromMethod(String name, String output) {
        runProgram(name, output);
    }

    private void runProgram(String name, String output) {
        String BASE_PATH = "src/test/resources/it/";
        Interpreter.main(new String[]{BASE_PATH + name + ".txt"});
        assertEquals(output , getOutputString(Interpreter.outputValue));
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("pairs1", "(ad, be, cf)"),
                Arguments.of("pairs2", "(ad, be, cf)"),
                Arguments.of("pairs3", "(ad, be, cf)"),
                Arguments.of("factorial", "(6, 120, 5040)"),
                Arguments.of("conc", "(CIS104B, CIS104B, CIS104B)"),
                Arguments.of("vector_sum", "(5, 7, 9)"),
                Arguments.of("fib", "(0, 1, 1, 2, 3)"),
                Arguments.of("is_function", "true"),
                Arguments.of("towers", "Move A to C\\nMove A to B\\nMove C to B\\nMove A to C\\nMove B to A\\nMove B to C\\nMove A to C\\nMove A to B\\nMove C to B\\nMove C to A\\nMove B to A\\nMove C to B\\nMove A to C\\nMove A to B\\nMove C to B\\n")
        );
    }

    private String getOutputString(Node output) {
        if (OperatorDictionary.map.get(output.getValue()) == OperatorDictionary.Operator.TAU) {
            String joinedResult = null;
            try {
                joinedResult = output.getChildren().stream()
                        .map(Node::getValue)
                        .collect(Collectors.joining(", "));
            } catch (NoSuchMethodException e) {
                fail(e.getMessage());
            }
            return "("+ joinedResult +")";
        } else {
            return output.getValue();
        }
    }
}
