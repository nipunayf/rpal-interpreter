package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.LambdaNode;

import java.util.List;

public class LambdaBuilder extends AbstractBuilder {
    @Override
    public void standardize(Node node) throws NoSuchMethodException, CloneNotSupportedException {
        standardizeLambda(node);
    }

    public static void standardizeLambda(Node node) throws NoSuchMethodException {
        List<Node> children = node.getChildren();
        int numChildren = children.size();

        // Standardizes multi parameter lambda node
        if (numChildren > 2) {
            int baseLevel = node.getLevel();

            Node firstVariable = node.popChild();
            Node secondVariable = node.popChild();

            Node firstLambda = new LambdaNode(baseLevel + 1);
            firstLambda.addNode(secondVariable);

            Node parent = firstLambda;
            Node variable, lambda;
            int level = baseLevel + 2;
            for (int i = 2; i < numChildren - 1; i++) {
                variable = node.popChild();
                lambda = new LambdaNode(level);
                lambda.addNode(variable);
                level++;
                parent.addNode(lambda);
                parent = lambda;
            }
            Node expression = node.popChild();
            parent.addNode(expression);
            node.addNode(firstVariable);
            node.addNode(firstLambda);
        }
    }
}
