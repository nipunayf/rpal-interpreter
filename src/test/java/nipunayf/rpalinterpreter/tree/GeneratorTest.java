package nipunayf.rpalinterpreter.tree;

import nipunayf.rpalinterpreter.tree.node.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    private final String BASE_PATH = "src/test/resources/generator/";

    @Test
    void shouldProcessParentNodeWithTwoExpressions() {
        try {
            Node node = Generator.generateTree(BASE_PATH + "two_expressions.txt");

            List<Node> children = node.getChildren();
            Node firstChild = children.get(0);
            Node secondChild = children.get(1);

            Assertions.assertAll(
                    () -> assertEquals("w", firstChild.getChildren().get(0).getValue()),
                    () -> assertEquals("z", firstChild.getChildren().get(1).getValue()),
                    () -> assertEquals("x", secondChild.getChildren().get(0).getValue()),
                    () -> assertEquals("y", secondChild.getChildren().get(1).getValue())
            );
        } catch (IOException | NoSuchMethodException e) {
            Assertions.fail(e.getMessage());
        }
    }
}