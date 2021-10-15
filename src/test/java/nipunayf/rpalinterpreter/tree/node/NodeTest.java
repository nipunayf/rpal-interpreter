package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.SymbolDictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @ParameterizedTest
    @CsvSource({
            "....<ID:Psum>, 4, IDENTIFIER, Psum",
            "...gamma, 3, OPERATOR, gamma",
            "......<INT:0>, 6, INTEGER, 0",
            "let, 0, OPERATOR, let",

    })
    void shouldGenerateNodeFromLine(ArgumentsAccessor arguments) {
        Node node = Node.generateNode(arguments.getString(0));

        Assertions.assertAll(
                () -> assertEquals(node.level, arguments.getInteger(1)),
                () -> assertEquals(node.type, arguments.get(2, SymbolDictionary.Symbol.class)),
                () -> assertEquals(node.value, arguments.getString(3))
        );
    }

    @Test
    void shouldNotAllowAddNodeToData() {
        Node nodeParent = Node.generateNode("..<INT:0>");
        Node nodeChild = Node.generateNode("...<INT:0>");

        Assertions.assertThrows(NoSuchMethodException.class, () -> {
            nodeParent.addNode((nodeChild));
        });
    }

    @Test
    void shouldAllowAddNodeToOperator() {
        Node nodeParent = Node.generateNode("let");
        Node nodeChild = Node.generateNode(".<INT:0>");

        try {
            nodeParent.addNode(nodeChild);
            List<Node> children = List.of(nodeChild);
            Assertions.assertEquals(nodeParent.getChildren(), children);
        } catch (NoSuchMethodException e) {
            Assertions.fail(e.getMessage());
        }
    }
}