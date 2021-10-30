package nipunayf.rpalinterpreter.csemachine;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.ArithmeticOpNode;
import nipunayf.rpalinterpreter.tree.node.operators.NegNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class MachineTest {
    Machine machine;

    @Test
    void shouldEvaluateSingleControl() {
        Node nodeThree = new DataNode(3, "1", SymbolDictionary.Symbol.INTEGER);
        Node nodeTwo = new NegNode(2, "neg", SymbolDictionary.Symbol.OPERATOR);
        Node nodeOne = new NegNode(1, "neg", SymbolDictionary.Symbol.OPERATOR);
        Node root = new NegNode(0, "neg", SymbolDictionary.Symbol.OPERATOR);

        try {
            nodeTwo.addNode(nodeThree);
            nodeOne.addNode(nodeTwo);
            root.addNode(nodeOne);

            machine = new Machine(new PreliminaryEnvironment(new HashMap<>()), root);

            assertEquals("-1", machine.evaluate().getValue());
        } catch (InvalidCSEMachineException | NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldCheckTheEnvironmentForIdentifiers() {
        DataNode identifier = new DataNode(3, "x", SymbolDictionary.Symbol.IDENTIFIER);
        Node nodeTwo = new NegNode(2, "neg", SymbolDictionary.Symbol.OPERATOR);
        Node nodeOne = new NegNode(1, "neg", SymbolDictionary.Symbol.OPERATOR);
        Node root = new NegNode(0, "neg", SymbolDictionary.Symbol.OPERATOR);

        try {
            nodeTwo.addNode(identifier);
            nodeOne.addNode(nodeTwo);
            root.addNode(nodeOne);

            machine = new Machine(new PreliminaryEnvironment(new HashMap<>() {{
                put(identifier.getValue(), new DataNode(3, "1", SymbolDictionary.Symbol.INTEGER));
            }}), root);

            assertEquals("-1", machine.evaluate().getValue());
        } catch (InvalidCSEMachineException | NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldInitializeSingleEnvControl() {
        Node root = new ArithmeticOpNode(0, "+", SymbolDictionary.Symbol.OPERATOR);
        Node leftChild = new NegNode(1, "neg", SymbolDictionary.Symbol.OPERATOR);
        Node rightChild = new DataNode(1, "2", SymbolDictionary.Symbol.INTEGER);
        Node leaf = new DataNode(2, "3", SymbolDictionary.Symbol.INTEGER);

        try {
            leftChild.addNode(leaf);
            root.addNode(leftChild);
            root.addNode(rightChild);

            machine = new Machine(new PreliminaryEnvironment(new HashMap<>()), root);

            Node firstNode = machine.control.pop();
            Node secondNode = machine.control.pop();
            Node thirdNode = machine.control.pop();
            Node fourthNode = machine.control.pop();

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