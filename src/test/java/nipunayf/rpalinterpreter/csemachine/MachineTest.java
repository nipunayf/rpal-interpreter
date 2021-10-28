package nipunayf.rpalinterpreter.csemachine;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
import nipunayf.rpalinterpreter.tree.node.DataNode;
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
}