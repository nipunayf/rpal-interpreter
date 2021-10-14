package nipunayf.rpalinterpreter.tree.builder;

import nipunayf.rpalinterpreter.SymbolDictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {

    @ParameterizedTest
    @CsvSource({
            "....<ID:Psum>, 4, IDENTIFIER, Psum",
            "...gamma, 3, OPERATOR, gamma",
            "......<INT:0>, 6, INTEGER, 0",
            "let, 0, OPERATOR, let",

    })
    void shouldGenerateNodeFromLine(ArgumentsAccessor arguments) {
        Builder builder = new Builder();
        Node node = builder.generateNode(arguments.getString(0));

        Assertions.assertAll(
                () -> assertEquals(node.level, arguments.getInteger(1)),
                () -> assertEquals(node.type, arguments.get(2, SymbolDictionary.Symbol.class)),
                () -> assertEquals(node.value, arguments.getString(3))
        );
    }

    @Test
    void shouldNotAllowAddNodeToData() {
        Builder builder = new Builder();

        Node nodeParent = builder.generateNode("..<INT:0>");
        Node nodeChild = builder.generateNode("...<INT:0>");

        Assertions.assertThrows(NoSuchMethodException.class, () -> {nodeParent.addNode((nodeChild));});
    }
}