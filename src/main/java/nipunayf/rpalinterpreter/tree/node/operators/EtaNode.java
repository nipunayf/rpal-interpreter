package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.csemachine.Machine;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

/**
 * This node is used for recursion in the CSE machine.
 *
 */
public class EtaNode extends OperatorNode {
    /**
     * Reference to the main control stack.
     */
    private Machine machine;

    /**
     * Reference to the respective lambda node.
     */
    private final Node lambda;

    /**
     * Creates an eta node
     *
     * @param level  level in the tree
     * @param lambda reference to the lambda node
     */
    public EtaNode(int level, Node lambda) {
        super(level, "Eta");
        this.lambda = lambda;
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        // Add the eta node and the lambda node to the stack
        stack.add(this);
        stack.add(this.lambda);

        // Add two gamma nodes to the control stack
        machine.preorder(new OperatorNode(this.getLevel(), "gamma"));
        machine.preorder(new OperatorNode(this.getLevel(), "gamma"));
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
