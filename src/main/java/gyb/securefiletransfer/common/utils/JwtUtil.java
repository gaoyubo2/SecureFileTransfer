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

    public static final long EXPIRE = 1000 * 60 * 60 * 240;
    /**
     * 秘钥
     */
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 生成token字符串
     */
    public static String getJwtToken(Integer id, String username) {

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
        if (jwtToken == null ||jwtToken.isEmpty()) {
            throw new MyException(20001,"不存在token或过期token");
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            throw new MyException(20001,"错误的token:"+e.getMessage());
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
            String jwtToken = request.getHeader("Token");
            if (jwtToken == null || jwtToken.isEmpty()) {
                throw new MyException(20001,"不存在token或过期token");
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            throw new MyException(20001,"错误的token");
        }
        return true;
    }

    /**
     * 根据HttpServletRequest中的token获取userId
     *
     * @param request HttpServletRequest
     * @return userId
     */
    public static Integer getMemberIdRequest(HttpServletRequest request) {
        String jwtToken = request.getHeader("Token");
        if (!checkToken(jwtToken)) {
            return -1;
        }
        return getID(jwtToken);
    }

    private static Integer getID(String jwtToken) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            throw new MyException(20001, "token过期");
        }
        Claims claims = claimsJws.getBody();
        return (Integer) claims.get("id");
    }

    /**
     * 根据token获取id
     *
     * @param jwtToken token
     * @return userId
     */
    public static Integer getMemberIdByToken(String jwtToken) {
        if (!checkToken(jwtToken)) {
            return -1;
        }
        return getID(jwtToken);
    }

}
