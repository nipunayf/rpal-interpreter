package nipunayf.rpalinterpreter.csemachine;

import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.csemachine.environment.Environment;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.*;

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
     * @param root               root of the tree
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
    public void preorder(Node node) throws NoSuchMethodException {
        // Add the node to the control
        control.push(node);

        // Traverse the children if it is a composite of the tree
        if (node.getType() != SymbolDictionary.Symbol.OPERATOR)
            return;

        switch (OperatorDictionary.map.get(node.getValue())) {
            case LAMBDA: // Create a new control structure if the node is lambda
                ((LambdaNode) node).setAncestorEnvironment(currentEnvironment);
                return;
            case TERNARY: // Create separate structures for true and false cases
                control.pop();
                Node condition = node.popChild();
                Node beta = new BetaOpNode(node.getLevel(), this, node.popChild(), node.popChild());
                control.push(beta);
                preorder(condition);
        }

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
     * @throws NoSuchMethodException      cannot execute a function of a leaf
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
                if (node instanceof ArithmeticOpNode ||
                        node instanceof BooleanOpNode ||
                        node instanceof NegNode ||
                        node instanceof NotNode ||
                        node instanceof BetaOpNode) {
                    node.execute(stack);
                } else if (OperatorDictionary.map.get(node.getValue()) == OperatorDictionary.Operator.GAMMA) {
                    stack.pop().execute(stack);
                } else {
                    stack.push(node);
                }
            }
        }

        return stack.pop();
    }
}
