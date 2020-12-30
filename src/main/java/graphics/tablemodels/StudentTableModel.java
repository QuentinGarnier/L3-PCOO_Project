package graphics.tablemodels;

import student.Student;
import teachingunit.Grade;
import teachingunit.SchoolClass;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Class: StudentTableModel
 * @author Quentin Garnier
 */

public class StudentTableModel extends AbstractTableModel {
    private ArrayList<String> headers = new ArrayList<String>();
    private ArrayList<Grade> grades = new ArrayList<Grade>();
    private ArrayList<SchoolClass> schoolClasses = new ArrayList<SchoolClass>(); //liste associée à grades
    private Student student;

    public StudentTableModel(Student stud, SchoolClass[] sclClasses) {
        super();
        this.student = stud;

        headers.add("Code de l'UE");
        headers.add("Nom de l'UE");
        headers.add("Crédits ECTS");
        headers.add("Note de l'étudiant");

        setup(sclClasses);
    }

    public int getRowCount() {
        return grades.size();
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
            case 0: return grades.get(rowIndex).getCode();
            case 1: return schoolClasses.get(rowIndex).getName();
            case 2: return schoolClasses.get(rowIndex).getNbCredits();
            case 3: return grades.get(rowIndex);
            default: return null;
        }
    }

    public void addGrade(Grade grade, SchoolClass schoolClass) {
        grades.add(grade);
        schoolClasses.add(schoolClass);
        fireTableRowsInserted(grades.size() - 1, grades.size() - 1);
    }

    public void removeGrade(int rowIndex) {
        grades.remove(rowIndex);
        schoolClasses.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    private void setup(SchoolClass[] sclClasses) {
        for(Grade g : grades) {
            addSchoolClassToGrade(g, sclClasses);
        }
    }

    private void addSchoolClassToGrade(Grade g, SchoolClass[] sclClasses) {
        boolean found = false;
        for(SchoolClass sc : sclClasses) if(g.getCode().equals(sc.getCode())) {
            schoolClasses.add(sc);
            found = true;
            break;
        }
        if(!found) schoolClasses.add(null);
    }
}
