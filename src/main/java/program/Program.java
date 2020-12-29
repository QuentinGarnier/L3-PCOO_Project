package program;

import teachingunit.block.Block;
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

    public Grade getGrade(Grade[] grades) {
        double avg = 0;
        int totalCredits = 0;
        boolean oneGradeMin = false;
        boolean onlyABI = true;

        if(this.blocks != null) for(Block b : this.blocks) {
            Grade grd = b.getGrade(grades); //on récupère la note du bloc (null si pas de note)
            if(grd != null) {
                if(!oneGradeMin) oneGradeMin = true;
                avg += grd.getValue() * b.getNbCredits();
                totalCredits += b.getNbCredits();
                if(!grd.isAnABI()) onlyABI = false;
            }
        }

        if(oneGradeMin) {
            avg = (totalCredits == 0 ? 0 : avg / totalCredits); //opérateur ternaire juste en sécurité pour éviter les divisions par 0
            Grade res = new Grade(avg, this.getCode());
            res.setABI(onlyABI);
            return res;
        }
        return null;
    }

    public Grade getGrade(ArrayList<Grade> grades) {
        Grade[] gds = grades.toArray(new Grade[0]);
        return this.getGrade(gds);
    }

    public boolean equals(Program p) {
        if(p == null) return false;
        return this.code.equals(p.getCode());
    }

    @Override
    public String toString() {
        return this.code + " - " + this.name;
    }
}
