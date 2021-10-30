package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;

public class RecursionBuilder extends AbstractBuilder {

    @Override
    public void standardize(Node node) throws NoSuchMethodException, CloneNotSupportedException {
        int baseLevel = node.getLevel();
        Node equalChild = node.popChild();

        // Setting the first X node
        Node xNode = equalChild.popChild();
        xNode.setLevel(baseLevel + 1);

        // Setting a cloned X node
        Node clonedXNode = xNode.clone();
        clonedXNode.setLevel(baseLevel + 3);

        Node eNode = equalChild.popChild();

        // Creating the lambda node
        Node lambda = new LambdaNode(baseLevel + 2, "lambda");
        lambda.addNode(clonedXNode);
        eNode.setLevel(baseLevel + 3);
        lambda.addNode(eNode);

        // Creating the gamma node
        Node gamma = new OperatorNode(equalChild.getLevel(), "gamma");
        gamma.addNode(new OperatorNode(baseLevel + 2, "Y"));
        gamma.addNode(lambda);

        // Creating the second gamma node
        node.setValue("=");
        node.addNode(xNode);
        node.addNode(gamma);
    }
}
