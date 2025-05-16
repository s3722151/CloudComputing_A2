import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class KerberosPhase2 {
    public static void main(String[] args) throws Exception {
        // Hardcode Phase 1 outputs (in real Kerberos, these would be stored)
        String KC_S_Base64 = "a1b2c3..."; // Paste KC_S from Phase1-Step2 output
        byte[] KC_S = Base64.getDecoder().decode(KC_S_Base64);

        // === Step 3 (C → S) ===
        long ts = System.currentTimeMillis() / 1000;
        String authenticator = "{C: Joshua, ts: " + ts + "}";
        System.out.println("Phase2-Step3: C → S sends:\n" +
                "1. Ticket (from Phase1-Step2)\n" +
                "2. Authenticator: " + authenticator + " encrypted with KC_S");

        // === Step 4 (S → C) ===
        System.out.println("\nPhase2-Step4: S verifies and responds with {ts}KC_S");
    }
}