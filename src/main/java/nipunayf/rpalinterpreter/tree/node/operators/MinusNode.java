package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import org.junit.jupiter.api.Assertions;

import java.util.Stack;

public class MinusNode extends OperatorNode {
    /**
     * Creates a negation node
     *
     * @param level level in the tree
     * @param value value of the node
     * @param type  whether it is a data type or an operation type
     */
    public MinusNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
    }

    @Override
    public void execute(Stack<Node> stack) throws InvalidCSEMachineException {
        Node firstData = stack.pop();
        Node secondData = stack.pop();

        if (firstData.getType() != SymbolDictionary.Symbol.INTEGER ||
                secondData.getType() != SymbolDictionary.Symbol.INTEGER)
            throw new InvalidCSEMachineException("Plus operator only supports integers");

        int value = (Integer.parseInt(secondData.getValue()) - Integer.parseInt(firstData.getValue()));

        stack.push(new DataNode(firstData.getLevel(), Integer.toString(value) , SymbolDictionary.Symbol.INTEGER));
    }
}
