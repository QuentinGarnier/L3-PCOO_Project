package TeachingUnit.Block;

import TeachingUnit.Grade;
import TeachingUnit.TeachingUnit;

/**
 * @author Quentin Garnier
 */

public abstract class Block extends TeachingUnit {
    Block(String n, String c, int nb) {
        super(n, c, nb);
    }

    public abstract float getAverageGrade(Grade[] grades);
}
