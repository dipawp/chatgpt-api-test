package com.dipawp.chatgpt.configuration;

import com.dipawp.chatgpt.service.CustomUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AppWebAuthConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	
//    	 HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
//    	    requestCache.setMatchingRequestParameterName(null);
//    	    http
//    	        // ...
//    	        .requestCache((cache) -> cache
//    	            .requestCache(requestCache)
//    	        );
        //http.oauth2ResourceServer((oauth2) -> oauth2
        //        .jwt(Customizer.withDefaults()));

        //http.formLogin().permitAll();
    	//http.csrf(csrf -> csrf.disable());
//        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/","/login").permitAll()
//                        .requestMatchers("/completion/**").authenticated()
//                        .anyRequest().authenticated());
//
//        http.formLogin(formLogin -> formLogin.loginPage("/login").permitAll());

        http
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/users").authenticated()
                        .anyRequest().permitAll()).formLogin(
                        form -> form.loginPage("/login").usernameParameter("email").defaultSuccessUrl("/user").permitAll()
                ).logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        
        //http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }



    @Bean
    public DaoAuthenticationProvider getDaoAuthProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
