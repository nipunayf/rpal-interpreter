package nipunayf.rpalinterpreter.csemachine;

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
    private static Stack<Node> stack;

    /**
     * Applicative expression is divided into the control stacks based on lambda definitions.
     */
    private static HashMap<Integer, List<Node>> control;

    /**
     * The current environment of the CSE machine
     */
    private static int currentEnvironment;



    /**
     * Initializes the CSE machine with the controls, stack and environment.
     * @param root root of the tree
     */
    public static void initialize(Node root) {

    }

    /**
     * Evaluates the current control structures.
     */
    public static void evaluate() {

    }
}
