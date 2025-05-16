import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class KerberosPhase1 {
    public static void main(String[] args) throws Exception {
        // === Step 1 (C → AS) ===
        String C = "Joshua";
        String S = "Cayetano";
        String studentID = "3722151";
        int Lt = 8 * 3600;
        byte[] nC = MessageDigest.getInstance("MD5").digest(C.getBytes());
        System.out.println("Phase1-Step1: C → AS: {C, S, Lt, nC}\n" +
                "{C: " + C + ", S: " + S + ", Lt: " + Lt + ", nC: " + Base64.getEncoder().encodeToString(nC) + "}");

        // === Step 2 (AS → C) ===
        byte[] KC = MessageDigest.getInstance("MD5").digest((C + studentID).getBytes());
        byte[] KS = MessageDigest.getInstance("MD5").digest((S + studentID).getBytes());
        byte[] KC_S = new byte[16]; // Session key
        new SecureRandom().nextBytes(KC_S);

        System.out.println("\nPhase1-Step2: AS → C outputs:");
        System.out.println("KC (MD5(C+ID)): " + Base64.getEncoder().encodeToString(KC));
        System.out.println("KS (MD5(S+ID)): " + Base64.getEncoder().encodeToString(KS));
        System.out.println("KC_S (session key): " + Base64.getEncoder().encodeToString(KC_S));
    }
}