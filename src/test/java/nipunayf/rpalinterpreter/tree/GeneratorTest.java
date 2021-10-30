package nipunayf.rpalinterpreter.tree;

import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    private final String BASE_PATH = "src/test/resources/generator/";

    @Test
    void shouldNotStandardizePlusOp() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "two_expressions.txt");

            List<Node> children = root.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            Assertions.assertAll(
                    () -> assertEquals("w", firstChild.getChildren().get(0).getValue()),
                    () -> assertEquals("z", firstChild.getChildren().get(1).getValue()),
                    () -> assertEquals("x", secondChild.getChildren().get(0).getValue()),
                    () -> assertEquals("y", secondChild.getChildren().get(1).getValue())
            );
        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldStandardizeFunctionFormOpOne() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "function_form_one.txt");

            List<Node> children = root.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            List<Node> lambdaChildren = secondChild.getChildren();
            Node firstLambdaChild = lambdaChildren.get(0);
            Node secondLambdaChild = lambdaChildren.get(1);

            assertAll(
                    () -> assertEquals("=", root.getValue()),
                    () -> assertEquals("P", firstChild.getValue()),
                    () -> assertEquals("lambda", secondChild.getValue()),
                    () -> assertEquals(",", firstLambdaChild.getValue()),
                    () -> assertEquals("+", secondLambdaChild.getValue())
            );

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldStandardizeFunctionFormOpMulti() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "function_form_multi.txt");

            List<Node> children = root.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            List<Node> lambdaChildren = secondChild.getChildren();
            Node firstLambdaChild = lambdaChildren.get(0);
            Node secondLambdaChild = lambdaChildren.get(1);
            Node lastLambdaChild = lambdaChildren.get(2);

            assertAll(
                    () -> assertEquals("=", root.getValue()),
                    () -> assertEquals("P", firstChild.getValue()),
                    () -> assertEquals("lambda", secondChild.getValue()),
                    () -> assertEquals("T", firstLambdaChild.getValue()),
                    () -> assertEquals("N", secondLambdaChild.getValue()),
                    () -> assertEquals("+", lastLambdaChild.getValue())
            );

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldStandardizeRecursion() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "rec.txt");

            List<Node> children = root.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            List<Node> gammaChildren = secondChild.getChildren();
            Node firstGammaChild = gammaChildren.get(0);
            Node secondGammaChild = gammaChildren.get(1);

            List<Node> lambdaChildren = secondGammaChild.getChildren();
            Node firstLambdaChild = lambdaChildren.get(0);
            Node secondLambdaChild = lambdaChildren.get(1);

            assertAll(
                    () -> assertEquals("=", root.getValue()),
                    () -> assertEquals("P", firstChild.getValue()),
                    () -> assertEquals("gamma", secondChild.getValue()),
                    () -> assertEquals("Y", firstGammaChild.getValue()),
                    () -> assertEquals("lambda", secondGammaChild.getValue()),
                    () -> assertEquals("P", firstLambdaChild.getValue()),
                    () -> assertEquals("lambda", secondLambdaChild.getValue())
            );

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }

    @ParameterizedTest(name = "should standardize {0} operator")
    @ValueSource(strings = { "let", "where" })
    void shouldStandardizeWhereAndLet(String op) {
        try {
            Node root = Generator.generateTree(BASE_PATH + op + ".txt");

            List<Node> children = root.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            List<Node> lambdaChildren = firstChild.getChildren();
            Node firstEqChild = lambdaChildren.get(0);
            Node secondEqChild = lambdaChildren.get(1);

            assertAll(
                    () -> assertEquals("gamma", root.getValue()),
                    () -> assertEquals("lambda", firstChild.getValue()),
                    () -> assertEquals("lambda", secondChild.getValue()),
                    () -> assertEquals("P", firstEqChild.getValue()),
                    () -> assertEquals("Q", secondEqChild.getValue())
            );

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldStandardizeWithin() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "within.txt");

            List<Node> children = root.getChildren();
            Node firstEqChild = children.get(0);
            Node secondEqChild = children.get(1);

            List<Node> gammaChildren = secondEqChild.getChildren();
            Node firstGammaChild = gammaChildren.get(0);
            Node secondGammaChild = gammaChildren.get(1).popChild();

            List<Node> lambdaChildren = firstGammaChild.getChildren();
            Node firstLambdaChild = lambdaChildren.get(0);
            Node secondLambdaChild = lambdaChildren.get(1).popChild();

            assertAll(
                    () -> assertEquals("=", root.getValue()),
                    () -> assertEquals("X2", firstEqChild.getValue()),
                    () -> assertEquals("gamma", secondEqChild.getValue()),
                    () -> assertEquals("lambda", firstGammaChild.getValue()),
                    () -> assertEquals("E1", secondGammaChild.getValue()),
                    () -> assertEquals("X1", firstLambdaChild.getValue()),
                    () -> assertEquals("E2", secondLambdaChild.getValue())
            );

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldStandardizeInfix() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "infix.txt");

            List<Node> children = root.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            List<Node> gammaChildren = firstChild.getChildren();
            Node firstGammaChild = gammaChildren.get(0);
            Node secondGammaChild = gammaChildren.get(1);

            assertAll(
                    () -> assertEquals("gamma", root.getValue()),
                    () -> assertEquals("gamma", firstChild.getValue()),
                    () -> assertEquals("E2", secondChild.getValue()),
                    () -> assertEquals("N", firstGammaChild.getValue()),
                    () -> assertEquals("E1", secondGammaChild.getValue())
            );

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldStandardizeSingleAnd() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "and_single.txt");

            List<Node> children = root.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            Node nAryChild = firstChild.popChild();
            Node tauChild = secondChild.popChild();

            assertAll(
                    () -> assertEquals("gamma", root.getValue()),
                    () -> assertEquals(",", firstChild.getValue()),
                    () -> assertEquals("tau", secondChild.getValue()),
                    () -> assertEquals("X1", nAryChild.getValue()),
                    () -> assertEquals("E1", tauChild.getValue())
            );

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldStandardizeMultiAnd() {
        try {
            Node root = Generator.generateTree(BASE_PATH + "and_multi.txt");

            List<Node> children = root.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            List<Node> nAryChildren = firstChild.getChildren();
            Node nAryFirstChild = nAryChildren.get(0);
            Node nArySecondChild = nAryChildren.get(1);

            List<Node> tauChildren = secondChild.getChildren();
            Node tauFirstChild = tauChildren.get(0);
            Node tauSecondChild = tauChildren.get(1);

            assertAll(
                    () -> assertEquals("gamma", root.getValue()),
                    () -> assertEquals(",", firstChild.getValue()),
                    () -> assertEquals("tau", secondChild.getValue()),
                    () -> assertEquals("X1", nAryFirstChild.getValue()),
                    () -> assertEquals("X2", nArySecondChild.getValue()),
                    () -> assertEquals("E1", tauFirstChild.getValue()),
                    () -> assertEquals("E2", tauSecondChild.getValue())
            );

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            fail(e.getMessage());
        }
    }
}