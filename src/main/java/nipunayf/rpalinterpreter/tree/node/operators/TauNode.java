package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class TauNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public TauNode(int level) {
        super(level, "tau");
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        super.execute(stack);
    }
}
