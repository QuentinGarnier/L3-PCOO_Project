package teachingunit;

/**
 * @author Quentin Garnier
 *
 * A grade can be an int between 0 and 20, or "ABI" (abscence), or "ERROR" (error when declared)
 */

public class Grade {
    private double value;
    private boolean abi;
    private String code;

    /**
     * @param x Valeur de la note (entre 0 et 20). Une note mal renseignée (donc pas entre 0 et 20) est comptée comme une ABI et a une valeur de 0.
     * @param c Code du cours associé à la note.
     */
    public Grade(double x, String c) {
        this.abi = !(x>=0 && x<=20);
        this.value = (this.abi?0:x);
        this.code = c;
    }

    //Constructeur vide pour définir une ABI.
    public Grade(String c) {
        this.value = 0;
        this.abi = true;
        this.code = c;
    }

    public double getValue() {
        return this.value;
    }

    public boolean isAnABI() {
        return this.abi;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return (this.abi?"ABI":""+this.value);
    }

}
