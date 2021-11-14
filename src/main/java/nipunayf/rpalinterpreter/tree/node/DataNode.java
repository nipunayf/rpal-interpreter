package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.DataDictionary;

public class DataNode extends Node {

    /**
     * Creates a data node (leaf node)
     *
     * @param level level in the tree
     * @param value value of the node
     * @param type  whether it is a data type or an operation type
     */
    public DataNode(int level, String value, DataDictionary.Data type) {
        super(level, type == DataDictionary.Data.STRING ? value.substring(1, value.length() - 1) : value, type);
    }
}
