package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.SymbolDictionary;

public class DataNode extends Node{

    /**
     * Creates a data node (leaf node)
     *
     * @param level level in the tree
     * @param value value of the node
     * @param type  whether it is a data type or an operation type
     */
    public DataNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
    }
}
