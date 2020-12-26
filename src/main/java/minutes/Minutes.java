package minutes;

import program.Program;
import student.Student;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Minutes {
    public static void create(Program program, Student[] students) {
        try {
            String pName = program.getName().toLowerCase().replaceAll(" ","_"); // Normalize the name
            new File("output").mkdirs(); // Create the directory output if it doesn't exist (to avoid error in the 'new PrintWriter()' below)
            PrintWriter writer = new PrintWriter("output/minutes_" + pName + ".csv"); // Open the file or create it if it doesn't exist
            writer.println("First Test!");
            writer.print("END");
            writer.close();
        } catch(IOException e) {
            System.err.println("Error: failed to create the file properly.");
        }
    }
}
