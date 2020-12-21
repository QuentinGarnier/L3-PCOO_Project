import TeachingUnit.Block.Block;

/**
 * @author Quentin Garnier
 */

public class Program {
    private String name;
    private String code;
    private Block[] blocks;

    public Program(String n, String c) {
        this.name = n;
        this.code = c;
        // A voir s'il est pertinent de définir les blocs directement dans le constructeur (surcharge ?).
    }

    public void setBlocks(Block... bs) {
        this.blocks = bs;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public Block[] getBlocks() {
        return this.blocks;
    }

    public float getAverageGrade() {
        float avg = 0;
        int totalCredits = 0;
        for(Block b : this.blocks) {
            avg += b.getAverageGrade()*b.getNbCredits();
            totalCredits += b.getNbCredits();
        }
        return (totalCredits==0? 0:avg/totalCredits);
    }

    // Fonction vérifiant si la moyenne du programme est d'au moins 10/20.
    public boolean isValidated() {
        return this.getAverageGrade() >= 10;
    }
}
