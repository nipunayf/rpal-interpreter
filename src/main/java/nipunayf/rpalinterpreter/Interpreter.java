package nipunayf.rpalinterpreter;

import nipunayf.rpalinterpreter.csemachine.Machine;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
import nipunayf.rpalinterpreter.tree.Generator;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.*;

import java.util.HashMap;
import java.util.Map;

public class Interpreter {
    public static Node outputValue;

    public static void main(String[] args) {
        try {
            Node root = Generator.generateTree(args[0]);

            // Processing the arguments of the input code
            if (args.length == 2) {
                if (args[1].equals("-cse")) {
                    Machine.setPrintMode();
                } else {
                    System.out.println("Only supported flag is \"-cse\" to print each evaluating step");
                    return;
                }
            } else if (args.length > 2) {
                System.out.println("Only supported two arguments: filename [-cse]");
                return;
            }

            // Initializing the preliminary environment
            Map<String, Node> preliminaryDirectory = new HashMap<>() {{
                put("Print", new PrintNode(0));
                put("Stern", new StringOpNode(0, "Stern"));
                put("Stem", new StringOpNode(0, "Stem"));
                put("Conc", new ConcatNode(0));
                put("Order", new TauOpNode(0, "Order"));
                put("Null", new TauOpNode(0, "Null"));
                put("Isinteger", new TypeOpNode(0, "Isinteger"));
                put("Isturthvalue", new TypeOpNode(0, "Isturthvalue"));
                put("Isstring", new TypeOpNode(0, "Isstring"));
                put("Istuple", new TypeOpNode(0, "Istuple"));
                put("Isfunction", new TypeOpNode(0, "Isfunction"));
                put("Isdummy", new TypeOpNode(0, "Isdummy"));
                put("ItoS", new IToSNode(0));
            }};

            // Generate the root CSE machine and evaluate the program
            Machine machine = new Machine(new PreliminaryEnvironment(preliminaryDirectory), root);
            machine.evaluate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
