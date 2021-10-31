package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AugNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public AugNode(int level) {
        super(level, "aug");
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node firstNode = stack.pop();
        Node secondNode = stack.pop();

        Node tau = new TauNode(this.getLevel());
//        List<Node> concatList = Stream.concat(firstNode.getChildren().stream(), secondNode.getChildren().stream()).collect(Collectors.toList());

        if (firstNode.getValue().equals("nil")) {
            tau.addNode(secondNode);
        } else if (firstNode instanceof TauNode) {
            List<Node> children = firstNode.getChildren();
            for (Node child : children) tau.addNode(child);
            tau.addNode(secondNode);
        } else {
            throw new InvalidCSEMachineException("Invalid operands for aug operator");
        }

        stack.push(tau);
    }
}
