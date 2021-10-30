package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class LambdaNodeTest {
    Node node;

    @Test
    void shouldProcessOneControlExpression() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "2", SymbolDictionary.Symbol.INTEGER));
        }};
        Node leftX = new DataNode(1, "x", SymbolDictionary.Symbol.IDENTIFIER);
        Node plus = new ArithmeticOpNode(1, "+", SymbolDictionary.Symbol.OPERATOR);
        Node rightX = new DataNode(2, "x", SymbolDictionary.Symbol.IDENTIFIER);
        Node data = new DataNode(2, "3", SymbolDictionary.Symbol.INTEGER);
        node = new LambdaNode(0, "lambda", SymbolDictionary.Symbol.OPERATOR);

        try {
            plus.addNode(rightX);
            plus.addNode(data);
            node.addNode(leftX);
            node.addNode(plus);

            node.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals("5", stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
}