package com.example.finalprojectpm.db.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    private PasswordUtil(){}
    public static String generateStrongPasswordHash(String password)
    {
        int logRounds = 12;
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }
    public static boolean validatePassword(String originalPassword, String storedPassword)
    {
        return BCrypt.checkpw(originalPassword,storedPassword);
    }
}