package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

/**
 * Handles the conversion form integer to string
 */
public class IToSNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public IToSNode(int level) {
        super(level, "ItoS");
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node number = stack.pop();

        // Only valid for an integer operand
        if (number.getType() != DataDictionary.Data.INTEGER)
            throw new InvalidCSEMachineException("ItoS only supports for integers");

        // Add the string node to the stack
        stack.push(new DataNode(this.getLevel(), "'" + number.getValue() + "'", DataDictionary.Data.STRING));
    }
}
