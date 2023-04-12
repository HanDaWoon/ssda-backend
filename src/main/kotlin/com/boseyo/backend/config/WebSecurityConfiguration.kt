package com.boseyo.backend.config

import com.boseyo.backend.config.jwt.JwtAuthenticationFilter
import com.boseyo.backend.config.jwt.JwtAuthorizationFilter
import com.boseyo.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return AuthenticationManager { it }
    }

    @Autowired
    private val userRepository: UserRepository? = null

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = listOf("https://ssda.dawoony.com", "http://localhost:3000")
        configuration.allowCredentials
        configuration.addAllowedOriginPattern("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
//        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .addFilter(JwtAuthenticationFilter(authenticationManager()))
            .addFilter(JwtAuthorizationFilter(authenticationManager(), userRepository!!))
            .authorizeHttpRequests()
            .requestMatchers("/api/user/cert/**")
            .hasAnyRole("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
            .requestMatchers("/user/manager/**")
            .hasAnyRole("ROLE_MANAGER", "ROLE_ADMIN")
            .requestMatchers("/api/admin/**")
            .hasRole("ROLE_ADMIN")
            .anyRequest().permitAll()
        return http.build()
    }
}


