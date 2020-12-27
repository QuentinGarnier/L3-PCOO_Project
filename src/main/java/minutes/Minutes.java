package minutes;

import program.Program;
import student.Student;
import teachingunit.Grade;
import teachingunit.SchoolClass;
import teachingunit.TeachingUnit;
import teachingunit.block.Block;
import teachingunit.block.SimpleBlock;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Minutes {
    public static void create(Program program, Student[] students) {
        try {
            String pName = program.getName().toLowerCase().replaceAll(" ","_"); // Normalize the name
            new File("output").mkdirs(); // Create the directory output if it doesn't exist (to avoid error in the 'new PrintWriter()' below)
            PrintWriter writer = new PrintWriter("output/minutes_" + pName + ".csv"); // Open the file or create it if it doesn't exist

            writer.println(header(program));
            for(Student std : students) writer.println(studentLine(std, program));
            writer.print("END - 4 last lines");

            writer.close();
        } catch(IOException e) {
            System.err.println("Error: failed to create the file properly.");
        }
    }

    private static String header(Program program) {
        String header = "\"N° Étudiant\",\"Nom\",\"Prénom\"," + info(program);
        for(Block b : program.getBlocks()) {
            header += "," + info(b); //Add info of a block
            //Add classes' info of blocks (except for SimpleBlock which are already equivalent to a class):
            if(!(b instanceof SimpleBlock)) for(SchoolClass scl : b.getClasses()) header += "," + info(scl);
        }
        return header;
    }

    private static String info(Program program) {
        return "\"" + program.getCode() + " - " + program.getName() + "\"";
    }

    private static String info(TeachingUnit tu) {
        return "\"" + tu.getCode() + " - " + tu.getName() + "\"";
    }

    private static String studentLine(Student student, Program p) {
        String str = "\"" + student.getId() + "\",\"" + student.getName() + "\",\"" + student.getFirstname() + "\"";
        ArrayList<Grade> grades = student.getGrades();
        ArrayList<Grade> avGrades = new ArrayList<Grade>();
/*
        ArrayList<String> listp = new ArrayList<String>();
        listp.add(program.getCode());
        for(Block b : program.getBlocks()) {
            listp.add(b.getCode());
            if(!(b instanceof SimpleBlock)) for(SchoolClass scl : b.getClasses()) listp.add(scl.getCode());
        }*/

        str += ",\"" + "gTOREMPLACE" + "\"";
        return str;
    }
}
