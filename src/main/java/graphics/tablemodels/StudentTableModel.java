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
    private ArrayList<Grade> grades;
    private ArrayList<SchoolClass> schoolClasses = new ArrayList<SchoolClass>(); //liste associée à grades
    private Student student;

    public StudentTableModel(Student stud, ArrayList<SchoolClass> sclClasses) {
        super();
        this.student = stud;

        this.headers.add("Code de l'UE");
        this.headers.add("Nom de l'UE");
        this.headers.add("Crédits ECTS");
        this.headers.add("Note de l'étudiant");

        this.grades = (stud == null? new ArrayList<Grade>(): stud.getGrades());
        setup(sclClasses);
    }

    public int getRowCount() {
        return this.grades.size();
    }

    public int getColumnCount() {
        return this.headers.size();
    }

    @Override
    public String getColumnName(int i) {
        return this.headers.get(i);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return this.grades.get(rowIndex).getCode();
            case 1: return (this.schoolClasses.get(rowIndex) == null ? "" : this.schoolClasses.get(rowIndex).getName());
            case 2: return (this.schoolClasses.get(rowIndex) == null ? "" : this.schoolClasses.get(rowIndex).getNbCredits());
            case 3: return this.grades.get(rowIndex);
            default: return null;
        }
    }

    public void addGrade(Grade grade, SchoolClass schoolClass) {
        this.student.addGrade(grade);
        this.schoolClasses.add(schoolClass);
        fireTableRowsInserted(this.grades.size() - 1, this.grades.size() - 1);
    }

    public void modifyGrade(int gradeIndex, double value, boolean isAnABI) {
        Grade grade = grades.get(gradeIndex);
        grade.setValue(value);
        grade.setABI(isAnABI);
    }

    public void removeGrade(int rowIndex) {
        this.grades.remove(rowIndex);
        this.schoolClasses.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    private void setup(ArrayList<SchoolClass> sclClasses) {
        for(Grade g : this.grades) {
            addSchoolClassToGrade(g, sclClasses);
        }
    }

    private void addSchoolClassToGrade(Grade g, ArrayList<SchoolClass> sclClasses) {
        boolean found = false;
        for(SchoolClass sc : sclClasses) if(g.getCode().equals(sc.getCode())) {
            schoolClasses.add(sc);
            found = true;
            break;
        }
        if(!found) schoolClasses.add(null);
    }
}
