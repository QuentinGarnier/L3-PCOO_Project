package graphics;

import student.Student;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Class: ProgramTableModel
 * @author Quentin Garnier
 */

public class ProgramTableModel extends AbstractTableModel {
    private ArrayList<Student> students = new ArrayList<Student>();
    private String[] headers = {"N° Étudiant", "Prénom", "Nom"};

    ProgramTableModel() {
        super();

        students.add(new Student(21603567, "Johnathan", "Sykes"));
        students.add(new Student(21603566, "Nicolas", "Van der Kampf"));
        students.add(new Student(21603565, "Damien", "Cuthbert"));
        students.add(new Student(21603564, "Corinne", "Valance"));
        students.add(new Student(21603563, "Emilie", "Schrödinger"));
        students.add(new Student(21603562, "Delphine", "Duke"));
        students.add(new Student(21603561, "Eric", "Twomk"));
        students.add(new Student(21603560, "Erica", "Juil"));
        students.add(new Student(21603568, "Michel", "Michel"));
        students.add(new Student(21603569, "Xiaomi", "Markalopolof"));
    }

    public int getRowCount() {
        return students.size();
    }

    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int i) {
        return headers[i];
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
