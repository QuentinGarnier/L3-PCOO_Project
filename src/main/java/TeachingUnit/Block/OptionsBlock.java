package TeachingUnit.Block;

import TeachingUnit.Grade;
import TeachingUnit.SchoolClass;

/**
 * @author Quentin Garnier
 */

public class OptionsBlock extends Block {
    private SchoolClass[] options;

    /**
     * @param classes Options of the block, with the SAME number of credits!
     */
    public OptionsBlock(String n, String c, SchoolClass[] classes) {
        super(n, c, classes[0].getNbCredits());
        this.options = classes;
    }

    public SchoolClass[] getOptions() {
        return this.options;
    }

    public int nbOfOptions() {
        return this.options.length;
    }

    public float getAverageGrade() {
        float avg = 0;
        for(SchoolClass cl : this.options) if(cl.getAverageGrade() > avg) avg = cl.getAverageGrade();
        return avg;
    }

    public void addGrade(Grade g, String code) {
        for(SchoolClass scl : this.options) {
            if(code.equals(scl.getCode())) {
                scl.addGrade(g);
                break;
            }
        }
    }
}
