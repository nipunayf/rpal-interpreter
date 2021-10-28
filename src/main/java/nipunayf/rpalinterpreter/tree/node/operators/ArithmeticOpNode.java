package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class ArithmeticOpNode extends OperatorNode {
    /**
     * Creates a negation node
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
            throw new InvalidCSEMachineException("Plus operator only supports integers");

        int value;
        switch(this.getValue()) {
            case "+":
                value = (Integer.parseInt(firstData.getValue()) + Integer.parseInt(secondData.getValue()));
                break;

            case "-":
                value = (Integer.parseInt(firstData.getValue()) - Integer.parseInt(secondData.getValue()));
                break;

            case "*":
                value = (Integer.parseInt(firstData.getValue()) * Integer.parseInt(secondData.getValue()));
                break;

            case "/":
                value = (Integer.parseInt(firstData.getValue()) / Integer.parseInt(secondData.getValue()));
                break;

            case "**":
                value = (int) Math.pow(Integer.parseInt(firstData.getValue()), Integer.parseInt(secondData.getValue()));
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for integers");
        }

        stack.push(new DataNode(firstData.getLevel(), Integer.toString(value) , SymbolDictionary.Symbol.INTEGER));
    }
}
