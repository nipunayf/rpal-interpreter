package nipunayf.rpalinterpreter.csemachine;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.environment.Environment;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
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
    Stack<Node> stack;

    /**
     * Applicative expression is divided into the control stacks based on lambda definitions.
     */
    Stack<Node> control;

    /**
     * The current environment of the CSE machine
     */
    Environment currentEnvironment;

    /**
     * Initializes the CSE machine with the controls, stack and environment.
     *
     * @param currentEnvironment local environment of the machine
     * @param root root of the tree
     * @throws NoSuchMethodException leaf has no children
     */
    public Machine(Environment currentEnvironment, Node root) throws NoSuchMethodException {
        this.currentEnvironment = currentEnvironment;
        stack = new Stack<>();
        control = new Stack<>();

        preorder(root);
    }

    /**
     * Performs a pre-order traversal of the ST tree to create the controls
     *
     * @param node to be traversed
     * @throws NoSuchMethodException leaf has no children
     */
    private void preorder(Node node) throws NoSuchMethodException {
        // Add the node to the control
        control.push(node);

        // Traverse the children if it is a composite of the tree
        if (node.getType() != SymbolDictionary.Symbol.OPERATOR)
            return;

        // Traverse the children from left to right
        List<Node> children = node.getChildren();
        for (Node child : children) {
            preorder(child);
        }
    }

    /**
     * Evaluates the current control structures.
     *
     * @return final value
     * @throws InvalidCSEMachineException input is not valid
     * @throws NoSuchMethodException cannot execute a function of a leaf
     */
    public Node evaluate() throws InvalidCSEMachineException, NoSuchMethodException {
        // Iterating until the control stack is empty
        while (!control.empty()) {
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

        return stack.pop();
    }
}
