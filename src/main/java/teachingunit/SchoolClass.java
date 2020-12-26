package teachingunit;

import java.util.ArrayList;

/**
 * @author Quentin Garnier
 */

public class SchoolClass extends TeachingUnit {

    public SchoolClass(String n, String c, int nb) {
        super(n, c, nb);
    }

    //Calcule la moyenne des notes données associées au cours :
    public float getAverageGrade(Grade[] grades) {
        float av = 0;
        int nbg = 0;
        for(Grade g : grades) if(g.getCode().equals(this.getCode())) {
            av += g.getValue();
            nbg ++;
        }
        return (nbg == 0? 0 : av/nbg);
    }

    // Une variable de la méthode getAverageGrade si on passe une ArrayList en entrée :
    public float getAverageGrade(ArrayList<Grade> grades) {
        Grade[] gds = grades.toArray(new Grade[0]);
        return this.getAverageGrade(gds);
    }
}
