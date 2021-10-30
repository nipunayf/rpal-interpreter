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
    void shouldProcessOneCVariable() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "2", SymbolDictionary.Symbol.INTEGER));
        }};
        Node leftX = new DataNode(1, "x", SymbolDictionary.Symbol.IDENTIFIER);
        Node plus = new ArithmeticOpNode(1, "+");
        Node rightX = new DataNode(2, "x", SymbolDictionary.Symbol.IDENTIFIER);
        Node data = new DataNode(2, "3", SymbolDictionary.Symbol.INTEGER);
        node = new LambdaNode(0, "lambda");

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

    @Test
    void shouldProcessNAryVariables() {
        Node data1 = new DataNode(1, "1", SymbolDictionary.Symbol.INTEGER);
        Node data2 = new DataNode(1, "2", SymbolDictionary.Symbol.INTEGER);
        Node tau = new OperatorNode(0, "tau");

        Node nAry = new OperatorNode(1, ",");
        Node nAryFirstChild = new DataNode(2, "x", SymbolDictionary.Symbol.IDENTIFIER);
        Node nArySecondChild = new DataNode(2, "y", SymbolDictionary.Symbol.IDENTIFIER);
        Node plus = new ArithmeticOpNode(1, "+");
        Node leftPlus = new DataNode(2, "x", SymbolDictionary.Symbol.IDENTIFIER);
        Node rightPlus = new DataNode(2, "y", SymbolDictionary.Symbol.IDENTIFIER);
        node = new LambdaNode(0, "lambda");

        try {
            nAry.addNode(nAryFirstChild);
            nAry.addNode(nArySecondChild);
            plus.addNode(leftPlus);
            plus.addNode(rightPlus);
            node.addNode(nAry);
            node.addNode(plus);

            tau.addNode(data1);
            tau.addNode(data2);
            Stack<Node> stack = new Stack<>() {{
                push(tau);
            }};

            node.execute(stack);
            assertAll(
                    () -> assertEquals(1, stack.size()),
                    () -> assertEquals("3", stack.pop().getValue())
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}