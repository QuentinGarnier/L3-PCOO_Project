package TeachingUnit;

/**
 * @author Quentin Garnier
 *
 * A grade can be an int between 0 and 20, or "ABI" (abscence), or "ERROR" (error when declared)
 */

public class Grade {
    private int value;
    private boolean abi;

    // Une note mal renseignée (donc pas entre 0 et 20) est comptée comme une ABI.
    // Une ABI a une valeur de 0.
    public Grade(int x) {
        this.abi = !(x>=0 && x<=20);
        this.value = (this.abi?0:x);
    }

    //Constructeur vide pour définir une ABI.
    public Grade() {
        this.value = 0;
        this.abi = true;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isAnABI() {
        return this.abi;
    }
}
