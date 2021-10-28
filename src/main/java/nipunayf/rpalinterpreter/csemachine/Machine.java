package nipunayf.rpalinterpreter.csemachine;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.environment.Environment;
import nipunayf.rpalinterpreter.tree.node.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Evaluates the standardized tree to return the final output.
 */
public class Machine {
    /**
     * Operands are temporarily kept in this stack until a function evaluates them.
     */
    static Stack<Node> stack;

    /**
     * Applicative expression is divided into the control stacks based on lambda definitions.
     */
    static Stack<Node> control;

    /**
     * The current environment of the CSE machine
     */
    static Environment currentEnvironment;



    /**
     * Initializes the CSE machine with the controls, stack and environment.
     * @param root root of the tree
     */
    public static void initialize(Node root) {

    }

    /**
     * Evaluates the current control structures.
     */
    public static String evaluate() throws InvalidCSEMachineException, NoSuchMethodException {
        // Iterating until the control stack is empty
        while(!control.empty()) {
            Node node = control.pop();

            // Substitute the identifier with the appropriate data node
            if (node.getType() == SymbolDictionary.Symbol.IDENTIFIER) {
                stack.push(currentEnvironment.construe(node));
            }

            // Node is a data node. Hence, adding to the stack
            else if (node.getType() != SymbolDictionary.Symbol.OPERATOR) {
                stack.push(node);
            }

            // Node is an operator.
            else {
                node.execute(stack);
            }
        }

        return stack.pop().getValue();
    }
}
