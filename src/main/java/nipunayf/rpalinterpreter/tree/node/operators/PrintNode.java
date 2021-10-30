package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class PrintNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public PrintNode(int level) {
        super(level, "Print");
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node output = stack.pop();
        System.out.println(output.getValue());
        stack.push(output);
    }
}
