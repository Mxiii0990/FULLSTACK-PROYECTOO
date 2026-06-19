package com.buildmypc.compatibility_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

// Seguridad del microservicio de compatibilidad (validaciones).
//
// Este servicio actúa como "Resource Server": no hace login ni emite tokens (eso es de user-service),
// solo RECIBE peticiones que traen un JWT en la cabecera "Authorization: Bearer <token>", verifica que
// el token sea válido y decide, según el rol, si deja pasar o no.
@Configuration
public class SecurityConfig {

    // Se lee del application.properties. Es la MISMA clave con la que el user-service firmó el token.
    @Value("${jwt.secret}")
    private String secret;

    // Construye la clave HMAC-SHA256 a partir del texto del secreto.
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        // El decoder verifica que la firma sea correcta y que el token no esté vencido.
        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
    }

    // Convierte el claim "roles" del token en "authorities" de Spring Security.
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authorities = new JwtGrantedAuthoritiesConverter();
        authorities.setAuthoritiesClaimName("roles"); // de qué claim del token leer los roles
        authorities.setAuthorityPrefix("");           // sin prefijo extra: ya guardamos "ROLE_..." completo
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authorities);
        return converter;
    }

    // Aquí se define la cadena de filtros de seguridad.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationConverter converter) throws Exception {
        http
                // CSRF se desactiva por ser una API REST sin estado.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Documentación y consola h2: abiertas.
                        .requestMatchers("/docs/**", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
                        // LEER (GET): lo permite cualquier usuario autenticado con uno de estos roles.
                        .requestMatchers(HttpMethod.GET, "/api/v1/validations/**", "/api/v2/validations/**")
                        .hasAnyRole("ADMIN", "TECNICO", "CLIENTE")
                        // ESCRIBIR (POST/PUT/DELETE): solo ADMIN o TECNICO.
                        .requestMatchers("/api/v1/validations/**", "/api/v2/validations/**")
                        .hasAnyRole("ADMIN", "TECNICO")
                        // Cualquier otra ruta no listada: basta con estar autenticado.
                        .anyRequest().authenticated())
                // STATELESS: el servidor NO guarda sesión.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Activa la validación del JWT.
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)))
                // Permite que la consola h2 se muestre en el navegador.
                .headers(h -> h.frameOptions(f -> f.disable()));
        return http.build();
    }
}