package cl.duoc.comunidadgamer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.duoc.comunidadgamer.security.Constantes;
import cl.duoc.comunidadgamer.security.JwtAuthorizationFilter;
import cl.duoc.comunidadgamer.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private final LoginSuccessHandler loginSuccessHandler;

    public WebSecurityConfig(
            JwtAuthorizationFilter jwtAuthorizationFilter,
            LoginSuccessHandler loginSuccessHandler) {

        this.jwtAuthorizationFilter =
                jwtAuthorizationFilter;

        this.loginSuccessHandler =
                loginSuccessHandler;
    }

        @Bean
        public SecurityFilterChain securityFilterChain(
                HttpSecurity http)
                throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(authz -> authz

                .requestMatchers(
                        "/",
                        "/home",
                        Constantes.LOGIN_URL,
                        "/eventos",
                        "/api/auth/**")
                .permitAll()

                .requestMatchers("/**.css")
                .permitAll()

                .anyRequest()
                .authenticated())

                .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives(
                        "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'"))
                )

                .formLogin(form -> form

                .loginPage(
                        Constantes.LOGIN_URL)

                .successHandler(
                        loginSuccessHandler)

                .permitAll())

                .logout(logout -> logout

                .logoutSuccessUrl(
                        "/home")

                .permitAll());

        http.addFilterAfter(
                jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
        }

    @Bean
    @Description("Usuarios almacenados en memoria para pruebas")
    public UserDetailsService users() {

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("USER", "ADMIN")
                .build();

        UserDetails doctor = User.builder()
                .username("doctor")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(
                user,
                admin,
                doctor);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}