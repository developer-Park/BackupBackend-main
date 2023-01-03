package ca.sait.backup.utils;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;


import java.util.Date;

/**
 * Jwt工具类
 * 注意点:
 * 1、生成的token, 是可以通过base64进行解密出明文信息
 * 2、base64进行解密出明文信息，修改再进行编码，则会解密失败
 * 3、无法作废已颁布的token，除非改秘钥
 * Jwt tool class
 * be careful:
 * 1. The generated token can decrypt the plaintext information through base64
 * 2. Decrypt the plaintext information with base64, modify and then encode, the decryption will fail
 * 3. The issued token cannot be invalidated unless the secret key is changed
 */

//Writer : John
public class
JWTUtils {


    /**
     * expired date: one week
     */
    private  static final long EXPIRE = 60000 * 60 * 24 * 7;
    //private  static final long EXPIRE = 1;

    /**
     *  encryption key
     */
    private  static final String SECRET = "ca.sait666";
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);
    /**

    /**
     * depend on user info to generate user token
     * @param user
     * @return
     */
    public static String geneJsonWebToken(Authentication authentication, String sessionData){
        String name = authentication.getName();
        String token = Jwts.builder().setSubject(name)
                .claim("sessionData", sessionData)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();

        return token;
    }

    public static String geneJsonGoogleWebToken(String email, String sessionData){
        String token = Jwts.builder().setSubject(email)
                .claim("sessionData", sessionData)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();

        return token;
    }

    /**
     * the method to check token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
        try{
            final Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){
            return null;
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }



}
