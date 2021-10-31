package nipunayf.rpalinterpreter.tree.node.operators;

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

        if (!(firstNode instanceof TauNode) || !(secondNode instanceof TauNode)) {
            throw new InvalidCSEMachineException(this.getValue() + " operator only supports tuples");
        }

        Node tau = new TauNode(this.getLevel());
        List<Node> concatList = Stream.concat(firstNode.getChildren().stream(), secondNode.getChildren().stream()).collect(Collectors.toList());

        for (Node child : concatList) tau.addNode(child);
        stack.push(tau);
    }
}
