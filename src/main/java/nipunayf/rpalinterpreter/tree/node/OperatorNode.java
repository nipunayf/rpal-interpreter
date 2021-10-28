package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.SymbolDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Node of the tree which represents an operation.
 * Should have children to evaluate.
 */
class OperatorNode extends Node{
    private final List<Node> children = new ArrayList<>();

    /**
     * Creates an AST node
     *
     * @param level
     * @param value
     * @param type
     */
    OperatorNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
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
