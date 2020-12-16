package com.fudan.pm.security;

import com.fudan.pm.security.jwt.JwtRequestFilter;
import com.fudan.pm.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtUserDetailsService userDetailsService;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // Make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/my_info").permitAll()
                .antMatchers("/changePassword").permitAll()
                .antMatchers("/my_project").hasRole("user")
                .antMatchers("/searchActivity").hasRole("user")
                .antMatchers("/activityList").hasRole("user")
                .antMatchers("/enrolledActivity").hasRole("user")
                .antMatchers("/activityDetails").hasRole("user")
                .antMatchers("/activityEnrollment").hasRole("user")
                .antMatchers("/retreatEnrollment").hasRole("user")
                .antMatchers("/activityCheckIn").hasRole("user")
                .antMatchers("/createActivity").hasRole("admin")
                .antMatchers("/deleteActivity").hasRole("admin")
                .antMatchers("/editActivity").hasRole("admin")
                .antMatchers("/launchActivity").hasRole("admin")
                .antMatchers("/hostActivityList").hasRole("admin")
                .antMatchers("/hostActivityDetails").hasRole("admin")
                .antMatchers("/getVenueList").hasRole("admin")
                .antMatchers("/writeReview").hasRole("user")

                .anyRequest().authenticated();

//      Here we use JWT(Json Web Token) to authenticate the user.
//      You need to write your code in the class 'JwtRequestFilter' to make it works.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Hint: Now you can view h2-console page at `http://IP-Address:<port>/h2-console` without authentication.
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
