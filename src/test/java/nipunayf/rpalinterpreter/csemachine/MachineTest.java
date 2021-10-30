package nipunayf.rpalinterpreter.csemachine;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
import nipunayf.rpalinterpreter.tree.Generator;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.ArithmeticOpNode;
import nipunayf.rpalinterpreter.tree.node.operators.NegNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class MachineTest {
    Machine machine;
    private final String BASE_PATH = "src/test/resources/machine/";

    @Test
    void shouldEvaluateSingleControl() {
        Node nodeThree = new DataNode(3, "1", SymbolDictionary.Symbol.INTEGER);
        Node nodeTwo = new NegNode(2);
        Node nodeOne = new NegNode(1);
        Node root = new NegNode(0);

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
        Node nodeTwo = new NegNode(2);
        Node nodeOne = new NegNode(1);
        Node root = new NegNode(0);

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
        Node root = new ArithmeticOpNode(0, "+");
        Node leftChild = new NegNode(1);
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

    @Test
    void shouldEvaluateMultipleLambdasInOneControl() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "inline_lambda.txt");

            Machine machine = new Machine(new PreliminaryEnvironment(new HashMap<>() {{
                put("z", new DataNode(0, "3", SymbolDictionary.Symbol.INTEGER));
            }}), root);
            Node output  = machine.evaluate();

            assertEquals("9", output.getValue());

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException | InvalidCSEMachineException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldEvaluateNestedLambdas() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "nested_lambda.txt");

            Machine machine = new Machine(new PreliminaryEnvironment(new HashMap<>()), root);
            Node output  = machine.evaluate();

            assertEquals("10", output.getValue());

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException | InvalidCSEMachineException e) {
            fail(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings={"ternary_true", "ternary_false"})
    void shouldEvaluateTernary(String fileName) {
        try {
            Node root = Generator.generateTree(BASE_PATH + fileName + ".txt");

            Machine machine = new Machine(new PreliminaryEnvironment(new HashMap<>()), root);
            Node output  = machine.evaluate();

            assertEquals("3", output.getValue());

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException | InvalidCSEMachineException e) {
            fail(e.getMessage());
        }
    }
}