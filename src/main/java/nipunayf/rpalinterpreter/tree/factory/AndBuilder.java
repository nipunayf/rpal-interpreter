package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import nipunayf.rpalinterpreter.tree.node.operators.TauNode;

public class AndBuilder extends AbstractBuilder {
    @Override
    public void standardize(Node node) throws NoSuchMethodException, CloneNotSupportedException {
        int baseLevel = node.getLevel();
        Node nAry = new OperatorNode(baseLevel+1, ",");
        Node tau = new TauNode(baseLevel+1);

        Node x, e, eq;
        for (int i = 0; i <= node.getChildren().size(); i++) {
            eq = node.popChild();
            x = eq.popChild();
            e = eq.popChild();

            nAry.addNode(x);
            tau.addNode(e);
        }

        node.setValue("gamma");
        node.addNode(nAry);
        node.addNode(tau);
    }
}
