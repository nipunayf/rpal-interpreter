package nipunayf.rpalinterpreter;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class SymbolDictionary {
    public static final Map<String, Symbol> map = new HashMap<String, Symbol>();

    public enum Symbol {
        INTEGER,
        STRING,
        IDENTIFIER,
        OPERATOR
    }

    static {
        map.put("INT", Symbol.INTEGER);
        map.put("STR", Symbol.STRING);
        map.put("ID", Symbol.IDENTIFIER);
    }
}
