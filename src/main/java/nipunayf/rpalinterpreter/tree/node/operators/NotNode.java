package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class NotNode extends OperatorNode {
    /**
     * Creates a not node
     *
     * @param level level in the tree
     */
    public NotNode(int level) {
        super(level, "not");
    }

    @Override
    public void execute(Stack<Node> stack) throws InvalidCSEMachineException {
        Node data = stack.pop();
        if (data.getType() != SymbolDictionary.Symbol.BOOLEAN)
            throw new InvalidCSEMachineException("Boolean operator only supports boolean");

        stack.push(new DataNode(data.getLevel(), Boolean.toString(!Boolean.parseBoolean(data.getValue())), data.getType()));
    }
}
