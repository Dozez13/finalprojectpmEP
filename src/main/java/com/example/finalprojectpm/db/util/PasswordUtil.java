package com.example.finalprojectpm.db.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This class contains methods to hash user password and validate it upon login
 * It uses  password-hashing function which is name Bcrypt for it
 * @author Pavlo Manuilenko
 * @see BCrypt
 */
public class PasswordUtil {
    private PasswordUtil(){}

    /**
     * This method hashs a password using the OpenBSD bcrypt scheme
     * @param password password to be hashed
     * @return the hashed password
     */
    public static String generateStrongPasswordHash(String password)
    {
        int logRounds = 12;
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    /**
     * This method validate password with stored password in database
     * @param originalPassword check this password with previously hashed
     * @param storedPassword hashed password
     * @return true if password is in database and false otherwise
     */
    public static boolean validatePassword(String originalPassword, String storedPassword)
    {
        return BCrypt.checkpw(originalPassword,storedPassword);
    }
}