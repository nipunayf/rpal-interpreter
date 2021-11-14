package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class ArithmeticOpNode extends OperatorNode {
    /**
     * Creates an arithmetic operation node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public ArithmeticOpNode(int level, String value) {
        super(level, value);
        directlyExecutable = true;
    }

    @Override
    public void execute(Stack<Node> stack) throws InvalidCSEMachineException {
        Node firstData = stack.pop();
        Node secondData = stack.pop();

        if (firstData.getType() != DataDictionary.Data.INTEGER ||
                secondData.getType() != DataDictionary.Data.INTEGER)
            throw new InvalidCSEMachineException("Arithmetic operator only supports integers");

        int intValue = 0;
        boolean boolValue = false;
        boolean isInteger = false;
        switch (OperatorDictionary.map.get(this.getValue())) {
            case PLUS:
                intValue = (Integer.parseInt(firstData.getValue()) + Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case MINUS:
                intValue = (Integer.parseInt(firstData.getValue()) - Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case MULTIPLICATION:
                intValue = (Integer.parseInt(firstData.getValue()) * Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case DIVISION:
                intValue = (Integer.parseInt(firstData.getValue()) / Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case EXPONENTIAL:
                intValue = (int) Math.pow(Integer.parseInt(firstData.getValue()), Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case GREATER_THAN:
                boolValue = (Integer.parseInt(firstData.getValue()) > Integer.parseInt(secondData.getValue()));
                break;

            case GREATER_THAN_OR_EQUAL:
                boolValue = (Integer.parseInt(firstData.getValue()) >= Integer.parseInt(secondData.getValue()));
                break;

            case LESS_THAN:
                boolValue = (Integer.parseInt(firstData.getValue()) < Integer.parseInt(secondData.getValue()));
                break;

            case LESS_THAN_OR_EQUAL:
                boolValue = (Integer.parseInt(firstData.getValue()) <= Integer.parseInt(secondData.getValue()));
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for integers");
        }

        String value = isInteger ? Integer.toString(intValue) : Boolean.toString(boolValue);
        stack.push(new DataNode(firstData.getLevel(), value, DataDictionary.Data.INTEGER));
    }
}
