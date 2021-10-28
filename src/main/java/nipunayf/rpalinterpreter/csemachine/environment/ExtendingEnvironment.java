package nipunayf.rpalinterpreter.csemachine.environment;

import nipunayf.rpalinterpreter.tree.node.Node;

import java.util.Map;

public class ExtendingEnvironment implements Environment {
    private final Map<Node, Node> map;
    private final Environment ancestor;

    /**
     * Initializes the environment
     *
     * @param map      current environment
     * @param ancestor previous environment
     */
    public ExtendingEnvironment(Map<Node, Node> map, Environment ancestor) {
        this.map = map;
        this.ancestor = ancestor;
    }

    @Override
    public Node construe(Node identifier) {
        //Check if the current environment has the value
        if (this.map.containsKey(identifier)) {
            return this.map.get(identifier);
        }

        //Check if the previous environment has the value.
        return ancestor.construe(identifier);
    }
}
