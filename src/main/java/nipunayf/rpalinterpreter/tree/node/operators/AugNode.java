package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.List;
import java.util.Stack;

/**
 * Augments the data node to a tuple
 */
public class AugNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public AugNode(int level) {
        super(level, "aug");
        directlyExecutable = true;
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node firstNode = stack.pop();
        Node secondNode = stack.pop();

        Node tau = new TauNode(this.getLevel());

        // If the first node is a nil, then create a tau node with the second value.
        if (DataDictionary.map.get(firstNode.getValue()) == DataDictionary.Data.NIL) {
            tau.addNode(secondNode);
        }

        // If the first node is tau node, then add the second node to its children.
        else if (firstNode instanceof TauNode) {
            List<Node> children = firstNode.getChildren();
            for (Node child : children) tau.addNode(child);
            tau.addNode(secondNode);
        }

        // Invalid operands for the aug operator
        else {
            throw new InvalidCSEMachineException("Invalid operands for aug operator");
        }

        stack.push(tau);
    }
}
