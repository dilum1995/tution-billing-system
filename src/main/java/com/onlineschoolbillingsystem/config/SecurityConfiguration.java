package com.onlineschoolbillingsystem.config;

import com.onlineschoolbillingsystem.filter.JwtFilter;
import com.onlineschoolbillingsystem.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/***
 * Class that contains all the security
 * configurations.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Institute related service.
    private InstituteService instituteService;
    // Filter to validate token
    private JwtFilter jwtFilter;

    @Autowired
    public SecurityConfiguration(InstituteService instituteService, JwtFilter jwtFilter) {
        this.instituteService = instituteService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(instituteService);
    }

    //To get rid of password encoder issues.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * For /authenticate endpoint permit all and if
         * there are any other request authenticate them.
         */
        http.csrf().disable().authorizeRequests()
                .antMatchers("/authenticate", "/institutes")
                .permitAll().anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                //Session creation policy.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //Registering the filter.
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
