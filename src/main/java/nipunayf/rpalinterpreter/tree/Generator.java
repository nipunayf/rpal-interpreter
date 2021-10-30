package nipunayf.rpalinterpreter.tree;

import nipunayf.rpalinterpreter.SymbolDictionary;
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

        String line;
        Node parentNode = null;
        Node prevNode = null;
        Node node;
        int currentLevel = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        // Process each line
        while ((line = bufferedReader.readLine()) != null) {
            node = Node.generateNode(line);

            // If the level of the tree gets deeper. Note that only one level can be increased
            if (node.getLevel() > currentLevel) {
                //TODO: Throw an exception if the level increased by n(> 1)
                if (prevNode != null) {
                    prevNode.addNode(node);
                }
                parentNode = prevNode;
                prevNode = node;
                currentLevel = node.getLevel();
            }

            // If the node is a sibling or first of the level
            else if (node.getLevel() == currentLevel) {
                if (parentNode != null) {
                    parentNode.addNode(node);
                } else parentNode = node;
                prevNode = node;
            }

            // If the node is a sibling of an ancestor of the previous node;
            else {
                // If the node level is x, then standardize the nodes from x+1 to the most depth level.
                for (int i = currentLevel; i >= node.getLevel(); i--) {
                    Node removedNode = pointerMap.remove(i);
                    if(removedNode != null) standardizeNode(removedNode);
                }
                parentNode = pointerMap.get(node.getLevel() - 1);
                if (parentNode != null) {
                    parentNode.addNode(node);
                }
                if (node.getType() == SymbolDictionary.Symbol.OPERATOR) {
                    pointerMap.put(node.getLevel(), node);
                    parentNode = node;
                    prevNode = node;
                } else {
                    List<Node> children = parentNode != null ? parentNode.getChildren() : null;
                    prevNode = children != null ? children.get(children.size() - 1) : null;
                }
                currentLevel = node.getLevel();
            }

            // Add to the pointers list if the node is an operator
            if (node.getType() == SymbolDictionary.Symbol.OPERATOR) {
                pointerMap.put(node.getLevel(), node);
            }
        }
        Set<Integer> keys = pointerMap.keySet();

        List<Integer> list = new ArrayList<>(keys);
        list.sort(Collections.reverseOrder());
        Set<Integer> resultSet = new LinkedHashSet<>(list);

        Node removedNode = null;
        for (Integer i: resultSet) {
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
        switch(node.getValue()) {
            case "function_form":
                AbstractBuilder.getInstance(FunctionFormBuilder.class).standardize(node);
                break;
            case "rec":
                AbstractBuilder.getInstance(RecursionBuilder.class).standardize(node);
                break;
            case "let":
                AbstractBuilder.getInstance(LetBuilder.class).standardize(node);
                break;
            case "where":
                AbstractBuilder.getInstance(WhereBuilder.class).standardize(node);
                break;
        }
    }
}
