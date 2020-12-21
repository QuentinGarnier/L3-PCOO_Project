package TeachingUnit.Block;

import TeachingUnit.Grade;
import TeachingUnit.SchoolClass;

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
    public float getAverageGrade() {
        float avg = 0;
        int totalCredits = 0;
        for(SchoolClass cl : this.classes) {
            avg += cl.getAverageGrade()*cl.getNbCredits();
            totalCredits += cl.getNbCredits();
        }
        return (totalCredits==0? 0:avg/totalCredits);
    }

    public void addGrade(Grade g, String code) {
        for(SchoolClass scl : this.classes) {
            if(code.equals(scl.getCode())) {
                scl.addGrade(g);
                break;
            }
        }
    }
}
