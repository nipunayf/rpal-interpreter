package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.SymbolDictionary;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.OperatorNode;

import java.util.ArrayList;
import java.util.List;

public class FunctionFormBuilder extends AbstractBuilder {

    @Override
    public void standardize(Node node) throws NoSuchMethodException {
        int numChildren = node.getChildren().size();
        Node pNode = node.popChild();

        // Construct the lambda node
        Node lambdaNode = new OperatorNode(pNode.getLevel(), "lambda", SymbolDictionary.Symbol.OPERATOR);
        // Add the variables to the lambda node
        Node variable;
        for (int i = 0; i < numChildren - 2; i++) {
            variable = node.popChild();
            variable.increaseLevel();
            lambdaNode.addNode(variable);
        }
        Node eNode = node.popChild();
        eNode.increaseLevel();
        lambdaNode.addNode(eNode);

        // Standardize the node
        node.setValue("=");
        node.addNode(pNode);
        node.addNode(lambdaNode);
    }
}
