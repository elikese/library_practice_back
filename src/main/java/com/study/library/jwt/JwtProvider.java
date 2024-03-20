package com.study.library.jwt;

import com.study.library.entity.User;
import com.study.library.repository.UserMapper;
import com.study.library.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;

    private UserMapper userMapper;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Autowired UserMapper userMapper) {

        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.userMapper = userMapper;
    }

    public String generateToken(User user) {

        int userId = user.getUserId();
        String username = user.getUserName();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));

        String accessToken = Jwts.builder()
                .claim("userId",userId)
                .claim("username", username)
                .claim("authorities",authorities)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    public String removeBearer(String token) {
        if(!StringUtils.hasText(token)) {
            return null;
        }
        return token.substring("Bearer ".length());
    }

    public Claims getClaims(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT 인증오류: {}", e.getMessage());
        }

        return claims;
    }


    public Authentication getAuthentication(Claims claims) {
        String username = claims.get("username").toString();
        User user = userMapper.findUserByUserName(username);
        if(user == null) {
            return null;
        }
        PrincipalUser principalUser = user.toPrincipal();
        return new UsernamePasswordAuthenticationToken(principalUser, principalUser.getPassword(), principalUser.getAuthorities());
    }

    public String generateAuthMailToken(String toMail) {
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 1 * 5));
        return Jwts.builder()
                .claim("toMailAddress",toMail)
                .setExpiration(expireDate)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

}
