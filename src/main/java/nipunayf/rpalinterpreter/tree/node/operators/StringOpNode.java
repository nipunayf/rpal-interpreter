package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class StringOpNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public StringOpNode(int level, String value) {
        super(level, value);
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node string = stack.pop();

        if (string.getType() != SymbolDictionary.Symbol.STRING)
            throw new InvalidCSEMachineException(this.getValue() + " operator only supports strings");

        String outputString;
        switch (OperatorDictionary.map.get(this.getValue())) {
            case STERN:
                outputString = string.getValue().substring(1);
                stack.push(new DataNode(string.getLevel(), outputString, SymbolDictionary.Symbol.STRING));
                break;

            case STEM:
                outputString = string.getValue().substring(0, string.getValue().length() - 1);
                stack.push(new DataNode(string.getLevel(), outputString, SymbolDictionary.Symbol.STRING));
                break;

            case PRINT:
                System.out.println(string.getValue());
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for strings");
        }

    }
}
