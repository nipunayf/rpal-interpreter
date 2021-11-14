package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

/**
 * YNode is used in the AST tree for the purpose of recursion
 */
public class YNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public YNode(int level) {
        super(level, "Y");
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        // Creates a new Eta node
        Node eta = new EtaNode(this.getLevel(), stack.pop());
        stack.push(eta);
    }
}
