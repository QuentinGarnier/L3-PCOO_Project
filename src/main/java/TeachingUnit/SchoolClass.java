package TeachingUnit;

import java.util.ArrayList;

/**
 * @author Quentin Garnier
 */

public class SchoolClass extends TeachingUnit {
    private ArrayList<Grade> grades;

    public SchoolClass(String n, String c, int nb) {
        super(n, c, nb);
        this.grades = new ArrayList<Grade>();
    }

    public void addGrade(Grade g) {
        this.grades.add(g);
    }

    public ArrayList<Grade> getGrades() {
        return this.grades;
    }

    public float getAverageGrade() {
        float av = 0;
        int nbg = 0;
        for(Grade g : this.grades) {
            av += g.getValue();
            nbg ++;
        }
        return (nbg == 0? 0 : av/nbg);
    }
}
