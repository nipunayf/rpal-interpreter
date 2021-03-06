package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

/**
 * This class contains the set of all boolean operators execute on booleans
 */
public class BooleanOpNode extends OperatorNode {
    /**
     * Creates a boolean operation node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public BooleanOpNode(int level, String value) {
        super(level, value);
        directlyExecutable = true;
    }

    @Override
    public void execute(Stack<Node> stack) throws InvalidCSEMachineException {
        Node firstData = stack.pop();
        Node secondData = stack.pop();

        // Only valid for boolean operands
        if (firstData.getType() != DataDictionary.Data.BOOLEAN ||
                secondData.getType() != DataDictionary.Data.BOOLEAN)
            throw new InvalidCSEMachineException(this.getValue() + " operator only supports booleans");

        boolean value;
        switch (this.getValue()) {
            case "or":
                value = (Boolean.parseBoolean(firstData.getValue()) || Boolean.parseBoolean(secondData.getValue()));
                break;

            case "&":
                value = (Boolean.parseBoolean(firstData.getValue()) && Boolean.parseBoolean(secondData.getValue()));
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for booleans");
        }

        // Add the output data node to the stack
        stack.push(new DataNode(firstData.getLevel(), Boolean.toString(value), DataDictionary.Data.BOOLEAN));
    }
}
