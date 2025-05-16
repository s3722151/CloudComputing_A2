
    <html>
        <head>
            <title>Cloud Security 2025 - AES-128-CBC</title>
            <style>
                body {font - family: Arial, sans-serif; margin: 20px; }
                input {margin: 5px; padding: 5px; }
                button {margin: 10px 0; padding: 8px 15px; }
                .error {color: red; }
            </style>
        </head>
        <body>
            <h1>Cloud Security 2025</h1>
            <h2>AES-128-CBC Encryption/Decryption</h2>

            <!-- ENCRYPTION SECTION -->
            <h3>Encryption</h3>
            <label>Plaintext (text):</label><br />
            <input id="encryptText" size="40" placeholder="Enter text" oninput="updateEncryptHex()" /><br />
            <label>Plaintext (hex):</label><br />
            <input id="encryptHex" size="40" placeholder="Enter hex (even length)" oninput="validateHex('encryptHex')" /><br />
            <label>Key (hex, 128-bit/32 chars):</label><br />
            <input id="encryptKey" size="32" placeholder="e.g., 00112233445566778899aabbccddeeff" oninput="validateHex('encryptKey')" /><br />
            <label>IV (hex, 128-bit/32 chars):</label><br />
            <input id="encryptIV" size="32" placeholder="e.g., ffeeddccbbaa99887766554433221100" oninput="validateHex('encryptIV')" /><br />
            <label>Ciphertext (hex):</label><br />
            <input id="ciphertextOutput" size="40" readonly /><br />
            <button onclick="encryptAES()">Encrypt</button>
            <p id="encryptError" class="error"></p>

            <!-- DECRYPTION SECTION -->
            <h3>Decryption</h3>
            <label>Ciphertext (hex):</label><br />
            <input id="decryptHex" size="40" placeholder="Enter hex ciphertext" oninput="validateHex('decryptHex')" /><br />
            <label>Key (hex, 128-bit/32 chars):</label><br />
            <input id="decryptKey" size="32" placeholder="Same as encryption key" oninput="validateHex('decryptKey')" /><br />
            <label>IV (hex, 128-bit/32 chars):</label><br />
            <input id="decryptIV" size="32" placeholder="Same as encryption IV" oninput="validateHex('decryptIV')" /><br />
            <label>Decrypted (hex):</label><br />
            <input id="decryptedHex" size="40" readonly /><br />
            <label>Decrypted (text):</label><br />
            <input id="decryptedText" size="40" readonly /><br />
            <button onclick="decryptAES()">Decrypt</button>
            <p id="decryptError" class="error"></p>

            <script>
// ======================
                // HELPER FUNCTIONS
                // ======================

                // Validate hex input (even length, hex chars only)
                function validateHex(inputId) {
    const input = document.getElementById(inputId);
                const value = input.value;
                const errorField = inputId.includes('encrypt') ? 'encryptError' : 'decryptError';

                if (!/^[0-9a-fA-F]*$/.test(value)) {
                    document.getElementById(errorField).textContent = "Invalid hex characters!";
                return false;
    } else if (value.length % 2 !== 0 && value.length > 0) {
                    document.getElementById(errorField).textContent = "Hex must be even-length!";
                return false;
    } else {
                    document.getElementById(errorField).textContent = "";
                return true;
    }
}

                // Convert text to hex (for encryption)
                function updateEncryptHex() {
    const textInput = document.getElementById("encryptText").value;
                let hex = "";
                for (let i = 0; i < textInput.length; i++) {
                    hex += textInput.charCodeAt(i).toString(16).padStart(2, "0");
    }
                document.getElementById("encryptHex").value = hex;
}

                // Convert hex to text (for decryption)
                function hexToText(hex) {
                    let text = "";
                for (let i = 0; i < hex.length; i += 2) {
                    text += String.fromCharCode(parseInt(hex.substr(i, 2), 16));
    }
                return text;
}

                // Convert hex string to Uint8Array
                function hexToBytes(hex) {
    const bytes = new Uint8Array(hex.length / 2);
                for (let i = 0; i < hex.length; i += 2) {
                    bytes[i / 2] = parseInt(hex.substr(i, 2), 16);
    }
                return bytes;
}

                // Convert Uint8Array to hex string
                function bytesToHex(bytes) {
    return Array.from(bytes).map(b => b.toString(16).padStart(2, '0')).join('');
}

                // ======================
                // CRYPTO FUNCTIONS
                // ======================

                async function encryptAES() {
    const plaintextHex = document.getElementById("encryptHex").value;
                const keyHex = document.getElementById("encryptKey").value;
                const ivHex = document.getElementById("encryptIV").value;
                const errorField = "encryptError";

                // Validate inputs
                if (!validateHex('encryptHex') || !validateHex('encryptKey') || !validateHex('encryptIV')) return;
                if (keyHex.length !== 32) {
                    document.getElementById(errorField).textContent = "Key must be 32 hex chars (128-bit)!";
                return;
    }
                if (ivHex.length !== 32) {
                    document.getElementById(errorField).textContent = "IV must be 32 hex chars (128-bit)!";
                return;
    }

                try {
        // Import key
        const keyBytes = hexToBytes(keyHex);
                const ivBytes = hexToBytes(ivHex);
                const plaintextBytes = hexToBytes(plaintextHex);

                const key = await crypto.subtle.importKey(
                "raw",
                keyBytes,
                {name: "AES-CBC" },
                false,
                ["encrypt"]
                );

                // Encrypt
                const ciphertext = await crypto.subtle.encrypt(
                {
                    name: "AES-CBC",
                iv: ivBytes
            },
                key,
                plaintextBytes
                );

                // Output hex
                document.getElementById("ciphertextOutput").value = bytesToHex(new Uint8Array(ciphertext));
                document.getElementById(errorField).textContent = "";
    } catch (e) {
                    document.getElementById(errorField).textContent = "Encryption failed: " + e.message;
    }
}

                async function decryptAES() {
    const ciphertextHex = document.getElementById("decryptHex").value;
                const keyHex = document.getElementById("decryptKey").value;
                const ivHex = document.getElementById("decryptIV").value;
                const errorField = "decryptError";

                // Validate inputs
                if (!validateHex('decryptHex') || !validateHex('decryptKey') || !validateHex('decryptIV')) return;
                if (keyHex.length !== 32) {
                    document.getElementById(errorField).textContent = "Key must be 32 hex chars (128-bit)!";
                return;
    }
                if (ivHex.length !== 32) {
                    document.getElementById(errorField).textContent = "IV must be 32 hex chars (128-bit)!";
                return;
    }

                try {
        // Import key
        const keyBytes = hexToBytes(keyHex);
                const ivBytes = hexToBytes(ivHex);
                const ciphertextBytes = hexToBytes(ciphertextHex);

                const key = await crypto.subtle.importKey(
                "raw",
                keyBytes,
                {name: "AES-CBC" },
                false,
                ["decrypt"]
                );

                // Decrypt
                const plaintext = await crypto.subtle.decrypt(
                {
                    name: "AES-CBC",
                iv: ivBytes
            },
                key,
                ciphertextBytes
                );

                // Output hex and text
                const plaintextHex = bytesToHex(new Uint8Array(plaintext));
                document.getElementById("decryptedHex").value = plaintextHex;
                document.getElementById("decryptedText").value = hexToText(plaintextHex);
                document.getElementById(errorField).textContent = "";
    } catch (e) {
                    document.getElementById(errorField).textContent = "Decryption failed: " + e.message;
    }
}
            </script>
        </body>
</html>