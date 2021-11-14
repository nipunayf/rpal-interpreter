package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Objects;
import java.util.Stack;

/**
 * Handles the inversion of the sign
 */
public class NegNode extends OperatorNode {
    /**
     * Creates a negation node
     *
     * @param level level in the tree
     */
    public NegNode(int level) {
        super(level, "neg");
        directlyExecutable = true;
    }

    @Override
    public void execute(Stack<Node> stack) throws InvalidCSEMachineException {
        Node data = stack.pop();

        // Only valid for integers
        if (data.getType() != DataDictionary.Data.INTEGER)
            throw new InvalidCSEMachineException("Negation operator only supports integers");

        String dataValue = data.getValue();
        String value;

        // Adding a negative sign to 0 does not make a difference.
        if (Objects.equals(dataValue, "0"))
            value = "0";

        // Invert the sign of the integer
        else
            value = dataValue.charAt(0) == '-' ? dataValue.substring(1) : "-" + dataValue;

        stack.push(new DataNode(data.getLevel(), value, data.getType()));
    }
}
