package student;

import program.Program;
import teachingunit.Grade;
import teachingunit.SchoolClass;
import teachingunit.block.*;

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

    public Grade[] calculateGradesOf(Program p) {
        Grade[] gradesInArray = this.grades.toArray(new Grade[0]);
        ArrayList<Grade> gs = new ArrayList<Grade>();
        gs.add(p.getGrade(this.grades));
        if (p.getBlocks() != null) for (Block b : p.getBlocks()) {
            gs.add(b.getGrade(gradesInArray));
            if (!(b instanceof SimpleBlock)) if (b.getClasses() != null) for (SchoolClass scl : b.getClasses()) gs.add(scl.getGrade(gradesInArray));
        }
        return gs.toArray(new Grade[0]);
    }

    @Override
    public String toString() {
        return this.firstname + " " + this.name;
    }
}
