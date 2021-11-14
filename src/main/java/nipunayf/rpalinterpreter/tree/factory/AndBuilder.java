package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import nipunayf.rpalinterpreter.tree.node.operators.TauNode;

/**
 * Builder that standardizes the AND node
 */
public class AndBuilder extends AbstractBuilder {
    @Override
    public void standardize(Node node) throws NoSuchMethodException, CloneNotSupportedException {
        int baseLevel = node.getLevel();
        Node nAry = new OperatorNode(baseLevel + 1, ",");
        Node tau = new TauNode(baseLevel + 1);

        Node x, e, eq;
        int numEqs = node.getChildren().size();
        for (int i = 0; i < numEqs; i++) {
            eq = node.popChild();
            x = eq.popChild();
            x.setLevel(baseLevel + 2);
            e = eq.popChild();
            e.setLevel(baseLevel + 2);

            nAry.addNode(x);
            tau.addNode(e);
        }

        node.setValue("gamma");
        node.addNode(nAry);
        node.addNode(tau);
    }
}
