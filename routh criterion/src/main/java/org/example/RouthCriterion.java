package org.example;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.analysis.solvers.PolynomialSolver;
import org.apache.commons.math3.complex.Complex;
public class RouthCriterion {
    public static final double EPSILON = 1E-9;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the degree of the characteristic equation
        System.out.print("Enter the degree of the characteristic equation: ");
        int degree = scanner.nextInt();

        // Create arrays to store the coefficients
        double[] coefficients = new double[degree + 1];
        for (int i = 0; i <= degree; i++) {
            System.out.print("Enter coefficient of S^" + (degree - i) + ": ");
            coefficients[i] = scanner.nextDouble();
        }

        boolean[] hasDuplicateImgRoots = new boolean[]{false};
        double[][] routhTable = createRouthTable(coefficients, hasDuplicateImgRoots);

        // Print the Routh table and determine stability
        printRouthTable(routhTable, routhTable.length - 1);

        boolean hasRootsInRHS = checkStability(routhTable, hasDuplicateImgRoots);
        if(hasRootsInRHS)
            findRootsInRHS(coefficients);
    }
    public static boolean checkZeroRow(double[][] routhTable, int rowIndex){
        boolean currentRowIsAllZeros = true;
        for (int j = 0; j < routhTable[0].length; j++){
            if (routhTable[rowIndex][j] != 0 && routhTable[rowIndex][j] != EPSILON){
                currentRowIsAllZeros = false;
                break;
            }
        }
        return currentRowIsAllZeros;
    }
    public static void replaceZeroRow(double[][] routhTable, int zeroRowIndex){
        int exponent = routhTable.length - 1;
        for (int j = 0; j < routhTable[0].length; j++) {
            routhTable[zeroRowIndex][j] = routhTable[zeroRowIndex - 1][j] * (exponent - zeroRowIndex + 1 );
            if(routhTable[zeroRowIndex][j] == -0)
                routhTable[zeroRowIndex][j] = 0;
            exponent -= 2;
        }
    }
    public static void printRouthTable(double[][] routhTable, int rowLimit) {
        // Find the maximum width of the values
        int maxWidth = 0;
        for (double[] row : routhTable) {
            for (double value : row) {
                int width = String.format("%.3f", value).length();
                maxWidth = Math.max(maxWidth, width);
            }
        }
        System.out.println("Routh Table:");
        for (int i = 0; i <= rowLimit; i++) {
            System.out.print("S^" + (routhTable.length - 1 - i) + "\t\t");
            for (int j = 0; j < routhTable[0].length; j++) {
                if (routhTable[i][j] != EPSILON)
                    System.out.printf("%-" + (maxWidth + 4) + ".3f", routhTable[i][j]);
                else
                    System.out.printf("%-" + (maxWidth + 4) + "s", String.format("%.0E", EPSILON));
            }
            System.out.println();
        }
    }
    public static boolean checkStability(double[][] routhTable, boolean[] hasDuplicateImgRoots){
        int signChanges = 0;
        for (int i = 0; i < routhTable.length - 1; i++) {
            if (routhTable[i][0] * routhTable[i + 1][0] < 0) {
                signChanges++;
            }
        }

        // Check stability based on sign changes
        if (signChanges == 0 && !hasDuplicateImgRoots[0])
            System.out.println("The system is stable.");
        else if (signChanges == 0 && hasDuplicateImgRoots[0])
            System.out.println("Although there are no sign changes in the first column. " +
                    "The system is unstable because of duplicates on imaginary axis.");
        else{
            System.out.println("The system is unstable.");
            System.out.println("Number of poles in RHS = number of sign changes = " + signChanges);
            return true;
        }
        return false;
    }
    public static double[][] createRouthTable(double[] coefficients, boolean[] hasDuplicateImgRoots){
        int n = coefficients.length;
        double[][] routhTable = new double[n][(n + 1) / 2];

        // Fill in the first two rows of the Routh table
        for (int i = 0; i < n; i += 2) {
            routhTable[0][i / 2] = coefficients[i];
            if (i + 1 < n) {
                routhTable[1][i / 2] = coefficients[i + 1];
            }
        }

        // Fill in the rest of the Routh table
        for (int i = 2; i < n; i++) { // n = 5
            for (int j = 0; j < (n + 1) / 2 - 1; j++) {
                double a = routhTable[i - 2][0];
                double b = routhTable[i - 2][j + 1];
                double c = routhTable[i - 1][0];
                double d = routhTable[i - 1][j + 1];
                routhTable[i][j] = (b * c - a * d) / c;

                if(routhTable[i][j] == 0 && j == 0)
                    routhTable[i][j] = EPSILON;
                if(routhTable[i][j] == -0)
                    routhTable[i][j] = 0;
            }
            if(checkZeroRow(routhTable, i)){
                printRouthTable(routhTable, i);
                double[] auxiliaryCoefficients = getAuxiliaryCoefficients(routhTable, i);
                replaceZeroRow(routhTable, i);
                hasDuplicateImgRoots[0] = checkDuplicateRoots(auxiliaryCoefficients) || hasDuplicateImgRoots[0];
            }
        }
        return routhTable;
    }
    public static double[] getAuxiliaryCoefficients(double[][] routhTable, int zeroRowIndex){
        double[] auxiliaryCoefficients = new double[routhTable.length - zeroRowIndex + 1];
        int colIndex = 0;
        //get auxiliaryCoefficients
        for(int i = 0; i < auxiliaryCoefficients.length ; i++) {
            if(i % 2 == 1){
                auxiliaryCoefficients[auxiliaryCoefficients.length - 1 - i] = 0;
                continue;
            }
            auxiliaryCoefficients[auxiliaryCoefficients.length - 1 - i] = routhTable[zeroRowIndex - 1][colIndex++];
        }
        return auxiliaryCoefficients;
    }
    public static boolean checkDuplicateRoots(double[] auxiliaryCoefficients){
        PolynomialFunction polynomial = new PolynomialFunction(auxiliaryCoefficients);
        // Create a PolynomialSolver object (LaguerreSolver) to find the roots
        PolynomialSolver solver = new LaguerreSolver();
        // Find all roots (including complex roots)
        Complex[] roots = ((LaguerreSolver) solver).solveAllComplex(polynomial.getCoefficients(), 0.0);

        Map<Double, Integer> imaginaryPartCount = new HashMap<>();

        // Count the occurrences of each imaginary part on imaginary axis
        for (Complex root : roots) {
            if(root.getReal() == 0 || root.getReal() <= EPSILON){
                double imgPart = Math.round(root.getImaginary() * 1e6) / 1e6; // Round to 6 decimal places
                imaginaryPartCount.put(imgPart, imaginaryPartCount.getOrDefault(imgPart, 0) + 1);
            }
        }

        // Check for duplicate imaginary parts
        boolean hasDuplicateImaginary = false;
        for (Map.Entry<Double, Integer> entry : imaginaryPartCount.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println("Duplicate roots on the imaginary axis: " + entry.getKey() + "j");
                hasDuplicateImaginary = true;
            }
        }
        if (!hasDuplicateImaginary) {
            System.out.println("No duplicate roots on the imaginary axis.");
        }
        System.out.println();
        return hasDuplicateImaginary;
    }
    public static void findRootsInRHS(double[] coefficients) {
        //make it suitable for function
        for (int i = 0; i < coefficients.length / 2; i++) {
            double temp = coefficients[i];
            coefficients[i] = coefficients[coefficients.length - 1 - i];
            coefficients[coefficients.length - 1 - i] = temp;
        }

        PolynomialFunction polynomial = new PolynomialFunction(coefficients);
        // Create a PolynomialSolver object (LaguerreSolver) to find the roots
        PolynomialSolver solver = new LaguerreSolver();
        // Find all roots (including complex roots)
        Complex[] roots = ((LaguerreSolver) solver).solveAllComplex(polynomial.getCoefficients(), 0.0);

        System.out.println("Roots of the equation in RHS: ");
        for (Complex root : roots) {
            double realPart = Math.round(root.getReal() * 1e6) / 1e6; // Round to 6 decimal places
            double imgPart = Math.round(root.getImaginary() * 1e6) / 1e6; // Round to 6 decimal places
            if (realPart > 0) {
                if (imgPart == 0) {
                    System.out.println("Real root: " + realPart);
                } else {
                    System.out.println("Complex root: " + realPart + " + " + imgPart + "j");
                }
            }
        }
    }
}
