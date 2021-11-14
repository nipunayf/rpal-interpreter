package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.csemachine.Machine;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class EtaNode extends OperatorNode {
    /**
     * Reference to the main control stack.
     */
    private Machine machine;
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
        stack.add(this);
        stack.add(this.lambda);

        machine.preorder(new OperatorNode(this.getLevel(), "gamma"));
        machine.preorder(new OperatorNode(this.getLevel(), "gamma"));
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
