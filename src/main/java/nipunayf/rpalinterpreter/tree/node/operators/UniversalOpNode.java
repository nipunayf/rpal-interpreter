package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class UniversalOpNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public UniversalOpNode(int level, String value) {
        super(level, value);
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node firstData = stack.pop();
        Node secondData = stack.pop();

        if (firstData.getType() != secondData.getType())
            throw new InvalidCSEMachineException("Both operands should be of same type");

        boolean value;
        switch(OperatorDictionary.map.get(this.getValue())) {
            case EQUAL:
                value = checkEquality(firstData, secondData);
                break;

            case NOT_EQUAL:
                value = checkInequality(firstData, secondData);
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for integers");
        }

        stack.push(new DataNode(firstData.getLevel(), Boolean.toString(value) , SymbolDictionary.Symbol.BOOLEAN));
    }

    private boolean checkEquality(Node firstOperand, Node secondOperand) throws InvalidCSEMachineException {
        switch (firstOperand.getType()) {
            case INTEGER:
                return (Integer.parseInt(firstOperand.getValue()) == Integer.parseInt(secondOperand.getValue()));
            case STRING:
                return (firstOperand.getValue().equals(secondOperand.getValue()));
            case BOOLEAN:
                return (Boolean.parseBoolean(firstOperand.getValue()) == Boolean.parseBoolean(secondOperand.getValue()));
            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for integers");
        }
    }

    private boolean checkInequality(Node firstOperand, Node secondOperand) throws InvalidCSEMachineException {
        switch (firstOperand.getType()) {
            case INTEGER:
                return (Integer.parseInt(firstOperand.getValue()) != Integer.parseInt(secondOperand.getValue()));
            case STRING:
                return (!firstOperand.getValue().equals(secondOperand.getValue()));
            case BOOLEAN:
                return (Boolean.parseBoolean(firstOperand.getValue()) != Boolean.parseBoolean(secondOperand.getValue()));
            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for integers");
        }
    }
}
