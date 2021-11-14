package nipunayf.rpalinterpreter.tree;

import nipunayf.rpalinterpreter.DataDictionary;
import nipunayf.rpalinterpreter.OperatorDictionary;
import nipunayf.rpalinterpreter.tree.factory.*;
import nipunayf.rpalinterpreter.tree.node.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Generates a standardized tree.
 */
public class Generator {
    /**
     * Generates a full standardized tree
     *
     * @param fileName input file
     * @return root node of the tree
     * @throws IOException cannot read line
     */
    public static Node generateTree(String fileName) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, CloneNotSupportedException {
        // Refers to the nodes that are not standardized yet
        Map<Integer, Node> pointerMap = new HashMap<>();

        // Initializing the variables
        String line;
        Node parentNode = null;
        Node prevNode = null;
        Node node;
        int currentLevel = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        // Process each line
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("")) break;
            node = Node.generateNode(line);

            // If the level of the tree gets deeper. Note that only one level can be increased
            if (node.getLevel() > currentLevel) {
                // If the previous node is not null, then the current node is a child node of it
                // If the previous node is null, then the current node is the root.
                if (prevNode != null) {
                    prevNode.addNode(node);
                }
                parentNode = prevNode;
                prevNode = node;
                currentLevel = node.getLevel();
            }

            // If the node is a sibling or first of the level
            else if (node.getLevel() == currentLevel) {
                // If the level does not increase, then the current node is a
                // child node of the sibling's parent node as well
                if (parentNode != null) {
                    parentNode.addNode(node);
                } else parentNode = node;
                prevNode = node;
            }

            // If the node is a level higher to the current level
            else {
                // If the node level is x, then standardize the nodes from x+1 to the most depth level.
                for (int i = currentLevel; i >= node.getLevel(); i--) {
                    // Removes the node when standardizing
                    Node removedNode = pointerMap.remove(i);
                    if (removedNode != null) standardizeNode(removedNode);
                }

                // Finding the parent node corresponding to the node's level.
                parentNode = pointerMap.get(node.getLevel() - 1);
                if (parentNode != null) {
                    parentNode.addNode(node);
                }

                // If the node is an operator, then add it to the pointer map
                // as it needs to be standardized once the child nodes are filled and standardized
                if (node.getType() == DataDictionary.Data.OPERATOR) {
                    pointerMap.put(node.getLevel(), node);
                    parentNode = node;
                    prevNode = node;
                } else {
                    // Set the previous node of the current node
                    List<Node> children = parentNode != null ? parentNode.getChildren() : null;
                    prevNode = children != null ? children.get(children.size() - 1) : null;
                }
                currentLevel = node.getLevel();
            }

            // Add to the pointers list if the node is an operator
            if (node.getType() == DataDictionary.Data.OPERATOR) {
                pointerMap.put(node.getLevel(), node);
            }
        }

        // Standardize remaining nodes in the pointer map in a bottom-up approach
        Set<Integer> keys = pointerMap.keySet();

        List<Integer> list = new ArrayList<>(keys);
        list.sort(Collections.reverseOrder());
        Set<Integer> resultSet = new LinkedHashSet<>(list);

        Node removedNode = null;
        for (Integer i : resultSet) {
            removedNode = pointerMap.remove(i);
            standardizeNode(removedNode);
        }

        return removedNode;
    }

    /**
     * Standardize a given AST node
     *
     * @param node node to be standardized
     */
    private static void standardizeNode(Node node) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CloneNotSupportedException {
        switch (OperatorDictionary.map.get(node.getValue())) {
            case FUNCTION_FORM:
                AbstractBuilder.getInstance(FunctionFormBuilder.class).standardize(node);
                break;
            case RECURSION:
                AbstractBuilder.getInstance(RecursionBuilder.class).standardize(node);
                break;
            case LET:
                AbstractBuilder.getInstance(LetBuilder.class).standardize(node);
                break;
            case WHERE:
                AbstractBuilder.getInstance(WhereBuilder.class).standardize(node);
                break;
            case WITHIN:
                AbstractBuilder.getInstance(WithinBuilder.class).standardize(node);
                break;
            case INFIX:
                AbstractBuilder.getInstance(InfixBuilder.class).standardize(node);
                break;
            case AND_EQ:
                AbstractBuilder.getInstance(AndBuilder.class).standardize(node);
                break;
            case LAMBDA:
                AbstractBuilder.getInstance(LambdaBuilder.class).standardize(node);
                break;
        }
    }
}
