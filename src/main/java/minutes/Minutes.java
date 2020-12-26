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

public class Minutes {
    public static void create(Program program, Student[] students) {
        try {
            String pName = program.getName().toLowerCase().replaceAll(" ","_"); // Normalize the name
            new File("output").mkdirs(); // Create the directory output if it doesn't exist (to avoid error in the 'new PrintWriter()' below)
            PrintWriter writer = new PrintWriter("output/minutes_" + pName + ".csv"); // Open the file or create it if it doesn't exist

            writer.println(header(program));
            for(Student std : students) writer.println(studentLine(std));
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

    private static String studentLine(Student student) {
        String str = "\"" + student.getId() + "\",\"" + student.getName() + "\",\"" + student.getFirstname() + "\"";
        //tri des notes selon les ue : sachant que pour les ue sans note il faut quand même écrire ,"", dans le csv
        for(Grade g : student.getGrades()) str += ",\"" + g + "\"";
        return str;
    }
}
