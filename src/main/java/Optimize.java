public class Optimize {
    public static void main(String[] args) {

        double c = 20.0;
        double I = 0.25;

        double p = 20.0;
        double K = 100.0;
        double λ = 3 * 500;
        double μ = 500.0;
        double σ = 100.0;


        double[] optimalValues = calculateOptimalValues(c,I,p,K,λ,μ,σ,100,0.0001);


        System.out.printf("Optimal Q: %.4f%n", optimalValues[0]);
        System.out.printf("Optimal R: %.4f%n", optimalValues[1]);
        System.out.printf("Holding Cost: %.4f%n", optimalValues[2]);
        System.out.printf("Ordering Cost: %.4f%n", optimalValues[3]);
        System.out.printf("Penalty Cost: %.4f%n", optimalValues[4]);
        System.out.printf("Average Time Between Orders: %.4f%n", optimalValues[5]);
        System.out.printf("Proportion No Stock Out: %.4f%n", optimalValues[6]);
        System.out.printf("Proportion Unmet Demand: %.4f%n", optimalValues[7]);
        System.out.printf("Difference R and Mean Demand: %.4f%n", optimalValues[8]);

    }

    public static double[] calculateOptimalValues(double c, double I, double p, double K, double λ, double μ, double σ, int maxIterations, double tolerance) {
        double h = I * c;

        double Q0 = Math.sqrt(2 * K * λ / h);


        double F_R0 = 1 - (Q0 * h) / (p * λ);


        double current_z = ReadZtable.calculateZ(F_R0);



        double prev_R = μ + (σ * current_z);


        double[] optimalValues = new double[9];

        double Q = 0;

        for (int iteration = 1; iteration <= maxIterations; iteration++) {

            double Lz = ReadZtable.findL(current_z);
            double R = μ + (σ * current_z);
            double nR = σ * Lz;
            Q = Math.sqrt((2 * λ * (K + (p * nR))) / h);



            if (Math.abs(Q - Q0) < tolerance) {
                break;
            } else if (Math.abs(R - prev_R) < tolerance) {
                if (iteration != 1) break;
            }
            double F_R = 1 - (Q * h) / (p * λ);
            Q0 = Q;
            prev_R = R;
            current_z = ReadZtable.calculateZ(F_R);
        }

        optimalValues[0] = Q0;
        optimalValues[1] = prev_R;
        optimalValues[2] = h * (Q0 / 2 + prev_R - μ);
        optimalValues[3] = K * λ / Q;
        optimalValues[4] = p * λ * (σ * ReadZtable.findL(current_z)) / Q;
        optimalValues[5] = Q / λ;
        optimalValues[6] = 1 - (Q * h) / (p * λ);
        optimalValues[7] = (σ * ReadZtable.findL(current_z)) / Q;
        optimalValues[8] = prev_R - μ;

        return optimalValues;
    }
}