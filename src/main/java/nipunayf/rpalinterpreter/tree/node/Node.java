package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.DataDictionary.Data;
import nipunayf.rpalinterpreter.OperatorDictionary;
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
    Data type;
    String value;

    /**
     * Creates an AST node
     *
     * @param level level in the tree
     * @param value value of the node
     * @param type  whether it is a data type or an operation type
     */
    Node(int level, String value, Data type) {
        this.level = level;
        this.value = value;
        this.type = type;
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
        Data type;
        String value;

        // The type is a data type
        if (valueStart == '<') {
            if (line.charAt(stoppedIndex + 1) == 'n') {
                return new DataNode(level, "nil", Data.NIL);
            } else if (line.charAt(stoppedIndex + 1) == 'f') {
                return new DataNode(level, "false", Data.BOOLEAN);
            } else if (line.charAt(stoppedIndex + 1) == 't') {
                return new DataNode(level, "true", Data.BOOLEAN);
            } else if (line.charAt(stoppedIndex + 1) == 'd') {
                return new DataNode(level, "dummy", Data.DUMMY);
            } else {
                int valueStopIndex = line.indexOf(':');
                type = DataDictionary.map.get(line.substring(stoppedIndex + 1, valueStopIndex));
                int closeIndex = line.indexOf('>');
                value = line.substring(valueStopIndex + 1, closeIndex);
                return new DataNode(level, value, type);
            }

        } else { // The type is an operator
            value = line.substring(stoppedIndex);
            return generateOperatorNode(level, value);
        }
    }

    /**
     * Generate the specific operator node
     *
     * @param level level in the tree
     * @param value value of the node
     * @return respective operator node
     */
    private static OperatorNode generateOperatorNode(int level, String value) {
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

    /**
     * Duplicates the current node to a new one.
     *
     * @return Duplicated node
     * @throws CloneNotSupportedException clone operation not supported
     */
    public Node clone() throws CloneNotSupportedException {
        return (Node) super.clone();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Data getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setType(Data type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
