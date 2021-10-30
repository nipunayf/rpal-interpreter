package nipunayf.rpalinterpreter.tree.node.operators;

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
     * @param type  whether it is a data type or an operation type
     */
    public LambdaNode(int level, String value) {
        super(level, value);
    }

    @Override
    public void execute(Stack<Node> stack) throws NoSuchMethodException, InvalidCSEMachineException {
        List<Node> children = this.getChildren();
        Node variable = children.get(0);

        Map<String, Node> map = new HashMap<>() {{
            put(variable.getValue(), stack.pop());
        }};
        Environment localEnvironment = new ExtendingEnvironment(map, ancestorEnvironment);

        localMachine = new Machine(localEnvironment, children.get(1));
        Node finalOutput = localMachine.evaluate();

        stack.push(finalOutput);
    }
}
