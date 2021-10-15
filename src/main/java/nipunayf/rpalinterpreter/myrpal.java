package nipunayf.rpalinterpreter;

import java.io.*;
import java.util.Scanner;

public class myrpal {

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/nipunayf/rpalinterpreter/sample.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                int numDots;



                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
