public class Q1Chi {
    public static void main(String[] args) {
        // Input matrix (5x5) in hexadecimal (from Pi step output)
        // Note: y=0 is bottom, y=4 is top (same as your Pi output)
        int[][] inputMatrix = {
                { 0xc0, 0x7b, 0xd2, 0x9e, 0x91 }, // y=4 (top)
                { 0x24, 0x00, 0x47, 0x8e, 0xb7 }, // y=3
                { 0x91, 0xc5, 0x7a, 0x2a, 0x4e }, // y=2
                { 0xce, 0x8d, 0x03, 0x10, 0x55 }, // y=1
                { 0x9a, 0x06, 0x65, 0x53, 0xbe } // y=0 (bottom)
        };

        // Apply χ (Chi) step
        int[][] outputMatrix = computeChi(inputMatrix);

        // Print the result in the same format as your Pi output
        System.out.println("Output after (Chi) step:");
        printMatrix(outputMatrix);
    }

    // Computes χ (Chi) for a 5x5 state matrix
    public static int[][] computeChi(int[][] input) {
        int[][] output = new int[5][5];

        // Loop through each row (y)
        for (int y = 0; y < 5; y++) {
            // Loop through each column (x)
            for (int x = 0; x < 5; x++) {
                // Get B[x,y], B[x+1,y], B[x+2,y] (with wrap-around)
                int a = input[y][x];
                int b = input[y][(x + 1) % 5];
                int c = input[y][(x + 2) % 5];

                // Compute χ: A[x,y] = B[x,y] XOR (NOT B[x+1,y] AND B[x+2,y])
                // Mask with 0xFF to keep only 8 bits (byte)
                output[y][x] = a ^ ((~b) & c) & 0xFF;
            }
        }
        return output;
    }

    // Prints a 5x5 matrix in hex format (same format as your Pi output)
    public static void printMatrix(int[][] matrix) {
        System.out.println("y\\x   x=0    x=1    x=2    x=3    x=4");
        System.out.println("----------------------------------------");
        for (int y = 0; y < 5; y++) {
            System.out.printf("y=%d | ", 4 - y); // Show y coordinate in standard form (4 at top)
            for (int x = 0; x < 5; x++) {
                System.out.printf("%02x   ", matrix[y][x]);
            }
            System.out.println();
        }
    }
}