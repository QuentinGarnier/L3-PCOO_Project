import graphics.GMWindow;
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


        // Tests temporaires des classes :

        Student emma = new Student(21604250, "Emma", "Kitty");
        Student george = new Student(21602987, "George", "Arys");
        Student michel = new Student(21602987, "Michel", "Miche");

        SimpleBlock pcoo = new SimpleBlock("PCOO", "SPUF053", 6);
        SimpleBlock progfonct = new SimpleBlock("Prog. Fonct.", "SPUF054",6);
        SimpleBlock al = new SimpleBlock("Automates & Lang.", "SPUF052", 6);
        SchoolClass[] options = {new SchoolClass("Crypto","SPUF060",6), new SchoolClass("Archit.","SPUF061",6)};
        OptionsBlock cryptoOuArchit = new OptionsBlock("Options S5", "SPUF055", options, 6);

        Program prog = new Program("L3 Info", "L3I");
        prog.setBlocks(pcoo, progfonct, al, cryptoOuArchit);

        emma.addGrade(new Grade(16, "SPUF053"));
        emma.addGrade(new Grade(17.5, "SPUF054"));
        emma.addGrade(new Grade(12.5, "SPUF060"));
        emma.addGrade(new Grade(-1, "SPUF052")); //-1 <=> ABI

        george.addGrade(new Grade(10, "SPUF053"));
        george.addGrade(new Grade(6.234, "SPUF054"));
        george.addGrade(new Grade(-1, "SPUF061")); //-1 <=> ABI
        george.addGrade(new Grade(19.98, "SPUF052"));

        Program prog2 = new Program("L3 Maths info", "L3MI");
        SimpleBlock algebre = new SimpleBlock("Algèbre", "SPMA051", 6);
        prog2.setBlocks(algebre, pcoo);
        Program[] pList = new Program[] {prog, prog2};

        emma.setProgram(prog);
        george.setProgram(prog);
        michel.setProgram(prog2);

        Student[] stdList = new Student[3];
        stdList[0] = emma;
        stdList[1] = george;
        stdList[2] = michel;

        SchoolClass[] lesCours = new SchoolClass[5];
        lesCours[0] = pcoo.getSClass();
        lesCours[1] = progfonct.getSClass();
        lesCours[2] = al.getSClass();
        lesCours[3] = cryptoOuArchit.getClasses()[0];
        lesCours[4] = cryptoOuArchit.getClasses()[1];

        GMWindow gmWindow = new GMWindow(pList, stdList, lesCours); //à modifier quand elles auront push la classe XMLReader
        gmWindow.display();

    }
}
