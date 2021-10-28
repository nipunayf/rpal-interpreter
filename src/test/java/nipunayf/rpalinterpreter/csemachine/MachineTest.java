package nipunayf.rpalinterpreter.csemachine;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.IntegerOpNode;
import nipunayf.rpalinterpreter.tree.node.operators.NegNode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class MachineTest {

    @Test
    void shouldEvaluateSingleControl() {
        Machine.stack = new Stack<>();
        Machine.currentEnvironment = new PreliminaryEnvironment(new HashMap<>());
        Machine.control = new Stack<>() {{
            push(new NegNode(0, "neg", SymbolDictionary.Symbol.OPERATOR));
            push(new NegNode(1, "neg", SymbolDictionary.Symbol.OPERATOR));
            push(new NegNode(2, "neg", SymbolDictionary.Symbol.OPERATOR));
            push(new DataNode(3, "1", SymbolDictionary.Symbol.INTEGER));
        }};

        try {
            assertEquals("-1", Machine.evaluate());
        } catch (InvalidCSEMachineException | NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldCheckTheEnvironmentForIdentifiers() {
        DataNode identifier = new DataNode(3, "x", SymbolDictionary.Symbol.IDENTIFIER);
        Machine.stack = new Stack<>();
        Machine.currentEnvironment = new PreliminaryEnvironment(new HashMap<>() {{
            put(identifier, new DataNode(3, "1", SymbolDictionary.Symbol.INTEGER));
        }});
        Machine.control = new Stack<>() {{
            push(new NegNode(0, "neg", SymbolDictionary.Symbol.OPERATOR));
            push(new NegNode(1, "neg", SymbolDictionary.Symbol.OPERATOR));
            push(new NegNode(2, "neg", SymbolDictionary.Symbol.OPERATOR));
            push(identifier);
        }};

        try {
            assertEquals("-1", Machine.evaluate());
        } catch (InvalidCSEMachineException | NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldInitializeSingleEnvControl() {
        Node root = new IntegerOpNode(0, "+", SymbolDictionary.Symbol.OPERATOR);
        Node leftChild = new NegNode(1, "neg", SymbolDictionary.Symbol.OPERATOR);
        Node rightChild = new DataNode(1, "2", SymbolDictionary.Symbol.INTEGER);
        Node leaf = new DataNode(2, "3", SymbolDictionary.Symbol.INTEGER);

        try {
            leftChild.addNode(leaf);
            root.addNode(leftChild);
            root.addNode(rightChild);

            Machine.initialize(root);

            Node firstNode = Machine.control.pop();
            Node secondNode = Machine.control.pop();
            Node thirdNode = Machine.control.pop();
            Node fourthNode = Machine.control.pop();

            assertAll(
                    () -> assertEquals(rightChild, firstNode),
                    () -> assertEquals(leaf, secondNode),
                    () -> assertEquals(leftChild, thirdNode),
                    () -> assertEquals(root, fourthNode)
            );

        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }
}