package org.itstep.usersms.security;

import org.itstep.usersms.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests().
                antMatchers("/api/v1/login", "api/v1/register")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/api/v1/admin/*").hasRole("ADMIN");
        http.authorizeRequests()
                .antMatchers("/api/v1/admin/users/*").hasRole("ADMIN");

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
