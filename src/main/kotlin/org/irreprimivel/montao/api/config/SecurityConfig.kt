package org.irreprimivel.montao.api.config

import org.irreprimivel.montao.api.security.JWTAuthenticationFilter
import org.irreprimivel.montao.api.security.JWTLoginFilter
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig(val dataSource: DataSource) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.jdbcAuthentication()
                ?.dataSource(dataSource)
                ?.usersByUsernameQuery("SELECT username, password, true FROM users WHERE username=?")
                ?.authoritiesByUsernameQuery("SELECT username, role FROM users WHERE username=?")
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(JWTLoginFilter("/auth/login", authenticationManager()),
                                 UsernamePasswordAuthenticationFilter::class.java)
                .addFilterAfter(JWTAuthenticationFilter(),
                                UsernamePasswordAuthenticationFilter::class.java)
    }
}