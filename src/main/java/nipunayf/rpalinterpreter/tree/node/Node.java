package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary.Symbol;

import java.util.List;

/**
 * Represents a Node of the tree.
 * Follows the composite design pattern.
 * Hence, both OperatorNode and DataNode must extend this abstract class
 */
public abstract class Node {
    int level;
    Symbol type;
    String value;
    Node parent;

    /**
     * Creates an AST node
     */
    Node(int level, String value, Symbol type) {
        this.level = level;
        this.value = value;
        this.type = type;
        this.parent = null;
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
     * Get all the children of the node
     *
     * @return children
     * @throws NoSuchMethodException not valid for leaf nodes
     */
    public List<Node> getChildren() throws NoSuchMethodException {
        throw new NoSuchMethodException("Cannot retrieve children from a leaf node");
    }

    /**
     * Get the first child of the node.
     * This removes the respective child from the list.
     *
     * @return child at index 0
     * @throws NoSuchMethodException not valid for leaf nodes
     */
    public Node popChild() throws NoSuchMethodException {
        throw new NoSuchMethodException("Cannot retrieve a child from a leaf node");
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

    /**
     * Generates a node from the input line
     *
     * @param line
     * @return
     */
    public static Node generateNode(String line) {
        int stoppedIndex = 0;

        // Determining the level of the node by its dots
        int level = 0;
        for (int i = 0; i < line.length(); i++) {
            char a = line.charAt(i);
            if (a == '.') {
                level++;
                stoppedIndex = i + 1;
            } else break;
        }

        // Extracting the type and its value
        char valueStart = line.charAt(stoppedIndex);
        SymbolDictionary.Symbol type;
        String value;

        // The type is a data type
        if (valueStart == '<') {
            int valueStopIndex = line.indexOf(':');
            type = SymbolDictionary.map.get(line.substring(stoppedIndex + 1, valueStopIndex));
            int closeIndex = line.indexOf('>');
            value = line.substring(valueStopIndex + 1, closeIndex);
            return new DataNode(level, value, type);

        } else { // The type is an operator
            type = SymbolDictionary.Symbol.OPERATOR;
            value = line.substring(stoppedIndex, line.length());
            return new OperatorNode(level, value, type);
        }
    }

    public int getLevel() {
        return level;
    }

    public Symbol getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
