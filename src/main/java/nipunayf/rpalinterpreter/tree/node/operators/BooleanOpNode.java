package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class BooleanOpNode extends OperatorNode {
    /**
     * Creates a boolean operation node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public BooleanOpNode(int level, String value) {
        super(level, value);
    }

    @Override
    public void execute(Stack<Node> stack) throws InvalidCSEMachineException {
        Node firstData = stack.pop();
        Node secondData = stack.pop();

        if (firstData.getType() != SymbolDictionary.Symbol.BOOLEAN ||
                secondData.getType() != SymbolDictionary.Symbol.BOOLEAN)
            throw new InvalidCSEMachineException(this.getValue() + " operator only supports booleans");

        boolean value;
        switch(this.getValue()) {
            case "or":
                value = (Boolean.parseBoolean(firstData.getValue()) || Boolean.parseBoolean(secondData.getValue()));
                break;

            case "&":
                value = (Boolean.parseBoolean(firstData.getValue()) && Boolean.parseBoolean(secondData.getValue()));
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for booleans");
        }

        stack.push(new DataNode(firstData.getLevel(), Boolean.toString(value) , SymbolDictionary.Symbol.INTEGER));
    }
}
