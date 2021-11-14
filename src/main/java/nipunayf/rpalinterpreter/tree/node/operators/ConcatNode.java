package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

/**
 * Handle the concatenation of two string nodes
 */
public class ConcatNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public ConcatNode(int level) {
        super(level, "Concat");
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        String firstString = stack.pop().getValue();
        String secondString = stack.pop().getValue();

        // Add the concatenated two strings as a data node to the stack.
        stack.push(new DataNode(this.getLevel(), "'" + firstString + secondString + "'", DataDictionary.Data.STRING));
    }
}
