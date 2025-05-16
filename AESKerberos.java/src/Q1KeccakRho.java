public class Q1KeccakRho {

    // Rho offsets (standard table for y=0 at top)
    private static final int[][] rhoOffsets = {
            { 0, 36, 3, 41, 18 }, // y=0 (top in standard Keccak)
            { 1, 44, 10, 45, 2 }, // y=1
            { 62, 6, 43, 15, 61 }, // y=2
            { 28, 55, 25, 21, 56 }, // y=3
            { 27, 20, 39, 8, 14 } // y=4 (bottom in standard Keccak)
    };

    // State matrix after Theta (from your output)
    private static final String[][] stateMatrix = {
            { "53", "19", "81", "ce", "90" }, // y=4 (top)
            { "00", "0c", "e2", "db", "8d" }, // y=3
            { "0c", "1d", "ac", "f4", "96" }, // y=2
            { "4f", "01", "a3", "9a", "8a" }, // y=1
            { "4e", "19", "aa", "db", "af" } // y=0 (bottom)
    };

    // Rotate 8-bit value
    public static String rotate(String hexValue, int offset) {
        int value = Integer.parseInt(hexValue, 16) & 0xFF;
        offset = offset % 8;
        int rotated = ((value << offset) | (value >>> (8 - offset))) & 0xFF;
        return String.format("%02x", rotated);
    }

    // Apply Rho with y=4 at top (matches Theta output)
    public static String[][] applyRho() {
        String[][] transformedMatrix = new String[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                // Your y=4 is standard Keccak's y=0 (top)
                // Your y=0 is standard Keccak's y=4 (bottom)
                int standardY = 4 - y;
                int offset = rhoOffsets[standardY][x];
                transformedMatrix[y][x] = rotate(stateMatrix[y][x], offset);
            }
        }
        return transformedMatrix;
    }

    public static void printMatrix(String[][] matrix) {
        System.out.println("y\\x   x=0    x=1    x=2    x=3    x=4");
        System.out.println("----------------------------------------");
        for (int y = 4; y >= 0; y--) {
            System.out.printf("y=%d | ", y);
            for (int x = 0; x < 5; x++) {
                System.out.printf("%s    ", matrix[y][x]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("State Matrix (After Theta):");
        printMatrix(stateMatrix);

        System.out.println("\nAfter Rho:");
        printMatrix(applyRho());
    }
}
