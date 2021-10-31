package nipunayf.rpalinterpreter.csemachine.environment;

import nipunayf.rpalinterpreter.tree.node.Node;

import java.util.Map;

public class ExtendingEnvironment implements Environment {
    public final Map<String, Node> map;
    private final Environment ancestor;

    /**
     * Initializes the environment
     *
     * @param map      current environment
     * @param ancestor previous environment
     */
    public ExtendingEnvironment(Map<String, Node> map, Environment ancestor) {
        this.map = map;
        this.ancestor = ancestor;
    }

    @Override
    public Node construe(Node identifier) {
        //Check if the current environment has the value
        if (this.map.containsKey(identifier.getValue())) {
            return this.map.get(identifier.getValue());
        }

        //Check if the previous environment has the value.
        return ancestor.construe(identifier);
    }

    @Override
    public void printEnvironment() {
        System.out.print("C_ENV: ");
        for (Map.Entry<String, Node> entry : this.map.entrySet()) {
            System.out.print(entry.getKey() + " -> " + entry.getValue().getValue() + ", ");
        }
        System.out.print("\nS_ENV: ");
        ancestor.getEnvironment();
    }

    @Override
    public void getEnvironment() {
        for (Map.Entry<String, Node> entry : this.map.entrySet()) {
            System.out.print(entry.getKey() + " -> " + entry.getValue().getValue() + ", ");
        }
        ancestor.getEnvironment();
    }
}
