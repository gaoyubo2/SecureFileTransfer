package gyb.securefiletransfer.common.utils;


import gyb.securefiletransfer.common.handler.exceptionhandler.MyException;
import io.jsonwebtoken.*;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 郜宇博
 */
public class JwtUtil {
    /**
     * token的过期时间
     */

    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    /**
     * 秘钥
     */
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 生成token字符串
     */
    public static String getJwtToken(String id, String username) {

        return Jwts.builder()
                //设置头
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //设置过期时间
                .setSubject("user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置主体部分
                .claim("id", id)
                .claim("username", username)

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * 使用token直接 判断token是否存在与有效
     *
     * @param jwtToken token
     * @return token是否有效
     */
    public static boolean checkToken(String jwtToken) {
        if (jwtToken.isEmpty()) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 使用 HttpServletRequest 判断token是否存在与有效
     *
     * @param request HTTP请求
     * @return 是否
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if (jwtToken.isEmpty()) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据HttpServletRequest中的token获取userId
     *
     * @param request HttpServletRequest
     * @return userId
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (jwtToken.isEmpty()) {
            return "";
        }
        return getString(jwtToken);
    }

    private static String getString(String jwtToken) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            throw new MyException(20001, "token过期");
        }
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }

    /**
     * 根据token获取id
     *
     * @param jwtToken token
     * @return userId
     */
    public static String getMemberIdByToken(String jwtToken) {
        if (jwtToken.isEmpty()) {
            return "";
        }
        return getString(jwtToken);
    }

}
