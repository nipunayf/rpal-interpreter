package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.DataDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Node of the tree which represents an operation.
 * Should have children to evaluate.
 */
public class OperatorNode extends Node {
    private final List<Node> children = new ArrayList<>();
    protected boolean directlyExecutable;

    /**
     * Creates an operator node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public OperatorNode(int level, String value) {
        super(level, value, DataDictionary.Data.OPERATOR);
        directlyExecutable = false;
    }

    @Override
    public void addNode(Node node) throws NoSuchMethodException {
        this.children.add(node);
    }

    @Override
    public List<Node> getChildren() throws NoSuchMethodException {
        return this.children;
    }

    @Override
    public Node popChild() throws NoSuchMethodException {
        return this.children.remove(0);
    }

    public boolean isDirectlyExecutable() {
        return directlyExecutable;
    }
}
