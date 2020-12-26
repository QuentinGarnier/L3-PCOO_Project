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
    public float getAverageGrade(Grade[] grades) {
        float avg = 0;
        for(SchoolClass scl : this.options) if(scl.getAverageGrade(grades) > avg) avg = scl.getAverageGrade(grades);
        return avg;
    }


}
