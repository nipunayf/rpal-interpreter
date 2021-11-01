import nipunayf.rpalinterpreter.myrpal;
import nipunayf.rpalinterpreter.tree.node.operators.BooleanOpNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        myrpal.main(new String[]{BASE_PATH + name + ".txt"});
        assertEquals(output , myrpal.outputValue);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("a", 1),
                Arguments.of("b", 2),
                Arguments.of("foo", 3)
        );
    }
}
