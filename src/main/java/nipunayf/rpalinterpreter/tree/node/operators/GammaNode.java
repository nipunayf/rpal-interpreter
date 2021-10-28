package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class GammaNode extends OperatorNode {
    /**
     * Creates a gamma node
     *
     * @param level level in the tree
     * @param value value of the node
     * @param type  whether it is a data type or an operation type
     */
    public GammaNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException {
        Node operator = stack.pop();

        //TODO: implement hte gamma operator
    }
}
