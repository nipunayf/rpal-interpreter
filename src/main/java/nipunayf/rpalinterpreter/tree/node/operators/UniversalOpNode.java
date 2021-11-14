package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * This class contains the set of all operators that works on any data types
 */
public class UniversalOpNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public UniversalOpNode(int level, String value) {
        super(level, value);
        directlyExecutable = true;
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node firstData = stack.pop();
        Node secondData = stack.pop();

        boolean value;
        switch (OperatorDictionary.map.get(this.getValue())) {
            case EQUAL:
                if (firstData.getType() == secondData.getType()) {
                    value = firstData.getType() != DataDictionary.Data.INTEGER &&
                            firstData.getType() != DataDictionary.Data.STRING &&
                            firstData.getType() != DataDictionary.Data.BOOLEAN &&
                            firstData.getType() != DataDictionary.Data.OPERATOR ||
                            checkEquality(firstData, secondData);
                } else {
                    value = false;
                }
                break;

            case NOT_EQUAL:
                if (firstData.getType() == secondData.getType()) {
                    value = (firstData.getType() == DataDictionary.Data.INTEGER ||
                            firstData.getType() == DataDictionary.Data.STRING ||
                            firstData.getType() == DataDictionary.Data.BOOLEAN ||
                            firstData.getType() == DataDictionary.Data.OPERATOR) && checkInequality(firstData, secondData);
                } else {
                    value = true;
                }
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for integers");
        }

        stack.push(new DataNode(firstData.getLevel(), Boolean.toString(value), DataDictionary.Data.BOOLEAN));
    }

    /**
     * Check if the two elements are equal
     *
     * @param firstOperand  first element of the stack
     * @param secondOperand second element of the stack
     * @return true if elements are equal
     * @throws InvalidCSEMachineException if the elements are not of the supported types
     */
    private boolean checkEquality(Node firstOperand, Node secondOperand) throws InvalidCSEMachineException, NoSuchMethodException {
        if (OperatorDictionary.map.get(firstOperand.getValue()) == OperatorDictionary.Operator.TAU &&
                OperatorDictionary.map.get(secondOperand.getValue()) == OperatorDictionary.Operator.TAU) {
            return checkTupleEquality(firstOperand, secondOperand);
        } else {
            switch (firstOperand.getType()) {
                case INTEGER:
                    return (Integer.parseInt(firstOperand.getValue()) == Integer.parseInt(secondOperand.getValue()));
                case STRING:
                    return (firstOperand.getValue().equals(secondOperand.getValue()));
                case BOOLEAN:
                    return (Boolean.parseBoolean(firstOperand.getValue()) == Boolean.parseBoolean(secondOperand.getValue()));
                default:
                    return false;
            }

        }
    }

    /**
     * Check if the two elements are not equal
     *
     * @param firstOperand  first element of the stack
     * @param secondOperand second element of the stack
     * @return true if elements are not equal
     * @throws InvalidCSEMachineException if the elements are not of the supported types
     */
    private boolean checkInequality(Node firstOperand, Node secondOperand) throws InvalidCSEMachineException, NoSuchMethodException {
        if (OperatorDictionary.map.get(firstOperand.getValue()) == OperatorDictionary.Operator.TAU &&
                OperatorDictionary.map.get(secondOperand.getValue()) == OperatorDictionary.Operator.TAU) {
            return !checkTupleEquality(firstOperand, secondOperand);
        } else {
            switch (firstOperand.getType()) {
                case INTEGER:
                    return (Integer.parseInt(firstOperand.getValue()) != Integer.parseInt(secondOperand.getValue()));
                case STRING:
                    return (!firstOperand.getValue().equals(secondOperand.getValue()));
                case BOOLEAN:
                    return (Boolean.parseBoolean(firstOperand.getValue()) != Boolean.parseBoolean(secondOperand.getValue()));
                default:
                    return true;
            }
        }

    }

    private boolean checkTupleEquality(Node firstOperand, Node secondOperand) throws NoSuchMethodException {
        List<Node> firstChildren = firstOperand.getChildren();
        List<Node> secondChildren = secondOperand.getChildren();

        if (firstChildren.size() == secondChildren.size()) {
            int numChildren = firstChildren.size();

            Node firstChild, secondChild;
            for (int i=0; i<numChildren; i++) {
                firstChild = firstChildren.get(i);
                secondChild = secondChildren.get(i);

                if (firstChild.getType() ==  secondChild.getType()) {
                    if (!Objects.equals(firstChild.getValue(), secondChild.getValue())) return false;
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
