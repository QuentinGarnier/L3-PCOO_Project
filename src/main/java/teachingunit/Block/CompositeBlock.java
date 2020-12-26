package teachingunit.Block;

import teachingunit.Grade;
import teachingunit.SchoolClass;

/**
 * @author Quentin Garnier
 */

public class CompositeBlock extends Block {
    private SchoolClass[] classes;

    public CompositeBlock(String n, String c, SchoolClass[] cls) {
        super(n, c, 0);
        this.classes = cls;

        int nb = 0;
        for(SchoolClass cl : cls) nb += cl.getNbCredits();
        this.setNbCredits(nb);
    }

    public SchoolClass[] getClasses() {
        return this.classes;
    }

    public int nbOfClasses() {
        return this.classes.length;
    }


    /* Selon le sujet, il s'agit de la moyenne pondérée ici aussi, donc le nombre de crédits de chaque cours
       influe sur le calcul de la moyenne totale du bloc. */
    public float getAverageGrade(Grade[] grades) {
        float avg = 0;
        int totalCredits = 0;
        for(SchoolClass scl : this.classes) {
            avg += scl.getAverageGrade(grades)*scl.getNbCredits();
            totalCredits += scl.getNbCredits();
        }
        return (totalCredits==0? 0:avg/totalCredits);
    }
}
