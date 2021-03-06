package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.csemachine.Machine;
import nipunayf.rpalinterpreter.csemachine.environment.Environment;
import nipunayf.rpalinterpreter.csemachine.environment.ExtendingEnvironment;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Class for handling the lambda expression.
 * Creates a separate machine and evaluates the CSE machine individually for the lambda expression.
 */
public class LambdaNode extends OperatorNode {
    /**
     * CSE machine specific for the lambda expression
     */
    Machine localMachine;

    /**
     * Environment in which the lambda node being executed
     */
    Environment ancestorEnvironment;

    /**
     * Creates a lambda node
     *
     * @param level level in the tree
     */
    public LambdaNode(int level) {
        super(level, "lambda");
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        List<Node> children = this.getChildren();
        Node variable = children.get(0);

        // Creating the local environment of the lambda definition
        Map<String, Node> map = new HashMap<>();

        // If the lambda node has an n-ary variables assigned to it, followed by a tuple
        if (OperatorDictionary.map.get(variable.getValue()) == OperatorDictionary.Operator.N_ARY) {
            List<Node> nAryChildren = variable.getChildren();
            List<Node> tauChildren = stack.pop().getChildren();

            // Maps the variables to the nodes in the tuple
            for (int i = 0; i < nAryChildren.size(); i++) {
                map.put(nAryChildren.get(i).getValue(), tauChildren.get(i));
            }
        }

        // If the lambda node has only one variable assigned, followed by a node.
        else {
            map.put(variable.getValue(), stack.pop());
        }
        Environment localEnvironment = new ExtendingEnvironment(map, ancestorEnvironment);

        // Creating a separate cse machine to evaluate the lambda function
        localMachine = new Machine(localEnvironment, children.get(children.size() - 1));

        Node finalOutput = localMachine.evaluate();

        if (finalOutput != null) stack.push(finalOutput);
    }

    public void setAncestorEnvironment(Environment ancestorEnvironment) {
        this.ancestorEnvironment = ancestorEnvironment;
    }
}
