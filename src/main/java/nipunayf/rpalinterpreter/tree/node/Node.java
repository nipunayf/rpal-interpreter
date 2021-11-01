package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary.Symbol;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.operators.*;

import java.util.List;
import java.util.Stack;

/**
 * Represents a Node of the tree.
 * Follows the composite design pattern.
 * Hence, both OperatorNode and DataNode must extend this abstract class
 */
public abstract class Node implements Cloneable {
    int level;
    Symbol type;
    String value;
    Node parent;

    /**
     * Creates an AST node
     *
     * @param level level in the tree
     * @param value value of the node
     * @param type  whether it is a data type or an operation type
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
     * @param node node to be added
     * @throws NoSuchMethodException not valid for data nodes
     */
    public void addNode(Node node) throws NoSuchMethodException {
        throw new NoSuchMethodException("Cannot add a node to a data node");
    }

    /**
     * Get all the children of the node
     *
     * @return children
     * @throws NoSuchMethodException not valid for data nodes
     */
    public List<Node> getChildren() throws NoSuchMethodException {
        throw new NoSuchMethodException("Cannot retrieve children from a data node");
    }

    /**
     * Execute the function of the CSE element.
     *
     * @param stack stack of the CSE machine
     * @throws NoSuchMethodException not valid for data nodes
     */
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        throw new NoSuchMethodException("Data nodes do not have a function to execute");
    }

    /**
     * Get the first child of the node.
     * This removes the respective child from the list.
     *
     * @return child at index 0
     * @throws NoSuchMethodException not valid for data nodes
     */
    public Node popChild() throws NoSuchMethodException {
        throw new NoSuchMethodException("Cannot retrieve a child from a data node");
    }

    /**
     * Removes a child node form the composite node
     *
     * @param node node to be removed
     * @throws NoSuchMethodException not valid for data nodes
     */
    public void removeNode(Node node) throws NoSuchMethodException {
        throw new NoSuchMethodException("Cannot remove a node from a data node");
    }

    /**
     * Generates a node from the input line
     *
     * @param line expressing the node parameters
     * @return generated node
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
            if (line.charAt(stoppedIndex+1) == 'n') {
                return new DataNode(level, "nil", Symbol.TUPLE);
            }
            else if (line.charAt(stoppedIndex+1) == 'f') {
                return new DataNode(level, "false", Symbol.BOOLEAN);
            }
            else if (line.charAt(stoppedIndex+1) == 't') {
                return new DataNode(level, "true", Symbol.BOOLEAN);
            }
            else if (line.charAt(stoppedIndex+1) == 'd') {
                return new DataNode(level, "dummy", Symbol.DUMMY);
            }
            else {
                int valueStopIndex = line.indexOf(':');
                type = SymbolDictionary.map.get(line.substring(stoppedIndex + 1, valueStopIndex));
                int closeIndex = line.indexOf('>');
                value = line.substring(valueStopIndex + 1, closeIndex);
                return new DataNode(level, value, type);
            }

        } else { // The type is an operator
            type = SymbolDictionary.Symbol.OPERATOR;
            value = line.substring(stoppedIndex);
            return generateOperatorNode(level, value, type);
        }
    }

    private static OperatorNode generateOperatorNode(int level, String value, Symbol type) {
        switch (OperatorDictionary.map.get(value)) {
            case PLUS:
            case MINUS:
            case MULTIPLICATION:
            case DIVISION:
            case EXPONENTIAL:
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
            case LESS_THAN:
            case LESS_THAN_OR_EQUAL:
                return new ArithmeticOpNode(level, value);
            case OR:
            case AND:
                return new BooleanOpNode(level, value);
            case STERN:
            case STEM:
                return new StringOpNode(level, value);
            case EQUAL:
            case NOT_EQUAL:
                return new UniversalOpNode(level, value);
            case NOT:
                return new NotNode(level);
            case NEGATION:
                return new NegNode(level);
            case LAMBDA:
                return new LambdaNode(level);
            case PRINT:
                return new PrintNode(level);
            case TAU:
                return new TauNode(level);
            case CONCAT:
                return new ConcatNode(level);
            case AUGMENT:
                return new AugNode(level);
            case IS_INTEGER:
            case IS_TRUTH_VALUE:
            case IS_STRING:
            case IS_TUPLE:
            case IS_FUNCTION:
                return new TypeOpNode(level, value);
            case IToS:
                return new IToSNode(level);
            default:
                return new OperatorNode(level, value);
        }
    }

    public Node clone()throws CloneNotSupportedException{
        return (Node) super.clone();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Symbol getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setType(Symbol type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
