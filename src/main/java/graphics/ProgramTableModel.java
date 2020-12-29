package graphics;

import program.Program;
import student.Student;
import teachingunit.SchoolClass;
import teachingunit.block.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class: ProgramTableModel
 * @author Quentin Garnier
 */

public class ProgramTableModel extends AbstractTableModel {
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<String> headers = new ArrayList<String>();

    ProgramTableModel(Program p, Student[] stds) {
        super();

        headers.add("N° Étudiant");
        headers.add("Prénom");
        headers.add("Nom");
        headers.add(p.toString());
        for(Block b : p.getBlocks()) {
            headers.add(b.toString());
            if(!(b instanceof SimpleBlock)) for(SchoolClass scl : b.getClasses()) headers.add(scl.toString());
        }

        students.addAll(Arrays.asList(stds));
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
            default: return null;
        }
    }

    void addStudent(Student std) {
        students.add(std);
        fireTableRowsInserted(students.size() - 1, students.size() - 1);
    }

    void removeStudent(int rowIndex) {
        students.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    void changeList(ArrayList<Student> stds) {
        this.students = stds;
    }
}
