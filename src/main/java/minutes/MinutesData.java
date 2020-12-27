package minutes;

import teachingunit.Grade;
import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author Quentin Garnier
 */


class MinutesData {
    private int size;
    private double[] maximums;
    private double[] minimums;
    private double[] averages;
    private double[] stddevns; //écarts-types
    private ArrayList<ArrayList<Grade>> listForStddevns;
    private int[] numbersForAvg;

    MinutesData(int newSize) {
        this.size = newSize;
        this.maximums = new double[size];
        this.minimums = new double[size];
        this.averages = new double[size];
        this.stddevns = new double[size];
        this.listForStddevns = new ArrayList<ArrayList<Grade>>();
        this.numbersForAvg = new int[size];

        for(int i=0; i<this.size; i++) {
            this.maximums[i] = -1;
            this.minimums[i] = 21;
            this.averages[i] = 0;
            this.stddevns[i] = 0;
            this.listForStddevns.add(new ArrayList<Grade>());
            this.numbersForAvg[i] = 0;
        }
    }

    void addToData(Grade[] grades) {
        for(int i = 0; i<this.size; i++) {
            if(grades[i] != null) {
                double value = grades[i].getValue();
                if(this.maximums[i] < value) this.maximums[i] = value;
                if(this.minimums[i] > value) this.minimums[i] = value;
                this.averages[i] += value;
                this.numbersForAvg[i] += 1;
                this.listForStddevns.get(i).add(grades[i]);
            }
        }
    }

    void calculateAvgs() {
        for(int i = 0; i<this.size; i++) {
            if(this.numbersForAvg[i] > 0) this.averages[i] = this.averages[i] / this.numbersForAvg[i];
            else this.averages[i] = -1;
        }
    }

    /**
     * Fonction à appeler UNIQUEMENT après calcul de la moyenne !
     */
    void calculateStddevns() {
        for(int i = 0; i<this.size; i++) {
            double sum = 0;
            double avg = this.averages[i];
            int n = this.numbersForAvg[i];
            for(Grade g : this.listForStddevns.get(i)) {
                if(g != null) sum += pow((g.getValue() - avg), 2);
            }
            this.stddevns[i] = (n==0?-1:sqrt(sum/n));
        }
    }

    double[] getMaximums() {
        return this.maximums;
    }

    double[] getMinimums() {
        return this.minimums;
    }

    double[] getAverages() {
        return this.averages;
    }

    double[] getStddevns() {
        return this.stddevns;
    }
}
