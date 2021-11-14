package nipunayf.rpalinterpreter;

import java.util.HashMap;
import java.util.Map;

public class DataDictionary {
    public static final Map<String, Symbol> map = new HashMap<String, Symbol>();

    public enum Symbol {
        INTEGER,
        STRING,
        IDENTIFIER,
        OPERATOR,
        BOOLEAN,
        TUPLE,
        DUMMY
    }

    static {
        map.put("INT", Symbol.INTEGER);
        map.put("STR", Symbol.STRING);
        map.put("ID", Symbol.IDENTIFIER);
    }
}
