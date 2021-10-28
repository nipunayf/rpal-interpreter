package nipunayf.rpalinterpreter.csemachine.environment;

import nipunayf.rpalinterpreter.tree.node.Node;

import java.util.Map;
import java.util.NoSuchElementException;

public class PreliminaryEnvironment implements Environment{
    private final Map<Node, Node> map;

    /**
     * Initializes the environment
     *
     * @param map current environment
     */
    public PreliminaryEnvironment(Map<Node, Node> map) {
        this.map = map;
    }

    @Override
    public Node construe(Node identifier) {
        //Checks if the preliminary environment has the value
        if (this.map.containsKey(identifier)) {
            return this.map.get(identifier);
        }

        else throw new NoSuchElementException("Unknown identifier");
    }
}
