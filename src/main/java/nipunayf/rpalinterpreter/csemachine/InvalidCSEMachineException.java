package nipunayf.rpalinterpreter.csemachine;

/**
 * Exception to indicate an erroneous CSE machine
 */
public class InvalidCSEMachineException extends Exception {
    public InvalidCSEMachineException(String message) {
        super(message);
    }
}
