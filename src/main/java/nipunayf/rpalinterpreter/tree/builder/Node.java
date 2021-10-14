package nipunayf.rpalinterpreter.tree.builder;

import nipunayf.rpalinterpreter.SymbolDictionary.Symbol;

/**
 * Represents a Node of the tree.
 * Follows the composite design pattern.
 * Hence, both OperatorNode and DataNode must extend this abstract class
 */
public abstract class Node {
    int level;
    Symbol type;
    String value;

    /**
     * Creates an AST node
     */
    Node(int level, String value, Symbol type) {
        this.level = level;
        this.value = value;
        this.type = type;
    }

    /**
     * Add a child node to a composite node
     *
     * @param node
     * @throws NoSuchMethodException not valid for leaf nodes
     */
    public void addNode(Node node) throws NoSuchMethodException {
        throw new NoSuchMethodException("Cannot add a node to a leaf node");
    }

    /**
     * Removes a child node form the composite node
     *
     * @param node
     * @throws NoSuchMethodException not valid for leaf nodes
     */
    public void removeNode(Node node) throws NoSuchMethodException {
        throw new NoSuchMethodException("Cannot remove a node from a leaf node");
    }
}
