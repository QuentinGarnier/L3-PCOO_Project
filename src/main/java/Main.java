import minutes.Minutes;
import program.Program;
import student.Student;
import teachingunit.block.*;
import teachingunit.*;
import xmlreader.XMLReader;

/**
 * @author Quentin Garnier
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("HelloWorld!");

        XMLReader.read("data/data.xml", "student");

        // Tests temporaires des classes (décommentez pour voir si ça marche nickel pour vous) :

        Student emma = new Student(21604250, "Emma", "KITTY");

        SimpleBlock pcoo = new SimpleBlock("PCOO", "SPUF053", 6);
        SimpleBlock progfonct = new SimpleBlock("Prog. Fonct.", "SPUF054",6);
        SimpleBlock al = new SimpleBlock("Automates & Lang.", "SPUF052", 6);
        SchoolClass[] options = {new SchoolClass("Crypto","SPUF060",6), new SchoolClass("Archit.","SPUF061",6)};
        OptionsBlock cryptoOuArchit = new OptionsBlock("Options S5", "SPUF055", options, 6);

        Program prog = new Program("L3 Info", "L3I");
        prog.setBlocks(pcoo, progfonct, al, cryptoOuArchit);

        emma.addGrade(new Grade(18, "SPUF053"));
        emma.addGrade(new Grade(16, "SPUF053"));
        emma.addGrade(new Grade(14, "SPUF053")); //moy = 16
        emma.addGrade(new Grade(20, "SPUF054"));
        emma.addGrade(new Grade(15, "SPUF054")); //moy = 17.5
        emma.addGrade(new Grade(11, "SPUF052")); //moy = 11
        emma.addGrade(new Grade(12, "SPUF060"));
        emma.addGrade(new Grade(13, "SPUF060")); //moy = 12.5

        float finalGrade = prog.getAverageGrade(emma.getGrades()); //en théorie 14.25, donc les calculs sont bons !
        System.out.println(emma.getGrades());
        System.out.println(finalGrade);

        Student[] stdList = new Student[1];
        stdList[0] = emma;
        Minutes.create(prog, stdList);

    }
}
