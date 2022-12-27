package ca.sait.backup.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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


    /**
     * token prefix
     */
   // private  static final String TOKEN_PREFIX = "backup";


    /**
     * subject
     */
    private  static final String SUBJECT = "backup";


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



}
