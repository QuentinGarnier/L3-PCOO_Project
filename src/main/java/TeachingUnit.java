/**
 * @author Quentin Garnier
 */

public class TeachingUnit {
    private String code;
    private String name;
    private int nbCredits;

    public TeachingUnit(String n, String c, int nb) {
        this.name = n;
        this.code = c;
        this.nbCredits = nb;
    }

    public String getName() {
        return this.name;
    }

    
}
