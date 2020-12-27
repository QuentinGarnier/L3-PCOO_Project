package teachingunit.block;

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
    public Grade getGrade(Grade[] grades) {
        Grade[] classesGrades = new Grade[this.nbOfClasses()];
        for(int i=0; i<this.nbOfClasses(); i++) classesGrades[i] = this.classes[i].getGrade(grades); //on extrait de la liste uniquement les notes correspondantes aux cours

        boolean oneGradeMin = false;
        double avg = 0;
        int divis = 0;
        boolean onlyABI = true; //Par défaut, tant qu'on a pas prouvé qu'il y a au moins 1 note non ABI, c'est true
        for(int j=0; j<this.nbOfClasses(); j++) {
            if (classesGrades[j] != null) {
                if (!oneGradeMin) oneGradeMin = true;
                avg += classesGrades[j].getValue() * this.classes[j].getNbCredits(); //On peut car les notes sont bien rangées
                divis += this.classes[j].getNbCredits();
                if(!classesGrades[j].isAnABI()) onlyABI = false;
            }
        }

        //Création et renvoi de la note moyenne si on en a au moins trouvé une :
        if(oneGradeMin) {
            avg = (divis == 0 ? 0 : avg / divis); //opérateur ternaire juste en sécurité pour éviter les divisions par 0 si le nombre de crédit du premier cours a été mal renseigné (= 0)
            Grade res = new Grade(avg, this.getCode());
            res.setABI(onlyABI);
            return res;
        }
        return null; //null uniquement si aucune note de grades ne correspond aux cours du bloc
    }
}
