package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;

import java.util.List;

public class LetBuilder extends AbstractBuilder{

    @Override
    public void standardize(Node node) throws NoSuchMethodException {
        int baseLevel = node.getLevel();

        Node eqNode = node.popChild();
        Node xNode = eqNode.popChild();
        Node eNode = eqNode.popChild();
        Node pNode = node.popChild();

        // Creating the lambda node
        Node lambda = new LambdaNode(baseLevel + 1, "lambda");
        lambda.addNode(xNode);
        lambda.addNode(pNode);

        node.setValue("gamma");
        node.addNode(lambda);
        node.addNode(eNode);
    }
}
