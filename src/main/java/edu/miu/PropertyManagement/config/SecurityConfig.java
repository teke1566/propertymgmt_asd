package edu.miu.PropertyManagement.config;


import edu.miu.PropertyManagement.enums.Roles;
import edu.miu.PropertyManagement.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;



    @Bean
    public UserDetailsService userDetailsSvc() {
        return userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disabling CSRF protection
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                .requestMatchers("/api/v1/authenticate/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/users").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET,"/api/v1/users/*").hasAnyAuthority(Roles.CUSTOMER.name(), Roles.OWNER.name())

                .requestMatchers(HttpMethod.GET, "/api/v1/properties/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/properties/city/*").hasAuthority(Roles.OWNER.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/properties/owner/*/cancelContingency").hasAuthority(Roles.OWNER.name())

                .requestMatchers(HttpMethod.GET, "/api/v1/properties").hasAuthority(Roles.OWNER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/properties/*").hasAnyAuthority(Roles.CUSTOMER.name(), Roles.OWNER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/properties/**").hasAuthority(Roles.OWNER.name())

                .requestMatchers(HttpMethod.POST, "/api/v1/properties").hasAuthority(Roles.OWNER.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/properties/**").hasAuthority(Roles.OWNER.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/properties/**").hasAuthority(Roles.OWNER.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/properties/owner/**").hasAuthority(Roles.OWNER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/properties//owner/{ownerId}/active-offers/properties").hasAuthority(Roles.OWNER.name())



               .requestMatchers(HttpMethod.GET, "/api/v1/customer/filter-history/*").hasAuthority(Roles.CUSTOMER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/**").hasAuthority(Roles.CUSTOMER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/offers/**").hasAuthority(Roles.CUSTOMER.name())

                .requestMatchers(HttpMethod.POST, "/api/v1/customer/offers/**").hasAuthority(Roles.CUSTOMER.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/customer/*").hasAuthority(Roles.CUSTOMER.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/customer/**").hasAuthority(Roles.CUSTOMER.name())


                .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasAuthority(Roles.CUSTOMER.name())


                .requestMatchers("/api/v1/admin/**").hasAuthority(Roles.ADMIN.name())
                 .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasAuthority(Roles.ADMIN.name())

                .requestMatchers(HttpMethod.GET, "/api/v1/admin/user/*").hasAnyAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/admin").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/admin/*").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/admin/**").hasAuthority(Roles.ADMIN.name())


                .requestMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasAuthority(Roles.ADMIN.name())


                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsSvc());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
