package nipunayf.rpalinterpreter.tree.builder;

import nipunayf.rpalinterpreter.SymbolDictionary;

class DataNode extends Node{

    /**
     * Creates an AST node
     *
     * @param level
     * @param value
     * @param type
     */
    DataNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
    }
}
