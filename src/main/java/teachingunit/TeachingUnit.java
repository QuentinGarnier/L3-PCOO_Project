package teachingunit;

/**
 * @author Quentin Garnier
 */

public abstract class TeachingUnit {
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

    public String getCode() {
        return this.code;
    }

    public int getNbCredits() {
        return this.nbCredits;
    }

    public void setNbCredits(int nb) {
        this.nbCredits = nb;
    }

    @Override
    public String toString() {
        return this.name+" ("+this.code+")";
    }
}
