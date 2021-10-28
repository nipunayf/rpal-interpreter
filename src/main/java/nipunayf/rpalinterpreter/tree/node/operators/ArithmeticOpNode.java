package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
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
     * @param type  whether it is a data type or an operation type
     */
    public ArithmeticOpNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
    }

    @Override
    public void execute(Stack<Node> stack) throws InvalidCSEMachineException {
        Node firstData = stack.pop();
        Node secondData = stack.pop();

        if (firstData.getType() != SymbolDictionary.Symbol.INTEGER ||
                secondData.getType() != SymbolDictionary.Symbol.INTEGER)
            throw new InvalidCSEMachineException("Arithmetic operator only supports integers");

        int intValue = 0;
        boolean boolValue = false;
        boolean isInteger = false;
        switch(this.getValue()) {
            case "+":
                intValue = (Integer.parseInt(firstData.getValue()) + Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case "-":
                intValue = (Integer.parseInt(firstData.getValue()) - Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case "*":
                intValue = (Integer.parseInt(firstData.getValue()) * Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case "/":
                intValue = (Integer.parseInt(firstData.getValue()) / Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case "**":
                intValue = (int) Math.pow(Integer.parseInt(firstData.getValue()), Integer.parseInt(secondData.getValue()));
                isInteger = true;
                break;

            case "gr":
                boolValue = (Integer.parseInt(firstData.getValue()) > Integer.parseInt(secondData.getValue()));
                break;

            case "ge":
                boolValue = (Integer.parseInt(firstData.getValue()) >= Integer.parseInt(secondData.getValue()));
                break;

            case "ls":
                boolValue = (Integer.parseInt(firstData.getValue()) < Integer.parseInt(secondData.getValue()));
                break;

            case "le":
                boolValue = (Integer.parseInt(firstData.getValue()) <= Integer.parseInt(secondData.getValue()));
                break;

            case "eq":
                boolValue = (Integer.parseInt(firstData.getValue()) == Integer.parseInt(secondData.getValue()));
                break;

            case "ne":
                boolValue = (Integer.parseInt(firstData.getValue()) != Integer.parseInt(secondData.getValue()));
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for integers");
        }

        String value = isInteger ? Integer.toString(intValue) : Boolean.toString(boolValue);
        stack.push(new DataNode(firstData.getLevel(), value , SymbolDictionary.Symbol.INTEGER));
    }
}
