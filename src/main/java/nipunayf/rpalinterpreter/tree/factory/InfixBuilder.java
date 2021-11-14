package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

public class InfixBuilder extends AbstractBuilder {

    @Override
    public void standardize(Node node) throws NoSuchMethodException, CloneNotSupportedException {
        int baseLevel = node.getLevel();
        Node e1 = node.popChild();
        Node n = node.popChild();
        Node e2 = node.popChild();

        // Create the child gamma e
        Node gamma = new OperatorNode(baseLevel + 1, "gamma");
        n.setLevel(baseLevel + 2);
        gamma.addNode(n);
        e1.setLevel(baseLevel + 2);
        gamma.addNode(e1);

        node.setValue("gamma");
        node.addNode(gamma);
        node.addNode(e2);
    }
}
