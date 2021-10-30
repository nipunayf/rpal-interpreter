package nipunayf.rpalinterpreter;

import nipunayf.rpalinterpreter.csemachine.environment.Environment;
import nipunayf.rpalinterpreter.tree.Generator;
import nipunayf.rpalinterpreter.tree.node.Node;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class myrpal {

    public static void main(String[] args) {
        try {
            Node root = Generator.generateTree("src/main/java/nipunayf/rpalinterpreter/sample.txt");

            Map<String, Node> preliminaryDirectory = new HashMap<>() {{
            }};

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
