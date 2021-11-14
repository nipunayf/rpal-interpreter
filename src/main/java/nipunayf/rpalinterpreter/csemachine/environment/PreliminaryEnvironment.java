package nipunayf.rpalinterpreter.csemachine.environment;

import nipunayf.rpalinterpreter.tree.node.Node;

import java.util.Map;
import java.util.NoSuchElementException;

public class PreliminaryEnvironment implements Environment {
    private final Map<String, Node> map;

    /**
     * Initializes the environment
     *
     * @param map current environment
     */
    public PreliminaryEnvironment(Map<String, Node> map) {
        this.map = map;
    }

    @Override
    public Node construe(Node identifier) {
        //Checks if the preliminary environment has the value
        if (this.map.containsKey(identifier.getValue())) {
            return this.map.get(identifier.getValue());
        } else throw new NoSuchElementException("Unknown identifier: " + identifier.getValue());
    }

    @Override
    public void printEnvironment() {
        System.out.print("C_ENV: PRELIMINARY");
    }

    @Override
    public void getEnvironment() {
        System.out.print("PRELIMINARY, ");
    }
}
