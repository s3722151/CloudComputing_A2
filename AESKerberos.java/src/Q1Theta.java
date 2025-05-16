public class Q1Theta {
    public static void main(String[] args) {
        // Column parity C[x] (precomputed)
        int[] C = { 0x4C, 0x38, 0x1A, 0x7D, 0x50 };

        // Step 1: Calculate D[x] = C[(x-1)%5] ⊕ rot(C[(x+1)%5], 1)
        int[] D = new int[5];
        for (int x = 0; x < 5; x++) {
            int left = C[(x - 1 + 5) % 5]; // Handle wrap-around
            int right = C[(x + 1) % 5];
            int rotatedRight = rotateLeft(right, 1); // Rotate left by 1 bit
            D[x] = left ^ rotatedRight;
        }

        // State matrix (y=4 at the top, y=0 at the bottom)
        int[][] A = {
                { 0x6E, 0x6F, 0x2C, 0x20, 0x73 }, // y=4
                { 0x61, 0x79, 0x65, 0x74, 0x61 }, // y=3
                { 0x68, 0x61, 0x6E, 0x20, 0x43 }, // y=2
                { 0x61, 0x20, 0x4E, 0x61, 0x74 }, // y=1
                { 0x4A, 0x6F, 0x73, 0x68, 0x75 }  // y=0
        };

        // Step 2: Update state A[x][y] ^= D[x]
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                A[x][y] ^= D[x];
            }
        }

        // Print results
        System.out.println("D[x] values:");
        for (int x = 0; x < 5; x++) {
            System.out.printf("D[%d] = %02x\n", x, D[x]);
        }

        System.out.println("\nState after Theta step:");
        System.out.println("y\\x   x=0    x=1    x=2    x=3    x=4");
        System.out.println("----------------------------------------");
        for (int y = 4; y >= 0; y--) {
            System.out.printf("y=%d | ", y);
            for (int x = 0; x < 5; x++) {
                System.out.printf("%02x    ", A[x][y]);
            }
            System.out.println();
        }
    }

    private static int rotateLeft(int value, int bits) {
        return ((value << bits) | (value >>> (8 - bits))) & 0xFF;
    }
}