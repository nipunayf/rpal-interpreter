package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;

public class WhereBuilder extends AbstractBuilder {

    @Override
    public void standardize(Node node) throws NoSuchMethodException {
        int baseLevel = node.getLevel();

        Node pNode = node.popChild();
        Node eqNode = node.popChild();
        Node xNode = eqNode.popChild();
        Node eNode = eqNode.popChild();

        // Creating the lambda node
        Node lambda = new LambdaNode(baseLevel + 1);
        lambda.addNode(xNode);
        lambda.addNode(pNode);

        node.setValue("gamma");
        node.addNode(lambda);
        node.addNode(eNode);
    }
}
