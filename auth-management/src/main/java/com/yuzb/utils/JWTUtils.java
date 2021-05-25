package com.yuzb.utils;


import com.yuzb.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.function.Consumer;

public class JWTUtils {

    public static final String TOKEN_HEADER = "token";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SUBJECT = "mayikt";

    private static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;

    private static final String APPSECRET_KEY = "@4asdfaretokenyuzb";

    private static final String ROLE_CLAIMS = "roles";

    public static String generateJsonWebToken(User user) {
        String token = Jwts
                .builder()
                .setSubject(SUBJECT)
                .claim(ROLE_CLAIMS, user.getAuthorities())
                .claim("id", user.getUserId())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }

    /**
     * 生成token
     *
     * @param username
     * @param role
     * @return
     */
    public static String createToken(String username, String role) {

        Map<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setClaims(map)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }

    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 获取用户角色
     *
     * @param token
     * @return
     */
    public static List<SimpleGrantedAuthority> getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        ArrayList<LinkedHashMap> roles = (ArrayList<LinkedHashMap>) claims.get(ROLE_CLAIMS);
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        roles.forEach(new Consumer<LinkedHashMap>() {
            @Override
            public void accept(LinkedHashMap linkedHashMap) {
                grantedAuthorityList.add(new SimpleGrantedAuthority((String) linkedHashMap.get("authority")));
            }
        });
        return grantedAuthorityList;
    }

    /**
     * 是否过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

}
