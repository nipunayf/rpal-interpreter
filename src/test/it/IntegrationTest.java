import nipunayf.rpalinterpreter.myrpal;
import nipunayf.rpalinterpreter.tree.node.operators.BooleanOpNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Array;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    @ParameterizedTest(name = "should process {0} AST tree")
    @CsvSource({
            "fn1, 4",
            "fn2, irst letter missing in this sentence?",
            "fn3, 15",
            "abs, 3",
            "infix, 13",
            "infix2, 18"
    })
    void shouldEvaluateAST(ArgumentsAccessor arguments) {
        String BASE_PATH = "src/test/resources/it/";
        myrpal.main(new String[]{BASE_PATH + arguments.getString(0) + ".txt"});
        assertEquals(arguments.getString(1) , myrpal.outputValue);
    }
}
