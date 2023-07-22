package swm_nm.morandi.auth.security;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import swm_nm.morandi.auth.constants.SecurityConstants;
import swm_nm.morandi.auth.response.TokenDto;
import swm_nm.morandi.member.domain.Member;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private Optional<Jws<Claims>> parseTokenToJws(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return Optional.of(claimsJws);

        }  catch (ExpiredJwtException e) {
            logger.info("Expired JWT Token");
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("Invalid JWT signature");
        } catch (UnsupportedJwtException e) {
            logger.info("Token not supported");
        } catch (IllegalArgumentException e) {
            logger.info("JWT Error");
        }

        return Optional.empty();
    }
    private Jws<Claims> getJws(String token) {
        return parseTokenToJws(token).orElseThrow(() -> new RuntimeException("getJws Method"));
    }
    public boolean validateToken(String token){
        return parseTokenToJws(token).isPresent();
    }

    public Long getUserIdfromToken(String token) {
        Claims claims = getJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    public TokenDto getTokens(Member member){
        String acccessToken = generateAccessToken(member.getMemberId(),"USER");
        
        String refreshToken = generateRefreshToken(member.getMemberId());
        return TokenDto.builder()
                .accessToken(acccessToken)
                .refreshToken(refreshToken)
                .build();
    }
    private Key getSecretKey() {

        //임시 테스트 용도
        return SecurityConstants.JWT_KEY;

        //return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }
    private String buildAccessToken(
            Long id, Date issuedAt, Date accessTokenExpiresIn, String role) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
                .setIssuer("MORANDI")
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim("type", "access_token")
                .claim("role", role)
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    private String buildRefreshToken(Long id, Date issuedAt, Date accessTokenExpiresIn) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
                .setIssuer("MORANDI")
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim("type", "refresh_token")
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    public String generateAccessToken(Long id, String role) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn =
                //현재시각 + SecurityConstants의 JWT_EXPIRATION
                new Date(issuedAt.getTime() + SecurityConstants.JWT_EXPIRATION);

        return buildAccessToken(id, issuedAt, accessTokenExpiresIn, role);
    }
    public String generateRefreshToken(Long id) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn =
                new Date(issuedAt.getTime() +  SecurityConstants.REFRESH_TOKEN_EXPIRATION);
        return buildRefreshToken(id, issuedAt, refreshTokenExpiresIn);
    }

//
//    public AccessTokenInfo parseAccessToken(String token) {
//        if (isAccessToken(token)) {
//            Claims claims = getJws(token).getBody();
//            return AccessTokenInfo.builder()
//                    .userId(Long.parseLong(claims.getSubject()))
//                    .role((String) claims.get(TOKEN_ROLE))
//                    .build();
//        }
//        throw InvalidTokenException.EXCEPTION;
//    }
//
//    public Long parseRefreshToken(String token) {
//        try {
//            if (isRefreshToken(token)) {
//                Claims claims = getJws(token).getBody();
//                return Long.parseLong(claims.getSubject());
//            }
//        } catch (ExpiredTokenException e) {
//            throw RefreshTokenExpiredException.EXCEPTION;
//        }
//        throw InvalidTokenException.EXCEPTION;
//    }
//
//    public Long getRefreshTokenTTlSecond() {
//        return jwtProperties.getRefreshExp();
//    }
//
//    public Long getAccessTokenTTlSecond() {
//        return jwtProperties.getAccessExp();
//    }
//

}