import java.math.BigInteger;
import java.security.MessageDigest;

public class Q4three {
    public static void main(String[] args) throws Exception {
        // Given constants (decimal)
        BigInteger p = new BigInteger(
                "178011905478542266528237562450159990145232156369120674273274450314442865788737020770612695252123463079567156784778466449970650770920727857050009668388144034129745221171818506047231150039301079959358067395348717066319802262019714966524135060945913707594956514672855690606794135837542707371727429551343320695239");
        BigInteger g = new BigInteger(
                "174068207532402095185811980123523436538604490794561350978495831040599953488455823147851597408940950725307797094915759492368300574252438761037084473467180148876118103083043754985190983472601550494691329488083395492313850000361646482644608492304078721818959999056496097769368017749273708962006689187956744210730");

        // Generate private keys (a, b) as 160-bit random numbers
        BigInteger a = new BigInteger(160, new java.util.Random());
        BigInteger b = new BigInteger(160, new java.util.Random());

        // Compute public keys (A, B)
        BigInteger A = g.modPow(a, p); // A = g^a mod p
        BigInteger B = g.modPow(b, p); // B = g^b mod p

        // Compute shared secret (K)
        BigInteger K_vpc = B.modPow(a, p); // K = B^a mod p (VPC side)
        BigInteger K_dc = A.modPow(b, p); // K = A^b mod p (Data Centre side)

        System.out.println("A (hex): " + A.toString(16));
        System.out.println("B (hex): " + B.toString(16));
        System.out.println("Shared secret K (hex): " + K_vpc.toString(16));
        System.out.println("Shared secrets match: " + K_vpc.equals(K_dc));
    }
}