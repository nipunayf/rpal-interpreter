package nipunayf.rpalinterpreter.tree.node;

import nipunayf.rpalinterpreter.SymbolDictionary;

public class DataNode extends Node{

    /**
     * Creates an AST node
     *
     * @param level
     * @param value
     * @param type
     */
    public DataNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
    }
}
