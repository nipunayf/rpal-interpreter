package nipunayf.rpalinterpreter;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.csemachine.Machine;
import nipunayf.rpalinterpreter.csemachine.environment.Environment;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
import nipunayf.rpalinterpreter.tree.Generator;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class myrpal {
    public static String outputValue;

    public static void main(String[] args) {
        try {
//            Node root = Generator.generateTree("src/main/java/nipunayf/rpalinterpreter/sample.txt");
            Node root = Generator.generateTree(args[0]);

            Map<String, Node> preliminaryDirectory = new HashMap<>() {{
                put("Print", new PrintNode(0));
                put("Stern", new StringOpNode(0, "Stern"));
                put("Stem", new StringOpNode(0, "Stem"));
                put("Conc", new ConcatNode(0));
                put("Order", new TauOpNode(0, "Order"));
                put("Isinteger", new TypeOpNode(0, "Isinteger"));
                put("Isturthvalue", new TypeOpNode(0, "Isturthvalue"));
                put("Isstring", new TypeOpNode(0, "Isstring"));
                put("Istuple", new TypeOpNode(0, "Istuple"));
                put("Isfunction", new TypeOpNode(0, "Isfunction"));
                put("Isdummy", new TypeOpNode(0, "Isdummy"));
                put("ItoS", new IToSNode(0));
            }};

            Machine machine = new Machine(new PreliminaryEnvironment(preliminaryDirectory), root);
            outputValue = machine.evaluate().getValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
