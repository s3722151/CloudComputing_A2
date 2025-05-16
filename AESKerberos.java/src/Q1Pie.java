public class Q1Pie {
    // State matrix after Rho (from your output)
    private static final String[][] stateMatrix = {
            { "4e", "91", "55", "b7", "be" }, // y=4 (top)
            { "9e", "10", "8e", "53", "2a" }, // y=3
            { "03", "47", "65", "7a", "d2" }, // y=2
            { "00", "06", "c5", "7b", "8d" }, // y=1
            { "9a", "91", "c0", "ce", "24" } // y=0 (bottom)
    };

    // Function to apply Pi transformation
    public static String[][] applyPiTransformation() {
        String[][] transformedMatrix = new String[5][5];

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                // Apply Pi transformation: (x, y) → (y, (2x + 3y) mod 5)
                // Note: Our matrix is stored with y=4 at top (index 0) and y=0 at bottom (index
                // 4)
                // So we need to adjust the y coordinates for the transformation

                // Current y in standard coordinates (where y=0 is bottom)
                int stdY = 4 - y;

                // Calculate new position
                int newX = stdY;
                int newY = (2 * x + 3 * stdY) % 5;

                // Convert newY back to our matrix coordinates
                int newMatrixY = 4 - newY;

                transformedMatrix[newMatrixY][newX] = stateMatrix[y][x];
            }
        }
        return transformedMatrix;
    }

    public static void printMatrix(String[][] matrix) {
        System.out.println("y\\x   x=0    x=1    x=2    x=3    x=4");
        System.out.println("----------------------------------------");
        for (int y = 0; y < 5; y++) {
            System.out.printf("y=%d | ", 4 - y); // Show y coordinate in standard form (4 at top)
            for (int x = 0; x < 5; x++) {
                System.out.printf("%4s  ", matrix[y][x]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("State Matrix (After Rho):");
        printMatrix(stateMatrix);

        System.out.println("\nAfter Pi:");
        printMatrix(applyPiTransformation());
    }
}