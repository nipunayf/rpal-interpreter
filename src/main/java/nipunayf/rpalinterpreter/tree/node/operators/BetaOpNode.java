package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.csemachine.Machine;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.List;
import java.util.Stack;

public class BetaOpNode extends OperatorNode {
    /**
     * Reference to the main control stack.
     */
    private Machine machine;
    private Node trueNode;
    private Node falseNode;

    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public BetaOpNode(int level, Machine machine, Node trueNode, Node falseNode) {
        super(level, "B");
        this.machine = machine;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node condition = stack.pop();

        if (Boolean.parseBoolean(condition.getValue())) {
            machine.preorder(trueNode);
        } else {
            machine.preorder(falseNode);
        }
    }
}
