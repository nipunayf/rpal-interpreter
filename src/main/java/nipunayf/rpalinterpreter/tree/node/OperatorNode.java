package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.SymbolDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Node of the tree which represents an operation.
 * Should have children to evaluate.
 */
public class OperatorNode extends Node{
    private final List<Node> children = new ArrayList<>();

    /**
     * Creates an operator node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public OperatorNode(int level, String value) {
        super(level, value, SymbolDictionary.Symbol.OPERATOR);
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
    public void removeNode(Node node) throws NoSuchMethodException {
        if (this.children.contains(node)) {
            this.children.remove(node);
        } else throw new NoSuchElementException("Provided node is not an element of the current node");
    }

    @Override
    public Node popChild() throws NoSuchMethodException {
        return this.children.remove(0);
    }
}
