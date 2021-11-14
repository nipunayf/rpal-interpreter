package nipunayf.rpalinterpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * List of all the Data available in the RPAL language.
 * Maps input file keyword the corresponding Data
 */
public class DataDictionary {
    public static final Map<String, Data> map = new HashMap<>();

    /**
     * Data available in the RPAL language
     */
    public enum Data {
        INTEGER,
        STRING,
        IDENTIFIER,
        OPERATOR,
        BOOLEAN,
        TUPLE,
        DUMMY,
        NIL
    }

    static {
        map.put("INT", Data.INTEGER);
        map.put("STR", Data.STRING);
        map.put("ID", Data.IDENTIFIER);
        map.put("nil", Data.NIL);
    }
}
