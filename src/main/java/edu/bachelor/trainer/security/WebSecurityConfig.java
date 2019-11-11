//package edu.bachelor.trainer.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests().antMatchers("/test1").authenticated()
//                .antMatchers("/test2").hasRole("ADMIN")
//                .anyRequest().authenticated();
//
//        http.addFilter(new JwtAuthorizationFilter(authenticationManager()));
//    }
//}
