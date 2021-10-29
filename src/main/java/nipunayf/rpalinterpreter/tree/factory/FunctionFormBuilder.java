package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

public class FunctionFormBuilder extends AbstractBuilder{

    @Override
    public void standardize(Node node) throws NoSuchMethodException {
        Node pNode = node.popChild();
        Node nNode = node.popChild();
        Node eNode = node.popChild();

        // Construct the lambda node
        Node lambdaNode = new OperatorNode(nNode.getLevel(), "lambda", SymbolDictionary.Symbol.OPERATOR);
        nNode.increaseLevel();
        eNode.increaseLevel();
        lambdaNode.addNode(nNode);
        lambdaNode.addNode(eNode);

        // Standardize the node
        node.setValue("=");
        node.addNode(pNode);
        node.addNode(lambdaNode);
    }
}
