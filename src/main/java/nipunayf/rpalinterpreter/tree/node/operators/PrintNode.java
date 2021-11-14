package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.Interpreter;
import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Handles the printing of the values of a node
 */
public class PrintNode extends OperatorNode {
    /**
     * Creates an operator node
     *
     * @param level level in the tree
     */
    public PrintNode(int level) {
        super(level, "Print");
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        Node output = stack.pop();

        // If the node is tau, print all the elements separated by a comma
        if (OperatorDictionary.map.get(output.getValue()) == OperatorDictionary.Operator.TAU) {
            String joinedResult = output.getChildren().stream()
                    .map(Node::getValue)
                    .collect(Collectors.joining(", "));
            System.out.println("(" + joinedResult + ")");
        }

        // If the node is lambda, express it as #<fn: [variable]>
        else if (OperatorDictionary.map.get(output.getValue()) == OperatorDictionary.Operator.LAMBDA) {
            System.out.println("#<fn" + output.getValue() + ">");
        }

        // Prints the value of any other nodes
        else {
            System.out.println(output.getValue());
        }

        // Stores the output in the interpreter for assertions in testing
        Interpreter.outputValue = output;
    }
}
