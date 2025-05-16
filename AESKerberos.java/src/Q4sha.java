import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Q4sha {

    public static String sha1(BigInteger number, String name) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // Convert BigInteger to byte array
            byte[] numberBytes = number.toByteArray();
            byte[] nameBytes = name.getBytes();

            // Concatenate numberBytes + nameBytes
            byte[] combined = new byte[numberBytes.length + nameBytes.length];
            System.arraycopy(numberBytes, 0, combined, 0, numberBytes.length);
            System.arraycopy(nameBytes, 0, combined, numberBytes.length, nameBytes.length);

            // Compute SHA-1
            byte[] hashBytes = md.digest(combined);
            BigInteger hashInt = new BigInteger(1, hashBytes);

            // Convert to 40-character hex string
            String hashtext = hashInt.toString(16);
            while (hashtext.length() < 40) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        BigInteger a = new BigInteger("836204261330556031970824693517419534224995436296");
        BigInteger b = new BigInteger("1179801408114423392233118431269275225083686068172");

        String firstName = "Joshua"; // Replace with your first name
        String surname = "Cayetano"; // Replace with your surname

        // Compute A = SHA1(a || firstName)
        String A = sha1(a, firstName);
        System.out.println("A = SHA1(a || " + firstName + ") = " + A);

        // Compute B = SHA1(b || surname)
        String B = sha1(b, surname);
        System.out.println("B = SHA1(b || " + surname + ") = " + B);
    }
}
