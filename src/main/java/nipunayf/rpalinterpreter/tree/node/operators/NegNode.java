package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class NegNode extends OperatorNode {
    /**
     * Creates a negation node
     *
     * @param level level in the tree
     * @param value value of the node
     * @param type  whether it is a data type or an operation type
     */
    public NegNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
    }

    @Override
    public void execute(Stack<Node> stack) throws InvalidCSEMachineException {
        Node data = stack.pop();
        if (data.getType() != SymbolDictionary.Symbol.INTEGER)
            throw new InvalidCSEMachineException("Negation operator only supports integers");

        String dataValue = data.getValue();
        String value = dataValue.charAt(0) == '-' ? dataValue.substring(1) : "-" + dataValue;

        stack.push(new DataNode(data.getLevel(), value , data.getType()));
    }
}
