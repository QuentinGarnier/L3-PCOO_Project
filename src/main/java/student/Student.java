package student;

import program.Program;
import teachingunit.Grade;
import java.util.ArrayList;

/**
 * @author Quentin Garnier
 */

public class Student {
    private int id;
    private String firstname;
    private String name;
    private Program program;
    private ArrayList<Grade> grades;

    public Student(int i, String fn, String n) {
        this.id = i;
        this.firstname = fn;
        this.name = n;
        this.grades = new ArrayList<Grade>();
        //program is null by default.
    }

    public void setProgram(Program pgm) {
        this.program = pgm;
    }

    public void addGrade(Grade g) {
        this.grades.add(g);
    }

    public int getId() {
        return this.id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Grade> getGrades() {
        return this.grades;
    }

    public Program getProgram() {
        return this.program;
    }

}
