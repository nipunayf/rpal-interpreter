package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.DataNode;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

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
                && data.getType() != DataDictionary.Data.TUPLE)
            throw new InvalidCSEMachineException(this.getValue() + " operator only supports tuples");

        switch (this.getValue()) {
            case "Order":
                int size;
                if (data.getType() == DataDictionary.Data.TUPLE) {
                    size = 0;
                } else {
                    size = data.getChildren().size();
                }
                stack.push(new DataNode(this.getLevel(), Integer.toString(size), DataDictionary.Data.INTEGER));
                break;

            case "Null":
                stack.push((new DataNode(this.getLevel(), Boolean.toString(data.getValue().equals("nil")), DataDictionary.Data.BOOLEAN)));
                break;

            default:
                throw new InvalidCSEMachineException("Invalid operator " + this.getValue() + " for tau");
        }
    }
}
