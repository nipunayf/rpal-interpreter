package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ConcatNodeTest {

    @Test
    void shouldConcatTwoStrings() {
        Node node = new ConcatNode(0);

        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "'" + "b" + "'", SymbolDictionary.Symbol.STRING));
            push(new DataNode(1, "'" + "a" + "'", SymbolDictionary.Symbol.STRING));
        }};

        try {
            node.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals("ab", stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}