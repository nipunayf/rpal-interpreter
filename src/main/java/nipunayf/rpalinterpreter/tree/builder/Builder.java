package nipunayf.rpalinterpreter.tree.builder;

import nipunayf.rpalinterpreter.SymbolDictionary;

/**
 * Builds a standardized tree by generating nodes and manipulating them.
 */
public class Builder {

    /**
     * Generates a node from the input line
     *
     * @param line
     * @return
     */
    public Node generateNode(String line) {
        int stoppedIndex = 0;

        // Determining the level of the node by its dots
        int level = 0;
        for (int i = 0; i < line.length(); i++) {
            char a = line.charAt(i);
            if (a == '.') {
                level++;
                stoppedIndex = i + 1;
            } else break;
        }

        // Extracting the type and its value
        char valueStart = line.charAt(stoppedIndex);
        SymbolDictionary.Symbol type;
        String value;

        // The type is a data type
        if (valueStart == '<') {
            int valueStopIndex = line.indexOf(':');
            type = SymbolDictionary.map.get(line.substring(stoppedIndex + 1, valueStopIndex));
            int closeIndex = line.indexOf('>');
            value = line.substring(valueStopIndex + 1, closeIndex);
            return new DataNode(level, value, type);

        } else { // The type is an operator
            type = SymbolDictionary.Symbol.OPERATOR;
            value = line.substring(stoppedIndex, line.length());
            return new OperatorNode(level, value, type);
        }
    }
}
