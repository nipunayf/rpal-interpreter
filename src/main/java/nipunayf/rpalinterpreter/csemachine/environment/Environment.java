package nipunayf.rpalinterpreter.csemachine.environment;

import nipunayf.rpalinterpreter.tree.node.Node;

/**
 * Defines the identifier mapping for the environment in which the nodes are processed.
 * Follows the decorator design pattern, so the environment can extend dynamically.
 */
public interface Environment {
    /**
     * Returns the value for the identifier
     *
     * @param identifier requested node
     * @return value of the identifier
     */
    Node construe(Node identifier);

    /**
     * Visualizes the current and stacked environment;
     */
    void printEnvironment();

    void getEnvironment();
}
