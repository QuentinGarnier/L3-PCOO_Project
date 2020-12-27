package teachingunit.block;

import teachingunit.Grade;
import teachingunit.SchoolClass;

/**
 * @author Quentin Garnier
 *          Léa Bloom
 */

public class OptionsBlock extends Block {
    private SchoolClass[] options;

    /**
     * @param classes Options of the block, with the SAME number of credits!
     */
    public OptionsBlock(String n, String c, SchoolClass[] classes, int nbc) {
        super(n, c, nbc);
        this.options = classes;
        for(SchoolClass scs : this.options) scs.setNbCredits(nbc);
    }

    public SchoolClass[] getClasses() {
        return this.options;
    }

    public int nbOfOptions() {
        return this.options.length;
    }

    public Grade getGrade(Grade[] grades) {
        Grade[] optionsGrades = new Grade[this.nbOfOptions()];
        for(int i=0; i<this.nbOfOptions(); i++) optionsGrades[i] = this.options[i].getGrade(grades); //on extrait de la liste uniquement les notes correspondantes aux options
        Grade grd = null;
        for(Grade g : optionsGrades) {
            if (g != null) {
                if (grd == null) grd = g;
                else if (g.getValue() > grd.getValue()) grd = g; //dans un else if pour éviter un NullPointerException sur le grd.getValue()
                else if (grd.isAnABI() && g.getValue() == 0 && !g.isAnABI()) grd = g; //cas particulier où on a 0 et ABI : la note est donc de 0 et non plus ABI
            }
        }

        if(grd != null) {
            Grade res = new Grade(grd.getValue(), this.getCode());
            res.setABI(grd.isAnABI());
            return res;
        }
        return null; //null si aucune note de grades ne correspond aux options du bloc
    }


}
