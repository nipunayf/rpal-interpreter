package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

/**
 *
 */
public class TauNode extends OperatorNode {
    /**
     * Sets if the gamma node executes this node.
     * Used when selecting an element from the tuple.
     */
    boolean executedByGamma;

    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public TauNode(int level) {
        super(level, "tau");
        directlyExecutable = true;
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        int numChildren = this.getChildren().size();

        // Selecting an element positioned by the topmost stack element
        if (executedByGamma) {
            int position = Integer.parseInt(stack.pop().getValue());
            stack.add(this.getChildren().get(position - 1));
            this.executedByGamma = false;
        }

        // Selects n elements from the stack
        else {
            Node tau = new TauNode(this.getLevel());
            for (int i = 0; i < numChildren; i++) {
                tau.addNode(stack.pop());
            }
            stack.add(tau);
        }
    }

    public void setExecutedByGamma() {
        this.executedByGamma = true;
    }
}
