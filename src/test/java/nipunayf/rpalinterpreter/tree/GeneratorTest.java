package nipunayf.rpalinterpreter.tree;

import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}