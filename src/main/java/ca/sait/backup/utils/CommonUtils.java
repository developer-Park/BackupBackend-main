package ca.sait.backup.utils;

import java.security.MessageDigest;

/**
 * Tool class
 * encryption password tool
 */

//Writer : John
public class CommonUtils {


    /**
     * SHA256 (Strong One-Way Hash)
     * @param data (Input string to be hashed)
     * @return (Uppercase string of hashed value)
     */
    public static String SHA256(String data)  {

        try {
            java.security.MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashArray = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : hashArray) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception exception) {

        }

        return null;

    }

}
