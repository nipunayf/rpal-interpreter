package nipunayf.rpalinterpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * List of all the Operators available in the RPAL language.
 * Maps input file keyword the corresponding Operator
 */
public class OperatorDictionary {
    public static final Map<String, Operator> map = new HashMap<>();

    /**
     * Operators available in the RPAL language
     */
    public enum Operator {
        PLUS,
        MINUS,
        MULTIPLICATION,
        DIVISION,
        EXPONENTIAL,
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL,
        LESS_THAN,
        LESS_THAN_OR_EQUAL,
        EQUAL,
        NOT_EQUAL,
        OR,
        AND,
        NOT,
        STEM,
        STERN,
        CONCAT,
        PRINT,
        NEGATION,
        LAMBDA,
        GAMMA,
        FUNCTION_FORM,
        RECURSION,
        LET,
        WHERE,
        N_ARY,
        WITHIN,
        TERNARY,
        INFIX,
        AND_EQ,
        TAU,
        ASSIGNMENT,
        Y,
        ETA,
        ORDER,
        AUGMENT,
        IS_INTEGER,
        IS_TRUTH_VALUE,
        IS_STRING,
        IS_TUPLE,
        IS_FUNCTION,
        IS_DUMMY,
        IToS
    }

    static {
        map.put("+", Operator.PLUS);
        map.put("-", Operator.MINUS);
        map.put("*", Operator.MULTIPLICATION);
        map.put("/", Operator.DIVISION);
        map.put("**", Operator.EXPONENTIAL);
        map.put("gr", Operator.GREATER_THAN);
        map.put("ge", Operator.GREATER_THAN_OR_EQUAL);
        map.put("ls", Operator.LESS_THAN);
        map.put("le", Operator.LESS_THAN_OR_EQUAL);
        map.put("eq", Operator.EQUAL);
        map.put("ne", Operator.NOT_EQUAL);
        map.put("or", Operator.OR);
        map.put("&", Operator.AND);
        map.put("not", Operator.NOT);
        map.put("neg", Operator.NEGATION);
        map.put("lambda", Operator.LAMBDA);
        map.put("gamma", Operator.GAMMA);
        map.put("function_form", Operator.FUNCTION_FORM);
        map.put("rec", Operator.RECURSION);
        map.put("let", Operator.LET);
        map.put("where", Operator.WHERE);
        map.put(",", Operator.N_ARY);
        map.put("within", Operator.WITHIN);
        map.put("->", Operator.TERNARY);
        map.put("Stem", Operator.STEM);
        map.put("Stern", Operator.STERN);
        map.put("Conc", Operator.CONCAT);
        map.put("Print", Operator.PRINT);
        map.put("@", Operator.INFIX);
        map.put("and", Operator.AND_EQ);
        map.put("tau", Operator.TAU);
        map.put("=", Operator.ASSIGNMENT);
        map.put("Y", Operator.Y);
        map.put("Eta", Operator.ETA);
        map.put("Order", Operator.ORDER);
        map.put("aug", Operator.AUGMENT);
        map.put("Isinteger", Operator.IS_INTEGER);
        map.put("Istruthvalue", Operator.IS_TRUTH_VALUE);
        map.put("Isstring", Operator.IS_STRING);
        map.put("Istuple", Operator.IS_TUPLE);
        map.put("Isfunction", Operator.IS_FUNCTION);
        map.put("Isdummy", Operator.IS_DUMMY);
        map.put("IToS", Operator.IToS);
    }
}
