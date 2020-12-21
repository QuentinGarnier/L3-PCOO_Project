package TeachingUnit.Block;

import TeachingUnit.Grade;
import TeachingUnit.SchoolClass;

/**
 * @author Quentin Garnier
 */

public class SimpleBlock extends Block {
    private SchoolClass sClass;

    public SimpleBlock(String n, String c, int nb) {
        super(n, c, nb);
        this.sClass = new SchoolClass(n, c, nb);
    }

    public SchoolClass getSClass() {
        return this.sClass;
    }

    public float getAverageGrade() {
        return this.sClass.getAverageGrade();
    }

    public void addGrade(Grade g, String code) {
        if(code.equals(this.sClass.getCode())) this.sClass.addGrade(g);
    }
}
