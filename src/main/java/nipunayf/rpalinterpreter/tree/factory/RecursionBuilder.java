package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;
import nipunayf.rpalinterpreter.tree.node.operators.YNode;

/**
 * Builder that standardizes the rec node
 */
public class RecursionBuilder extends AbstractBuilder {

    @Override
    public void standardize(Node node) throws NoSuchMethodException, CloneNotSupportedException {
        int baseLevel = node.getLevel();
        Node equal = node.popChild();

        // Setting the first X node
        Node x = equal.popChild();
        x.setLevel(baseLevel + 1);

        // Setting a cloned X node
        Node clonedXNode = x.clone();
        clonedXNode.setLevel(baseLevel + 3);

        Node eNode = equal.popChild();

        // Creating the lambda node
        Node lambda = new LambdaNode(baseLevel + 2);
        lambda.addNode(clonedXNode);
        eNode.setLevel(baseLevel + 3);
        lambda.addNode(eNode);

        // Creating the gamma node
        Node gamma = new OperatorNode(equal.getLevel(), "gamma");
        gamma.addNode(new YNode(baseLevel + 2));
        gamma.addNode(lambda);

        // Creating the second gamma node
        node.setValue("=");
        node.addNode(x);
        node.addNode(gamma);
    }
}
