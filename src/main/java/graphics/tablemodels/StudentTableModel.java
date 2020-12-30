package graphics.tablemodels;

import student.Student;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Class: StudentTableModel
 * @author Quentin Garnier
 */

public class StudentTableModel extends AbstractTableModel {
    private ArrayList<String> headers = new ArrayList<String>();
    private ArrayList<Student> students = new ArrayList<Student>();

    public StudentTableModel() {
        super();

        headers.add("N° Étudiant");
        headers.add("Prénom");
        headers.add("Nom");

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
                return null;
        }
    }

    public void addStudent(Student std) {
        students.add(std);
        fireTableRowsInserted(students.size() - 1, students.size() - 1);
    }

    public void removeStudent(int rowIndex) {
        students.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void changeStudentsList(ArrayList<Student> stds) {
        this.students = stds;
    }
}
