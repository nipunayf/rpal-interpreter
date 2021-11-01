package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;

public class TypeOpNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public TypeOpNode(int level, String value) {
        super(level, value);
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node node = stack.pop();

        boolean output;
        switch (OperatorDictionary.map.get(this.getValue())) {
            case IS_INTEGER:
                output = node.getType() == SymbolDictionary.Symbol.INTEGER;
                break;

            case IS_TRUTH_VALUE:
                output = node.getType() == SymbolDictionary.Symbol.BOOLEAN;
                break;

            case IS_STRING:
                output = node.getType() == SymbolDictionary.Symbol.STRING;
                break;

            case IS_TUPLE:
                output = node.getType() == SymbolDictionary.Symbol.TUPLE || node instanceof TauNode;
                break;

            case IS_DUMMY:
                output = node.getType() == SymbolDictionary.Symbol.DUMMY;
                break;

            case IS_FUNCTION:
                output = node instanceof LambdaNode;
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for booleans");
        }
        stack.push(new DataNode(this.getLevel(), Boolean.toString(output), SymbolDictionary.Symbol.BOOLEAN));
    }
}
