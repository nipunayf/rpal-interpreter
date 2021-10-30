package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class TauNodeTest {
    Node node;

    @BeforeEach
    void setUp() {
        node = new TauNode(0);
    }

    @Test
    void shouldCombineOneElement() {
        Node data = new DataNode(1, "1" , SymbolDictionary.Symbol.INTEGER);

        Stack<Node> stack = new Stack<>() {{
            push(data);
        }};

        try {
            node.addNode(data);
            node.execute(stack);
            Node tau = stack.pop();
            assertAll(
                    () -> assertEquals(0, stack.size()),
                    () -> assertEquals("tau", tau.getValue()),
                    () -> assertEquals("1", tau.popChild().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldCombineMultipleElements() {
        Node data1 = new DataNode(1, "1" , SymbolDictionary.Symbol.INTEGER);
        Node data2 = new DataNode(1, "2" , SymbolDictionary.Symbol.INTEGER);

        Stack<Node> stack = new Stack<>() {{
            push(data1);
            push(data2);
        }};

        try {
            node.addNode(data1);
            node.addNode(data2);

            node.execute(stack);
            Node tau = stack.pop();
            assertAll(
                    () -> assertEquals(0, stack.size()),
                    () -> assertEquals("tau", tau.getValue()),
                    () -> assertEquals("2", tau.popChild().getValue()),
                    () -> assertEquals("1", tau.popChild().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}