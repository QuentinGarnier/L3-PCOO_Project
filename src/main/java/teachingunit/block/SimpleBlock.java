package teachingunit.block;

import teachingunit.Grade;
import teachingunit.SchoolClass;

/**
 * @author Quentin Garnier
 */

public class SimpleBlock extends Block {
    private SchoolClass sClass;

    public SimpleBlock(String n, String c, int nb) {
        super(n, c, nb);
        this.sClass = new SchoolClass(n, c, nb);
    }

    // Not very useful for simple blocks:
    public SchoolClass[] getClasses() {
        return new SchoolClass[]{this.getSClass()};
    }

    public SchoolClass getSClass() {
        return this.sClass;
    }

    public float getAverageGrade(Grade[] grades) {
        return this.sClass.getAverageGrade(grades);
    }
}
