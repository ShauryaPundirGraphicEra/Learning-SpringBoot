package org.example.irctc.util;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {
    public static String hashPassword(String rawPassword){
        return BCrypt.hashpw(rawPassword,BCrypt.gensalt());
    }

    public static boolean checkPassword(String rawPassword,String hashedPassword){
        return BCrypt.checkpw(rawPassword,hashedPassword);
    }
}
