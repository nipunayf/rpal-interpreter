package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class LambdaNodeTest {
    Node node;

    @Test
    void shouldProcessOneCVariable() {
        Stack<Node> stack = new Stack<>() {{
            push(new DataNode(1, "2", DataDictionary.Data.INTEGER));
        }};
        Node leftX = new DataNode(1, "x", DataDictionary.Data.IDENTIFIER);
        Node plus = new ArithmeticOpNode(1, "+");
        Node rightX = new DataNode(2, "x", DataDictionary.Data.IDENTIFIER);
        Node data = new DataNode(2, "3", DataDictionary.Data.INTEGER);
        node = new LambdaNode(0);

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
        Node data1 = new DataNode(1, "1", DataDictionary.Data.INTEGER);
        Node data2 = new DataNode(1, "2", DataDictionary.Data.INTEGER);
        Node tau = new OperatorNode(0, "tau");

        Node nAry = new OperatorNode(1, ",");
        Node nAryFirstChild = new DataNode(2, "x", DataDictionary.Data.IDENTIFIER);
        Node nArySecondChild = new DataNode(2, "y", DataDictionary.Data.IDENTIFIER);
        Node plus = new ArithmeticOpNode(1, "+");
        Node leftPlus = new DataNode(2, "x", DataDictionary.Data.IDENTIFIER);
        Node rightPlus = new DataNode(2, "y", DataDictionary.Data.IDENTIFIER);
        node = new LambdaNode(0);

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

    @Test
    void shouldProcessMultipleVariables() {
        Node data1 = new DataNode(1, "1", DataDictionary.Data.INTEGER);
        Node data2 = new DataNode(1, "2", DataDictionary.Data.INTEGER);

        Node lambdaFirstChild = new DataNode(2, "x", DataDictionary.Data.IDENTIFIER);
        Node lambdaSecondChild = new DataNode(2, "y", DataDictionary.Data.IDENTIFIER);
        Node plus = new ArithmeticOpNode(1, "+");
        Node leftPlus = new DataNode(2, "x", DataDictionary.Data.IDENTIFIER);
        Node rightPlus = new DataNode(2, "y", DataDictionary.Data.IDENTIFIER);
        node = new LambdaNode(0);

        try {
            node.addNode(lambdaFirstChild);
            node.addNode(lambdaSecondChild);;
            plus.addNode(leftPlus);
            plus.addNode(rightPlus);
            node.addNode(plus);

            Stack<Node> stack = new Stack<>() {{
                push(data1);
                push(data2);
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