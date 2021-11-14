package nipunayf.rpalinterpreter.csemachine;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.csemachine.environment.Environment;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import nipunayf.rpalinterpreter.tree.node.operators.BetaNode;
import nipunayf.rpalinterpreter.tree.node.operators.EtaNode;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;
import nipunayf.rpalinterpreter.tree.node.operators.TauNode;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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

    static int steps = 0;

    /**
     * Enable this flag to print the CSE machine step-by-step
     */
    static boolean PRINT_MODE = false;

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
        if (node.getType() != DataDictionary.Data.OPERATOR)
            return;

        switch (OperatorDictionary.map.get(node.getValue())) {
            case LAMBDA: // Create a new control structure if the node is lambda
                ((LambdaNode) node).setAncestorEnvironment(currentEnvironment);
                return;
            case TERNARY: // Create separate structures for true and false cases
                control.pop();
                List<Node> children = node.getChildren();
                Node condition = children.get(0);
                Node beta = new BetaNode(node.getLevel(), this, children.get(1), children.get(2));
                control.push(beta);
                preorder(condition);
                return;
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
        printStepByStep();

        // Iterating until the control stack is empty
        while (!control.empty()) {
            Node node = control.pop();

            // Substitute the identifier with the appropriate data node
            if (node.getType() == DataDictionary.Data.IDENTIFIER) {
                stack.push(currentEnvironment.construe(node));
            }

            // Node is a data node. Hence, adding to the stack
            else if (node.getType() != DataDictionary.Data.OPERATOR) {
                stack.push(node);
            }

            // Node is an operator.
            else {
                if (((OperatorNode) node).isDirectlyExecutable()) {
                    node.execute(stack);
                } else if (OperatorDictionary.map.get(node.getValue()) == OperatorDictionary.Operator.GAMMA) {
                    Node operator = stack.pop();
                    if (OperatorDictionary.map.get(operator.getValue()) == OperatorDictionary.Operator.TAU)
                        ((TauNode) operator).setExecutedByGamma();
                    if (OperatorDictionary.map.get(operator.getValue()) == OperatorDictionary.Operator.ETA)
                        ((EtaNode) operator).setMachine(this);
                    if (operator.getType() == DataDictionary.Data.OPERATOR) operator.execute(stack);
                    else stack.push(operator);
                } else {
                    stack.push(node);
                }
            }
            printStepByStep();
        }

        return stack.isEmpty() ? null : stack.pop();
    }

    /**
     * Print the CSE machine step by step.
     */
    private void printStepByStep() {
        if (!PRINT_MODE) return;

        System.out.println("========STEP " + steps + "========");

        String joinedControl = control.stream()
                .map(Node::getValue)
                .collect(Collectors.joining(", "));
        System.out.println("CONTROL: " + joinedControl);

        String joinedStack = stack.stream()
                .map(Node::getValue)
                .collect(Collectors.joining(", "));
        System.out.println("STACK: " + joinedStack);

        currentEnvironment.printEnvironment();

        System.out.println("\n======================\n");
        steps++;
    }

    public static void setPrintMode() {
        PRINT_MODE = true;
    }
}
