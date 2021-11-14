package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;

/**
 * Builder that standardizes the FunctionForm node
 */
public class FunctionFormBuilder extends AbstractBuilder {

    @Override
    public void standardize(Node node) throws NoSuchMethodException {
        int numChildren = node.getChildren().size();
        Node pNode = node.popChild();

        // Construct the lambda node
        Node lambdaNode = new LambdaNode(pNode.getLevel());
        // Add the variables to the lambda node
        Node variable;
        for (int i = 0; i < numChildren - 2; i++) {
            variable = node.popChild();
            variable.setLevel(variable.getLevel() + 1);
            lambdaNode.addNode(variable);
        }
        Node eNode = node.popChild();
        eNode.setLevel(eNode.getLevel() + 1);
        lambdaNode.addNode(eNode);
        LambdaBuilder.standardizeLambda(lambdaNode);

        // Standardize the node
        node.setValue("=");
        node.addNode(pNode);
        node.addNode(lambdaNode);
    }
}
