package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import javax.xml.crypto.Data;
import java.util.Stack;


public class TauOpNode extends OperatorNode {
    /**
     * Creates a tau node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public TauOpNode(int level, String value) {
        super(level, value);
    }

    public void execute(Stack<Node> stack) throws InvalidCSEMachineException, NoSuchMethodException {
        Node data = stack.pop();

        if (OperatorDictionary.map.get(data.getValue()) != OperatorDictionary.Operator.TAU
        && data.getType() != SymbolDictionary.Symbol.TUPLE)
            throw new InvalidCSEMachineException(this.getValue() + " operator only supports tuples");

        switch (this.getValue()) {
            case "Order":
                int size;
                if (data.getType() == SymbolDictionary.Symbol.TUPLE) {
                    size = 0;
                } else {
                    size = data.getChildren().size();
                }
                stack.push(new DataNode(this.getLevel(), Integer.toString(size), SymbolDictionary.Symbol.INTEGER));
                break;

            case "Nil":
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for tau");
        }
    }
}
