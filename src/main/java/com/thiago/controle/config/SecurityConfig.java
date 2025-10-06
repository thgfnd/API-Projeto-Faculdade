package com.thiago.controle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Libera o acesso aos arquivos públicos do frontend
                        .requestMatchers(
                                "/",
                                "/*.html",
                                "/css/**",
                                "/app.js",
                                "/admin.js",
                                "/cliente.js",
                                "/fundo-oficina.jpg" // Permite a imagem na raiz
                        ).permitAll()

                        // 2. Libera endpoints compartilhados para QUALQUER usuário logado (Admin ou Cliente)
                        .requestMatchers(
                                "/api/user/me",
                                "/api/veiculos/{id}", // <-- ROTA ADICIONADA AQUI
                                "/api/veiculos/{id}/status-oleo",
                                "/api/veiculos/{id}/registrar-oleo",
                                "/api/veiculos/{id}/quilometragem",
                                "/api/veiculos/{id}/intervalos-troca"
                        ).authenticated()

                        // 3. Regras específicas do Cliente
                        .requestMatchers("/cliente.html", "/api/veiculos/meus-veiculos").hasAuthority("ROLE_CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/veiculos/meus-veiculos").hasAuthority("ROLE_CLIENTE")

                        // 4. Regra geral do Admin (vem por último)
                        .requestMatchers("/admin.html", "/api/**").hasAuthority("ROLE_ADMIN")

                        // 5. Qualquer outra requisição precisa de autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler)
                        .failureUrl("/?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }
}