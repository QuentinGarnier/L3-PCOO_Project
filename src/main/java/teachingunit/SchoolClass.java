package teachingunit;

/**
 * @author Quentin Garnier
 */

public class SchoolClass extends TeachingUnit {
    public SchoolClass(String n, String c, int nb) {
        super(n, c, nb);
    }

    public Grade getGrade(Grade[] grades) {
        for(Grade g : grades) if(g.getCode().equals(this.getCode())) return g;
        return null;
    }
}
