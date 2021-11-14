package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ConcatNodeTest {

    @Test
    void shouldConcatTwoStrings() {
        Node node = new ConcatNode(0);

        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "'" + "b" + "'", DataDictionary.Symbol.STRING));
            push(new DataNode(1, "'" + "a" + "'", DataDictionary.Symbol.STRING));
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