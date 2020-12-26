package program;

import teachingunit.Block.Block;
import teachingunit.Grade;
import java.util.ArrayList;

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

    public float getAverageGrade(Grade[] grades) {
        float avg = 0;
        int totalCredits = 0;
        for(Block b : this.blocks) {
            avg += b.getAverageGrade(grades)*b.getNbCredits();
            totalCredits += b.getNbCredits();
        }
        return (totalCredits==0? 0:avg/totalCredits);
    }

    public float getAverageGrade(ArrayList<Grade> grades) {
        Grade[] gds = grades.toArray(new Grade[0]);
        return this.getAverageGrade(gds);
    }

    // Fonction vérifiant si la moyenne du programme est d'au moins 10/20.
    public boolean isValidated(Grade[] grades) {
        return this.getAverageGrade(grades) >= 10;
    }
}
