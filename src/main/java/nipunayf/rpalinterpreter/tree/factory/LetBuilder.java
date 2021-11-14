package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;

public class LetBuilder extends AbstractBuilder{

    @Override
    public void standardize(Node node) throws NoSuchMethodException {
        int baseLevel = node.getLevel();

        Node eqNode = node.popChild();
        Node xNode = eqNode.popChild();
        Node eNode = eqNode.popChild();
        eNode.setLevel(baseLevel + 2);
        Node pNode = node.popChild();
        pNode.setLevel(baseLevel + 2);

        // Creating the lambda node
        Node lambda = new LambdaNode(baseLevel + 1);
        lambda.addNode(xNode);
        lambda.addNode(pNode);

        node.setValue("gamma");
        node.addNode(lambda);
        node.addNode(eNode);
    }
}
