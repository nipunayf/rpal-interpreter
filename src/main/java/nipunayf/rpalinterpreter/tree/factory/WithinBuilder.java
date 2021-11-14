package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;

/**
 * Builder that standardizes the within node
 */
public class WithinBuilder extends AbstractBuilder {

    @Override
    public void standardize(Node node) throws NoSuchMethodException, CloneNotSupportedException {
        int baseLevel = node.getLevel();
        Node firstEqChild = node.popChild();
        Node secondEqChild = node.popChild();

        Node x1 = firstEqChild.popChild();
        Node e1 = firstEqChild.popChild();

        Node x2 = secondEqChild.popChild();
        Node e2 = secondEqChild.popChild();

        // Creating the lambda node
        Node lambda = new LambdaNode(baseLevel + 2);
        x1.setLevel(baseLevel + 3);
        e2.setLevel(baseLevel + 3);
        lambda.addNode(x1);
        lambda.addNode(e2);

        // Creating the gamma node
        Node gamma = new OperatorNode(baseLevel + 1, "gamma");
        e1.setLevel(baseLevel + 2);
        gamma.addNode(lambda);
        gamma.addNode(e1);

        node.setValue("=");
        x2.setLevel(baseLevel + 1);
        node.addNode(x2);
        node.addNode(gamma);
    }
}
