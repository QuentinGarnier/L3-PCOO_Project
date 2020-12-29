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
import java.util.ArrayList;

/**
 * @author Quentin Garnier
 */

public class Minutes {
    private static MinutesData minutesData;

    public static void create(Program program, Student[] students) {
        try {
            String pName = program.getName().toLowerCase().replaceAll(" ","_"); // Normalize the name
            new File("output").mkdirs(); // Create the directory output if it doesn't exist (to avoid error in the 'new PrintWriter()' below)
            PrintWriter writer = new PrintWriter("output/minutes_" + pName + ".csv"); // Open the file or create it if it doesn't exist

            writer.println(header(program));
            for(Student std : students) writer.println(studentLine(std, program));
            for(int i=1; i<5; i++) writer.println(footer(i));

            writer.close();
        } catch(IOException e) {
            System.err.println("Error: failed to create the file properly.");
        }
    }

    //Give standard values to variables:
    private static void setup(int size) {
        minutesData = new MinutesData(size);
    }

    private static String header(Program program) {
        String header = "\"N° Étudiant\",\"Nom\",\"Prénom\"," + info(program);
        int size = 1; //on ajoute la case de program
        for(Block b : program.getBlocks()) {
            header += "," + info(b); //Add info of a block
            size ++;
            //Add classes' info of blocks (except for SimpleBlock which are already equivalent to a class):
            if(!(b instanceof SimpleBlock)) for(SchoolClass scl : b.getClasses()) {
                header += "," + info(scl);
                size++;
            }
        }
        setup(size); //initialise le MinutesData une fois les informations du header initialisées
        return header;
    }

    private static String info(Program program) {
        return "\"" + program + "\"";
    }

    private static String info(TeachingUnit tu) {
        return "\"" + tu + "\"";
    }

    private static String studentLine(Student student, Program p) {
        Grade[] sList = student.getGrades().toArray(new Grade[0]);
        ArrayList<Grade> grades = new ArrayList<Grade>();

        String str = "\"" + student.getId() + "\",\"" + student.getName() + "\",\"" + student.getFirstname() + "\"";
        str += ",\"" + addToList(p.getGrade(sList), grades) + "\"";
        for(Block b : p.getBlocks()) {
            str += ",\"" + addToList(b.getGrade(sList), grades) + "\"";
            if(!(b instanceof SimpleBlock)) for(SchoolClass scl : b.getClasses()) str += ",\"" + addToList(scl.getGrade(sList), grades) + "\"";
        }
        minutesData.addToData(grades.toArray(new Grade[0]));

        str = str.replaceAll("null","");
        return str;
    }

    private static Grade addToList(Grade g, ArrayList<Grade> grades) {
        grades.add(g);
        return g;
    }

    /**
     * @param numLine définit (de 1 à 4) quelle ligne du footer il faut print
     * @return la ligne sélectionnée construite
     */
    private static String footer(int numLine) {
        String str = "\"";
        switch(numLine) {
            case 1: str += "Note max\",\"\",\"\"";
                    for(double d : minutesData.getMaximums()) str += ",\"" + (d>-1?d:"") + "\"";
                    break;

            case 2: str += "Note min\",\"\",\"\"";
                    for(double d : minutesData.getMinimums()) str += ",\"" + (d<21?d:"") + "\"";
                    break;

            case 3: str += "Note moyenne\",\"\",\"\"";
                    minutesData.calculateAvgs();
                    for(double d : minutesData.getAverages()) str += ",\"" + (d>-1?d:"") + "\"";
                    break;

            case 4: str += "Écart-type\",\"\",\"\"";
                    minutesData.calculateStddevns();
                    for(double d : minutesData.getStddevns()) str += ",\"" + (d>-1?d:"") + "\"";
                    break;

            default: str = "";
                     break;
        }
        return str;
    }
}
