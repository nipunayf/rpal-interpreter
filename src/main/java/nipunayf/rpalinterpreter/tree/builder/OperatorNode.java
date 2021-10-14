package nipunayf.rpalinterpreter.tree.builder;

import nipunayf.rpalinterpreter.SymbolDictionary;

/**
 * Node of the tree which represents an operation.
 * Should have children to evaluate.
 */
class OperatorNode extends Node{
    /**
     * Creates an AST node
     *
     * @param level
     * @param value
     * @param type
     */
    OperatorNode(int level, String value, SymbolDictionary.Symbol type) {
        super(level, value, type);
    }
}
