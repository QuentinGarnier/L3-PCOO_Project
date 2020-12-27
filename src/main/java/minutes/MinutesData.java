package minutes;

import teachingunit.Grade;

class MinutesData {
    private int size;
    private double[] maximums;
    private double[] minimums;
    private double[] averages;
    private double[] stddevns; //écarts-types
    private int[] numbersForAvg;

    MinutesData(int newSize) {
        this.size = newSize;
        this.maximums = new double[size];
        this.minimums = new double[size];
        this.averages = new double[size];
        this.stddevns = new double[size];
        this.numbersForAvg = new int[size];

        for(int i=0; i<this.size; i++) {
            this.maximums[i] = -1;
            this.minimums[i] = 21;
            this.averages[i] = 0;
            this.stddevns[i] = 0;
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
            }
        }
    }

    void calculateAvgs() {
        for(int i = 0; i<this.size; i++) {
            if(this.numbersForAvg[i] > 0) this.averages[i] = this.averages[i] / this.numbersForAvg[i];
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
