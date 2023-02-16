package com.tfm.aseguradora.backend.tfm.policy.config;

import com.tfm.aseguradora.backend.tfm.policy.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtilService.extractUsername(jwt);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // CONSULTA A BASE DE DATOS (QUE ESTA HARDCODEADA EN ESTE EJEMPLO) Y DEVUELVE UN OBJETO QUE CONTIENE EL NOMBRE Y LA CONTRASEÑA
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // AHORA VALIDA SI EL TOKEN ES VALIDO (SI EL USUARIO QUE VIENE EN EL JSON QUE HAY EN EL TOKEN COINCIDE CON EL DE BBDD Y
            // SI HA EXPIRADO O NO)
            if (jwtUtilService.validateToken(jwt, userDetails)) {


                // EN ESTA MARAÑA DE CODIGO SE RELLENA EL SECURITY
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}