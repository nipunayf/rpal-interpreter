package nipunayf.rpalinterpreter;

import nipunayf.rpalinterpreter.csemachine.InvalidCSEMachineException;
import nipunayf.rpalinterpreter.csemachine.Machine;
import nipunayf.rpalinterpreter.csemachine.environment.Environment;
import nipunayf.rpalinterpreter.csemachine.environment.PreliminaryEnvironment;
import nipunayf.rpalinterpreter.tree.Generator;
import nipunayf.rpalinterpreter.tree.node.Node;
import nipunayf.rpalinterpreter.tree.node.operators.PrintNode;
import nipunayf.rpalinterpreter.tree.node.operators.StringOpNode;

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
            }};

            Machine machine = new Machine(new PreliminaryEnvironment(preliminaryDirectory), root);
            outputValue = machine.evaluate().getValue();

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException | InvalidCSEMachineException e) {
            e.printStackTrace();
        }
    }
}
