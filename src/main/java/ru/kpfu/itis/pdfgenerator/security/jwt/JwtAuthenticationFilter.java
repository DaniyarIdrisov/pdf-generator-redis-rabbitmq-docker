package ru.kpfu.itis.pdfgenerator.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kpfu.itis.pdfgenerator.redis.services.BlackListService;
import ru.kpfu.itis.pdfgenerator.services.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final BlackListService blackListService;
    private final TokenService tokenService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            String token = tokenService.getTokenFromHeader(bearerToken);

            if (blackListService.exists(token)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            JwtAuthentication tokenAuthentication = new JwtAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }
        filterChain.doFilter(request, response);
    }

}
