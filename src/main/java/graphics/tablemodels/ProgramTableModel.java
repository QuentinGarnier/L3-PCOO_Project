package graphics.tablemodels;

import program.Program;
import student.Student;
import teachingunit.Grade;
import teachingunit.SchoolClass;
import teachingunit.block.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Class: ProgramTableModel
 * @author Quentin Garnier
 */

public class ProgramTableModel extends AbstractTableModel {
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<String> headers = new ArrayList<String>();
    private Program program;
    private boolean[] ind;

    public ProgramTableModel(Program prog, boolean[] indexes, Student[] studentsList) {
        super();
        this.program = prog;
        this.ind = indexes;
        headers.add("N° Étudiant");
        headers.add("Prénom");
        headers.add("Nom");
        headers.add(prog.toString());
        try {
            int i = 0;
            for (Block b : prog.getBlocks()) {
                if (indexes != null) {
                    if (indexes[i]) {
                        headers.add(b.toString());
                        if (!(b instanceof SimpleBlock)) for (SchoolClass scl : b.getClasses()) headers.add(scl.toString());
                    }
                } else {
                    headers.add(b.toString());
                    if (!(b instanceof SimpleBlock)) for (SchoolClass scl : b.getClasses()) headers.add(scl.toString());
                }
                i++;
            }
        } catch(NullPointerException e) {}

        ArrayList<Student> stds = new ArrayList<Student>();
        for(Student s : studentsList) if(prog.equals(s.getProgram())) stds.add(s);
        students.addAll(stds);
    }

    public int getRowCount() {
        return students.size();
    }

    public int getColumnCount() {
        return headers.size();
    }

    @Override
    public String getColumnName(int i) {
        return headers.get(i);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return students.get(rowIndex).getId();
            case 1: return students.get(rowIndex).getFirstname();
            case 2: return students.get(rowIndex).getName();
            default:
                if(columnIndex >= headers.size()) return null;
                return students.get(rowIndex).calculateGradesOf(program)[columnIndex - 3];
        }
    }
}
