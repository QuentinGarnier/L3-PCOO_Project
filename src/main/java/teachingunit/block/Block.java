package teachingunit.block;

import teachingunit.Grade;
import teachingunit.SchoolClass;
import teachingunit.TeachingUnit;

/**
 * @author Quentin Garnier
 */

public abstract class Block extends TeachingUnit {
    Block(String n, String c, int nb) {
        super(n, c, nb);
    }

    public abstract float getAverageGrade(Grade[] grades);
    public abstract SchoolClass[] getClasses();
}
