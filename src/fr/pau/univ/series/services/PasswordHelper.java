package fr.pau.univ.series.services;

import java.util.Arrays;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHelper {
    private final static String SALT = "NiIBwqIiFKU7k1e2ZAVrs6GB0j0";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    /**
    * Génerates an encrypted password from a clear one.
    *
    * @param password the readable form of the password.
    * @return the encrypted version of given password.
    */
    public static String generateSecurePassword(final String password) {
        String returnValue = null;
        final byte[] securePassword = hash(password.toCharArray(), SALT.getBytes());
        returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }

    /**
    * Checks if given clear and encrypted password match.
    *
    * @param providedPassword unencrypted version of password
    * @param securedPassword encrypted version of password
    * @return <code>true</code> if given clear and encrypted password match, <code>false</code> otherwise.
    */
    public static boolean verifyUserPassword(final String providedPassword, final String securedPassword) {
        final String newSecurePassword = generateSecurePassword(providedPassword);
        return newSecurePassword.equals(securedPassword);
    }

    private static byte[] hash(final char[] password, final byte[] salt) {
        final PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}