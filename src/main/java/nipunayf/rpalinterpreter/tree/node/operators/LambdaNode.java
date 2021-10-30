package nipunayf.rpalinterpreter.tree.node.operators;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary;
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

public class LambdaNode extends OperatorNode {
    Machine localMachine;
    Environment ancestorEnvironment;

    /**
     * Creates a lambda node
     *
     * @param level level in the tree
     * @param value value of the node
     */
    public LambdaNode(int level, String value) {
        super(level, value);
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        List<Node> children = this.getChildren();
        Node variable = children.get(0);

        // Creating the local environment of the lambda definition
        Map<String, Node> map = new HashMap<>();
        if (OperatorDictionary.map.get(variable.getValue()) == OperatorDictionary.Operator.N_ARY) {
            List<Node> nAryChildren = variable.getChildren();
            List<Node> tauChildren = stack.pop().getChildren();

            for (int i = 0; i < nAryChildren.size(); i++) {
                map.put(nAryChildren.get(i).getValue(), tauChildren.get(i));
            }
        } else {
            map.put(variable.getValue(), stack.pop());
        }
        Environment localEnvironment = new ExtendingEnvironment(map, ancestorEnvironment);

        // Creating a separate cse machine to evaluate the lambda function
        localMachine = new Machine(localEnvironment, children.get(1));
        Node finalOutput = localMachine.evaluate();

        stack.push(finalOutput);
    }
}
