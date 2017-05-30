package org.irreprimivel.montao.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig(val dataSource: DataSource): WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.jdbcAuthentication()
                ?.dataSource(dataSource)
                ?.usersByUsernameQuery("SELECT username, password, true FROM users WHERE username=?")
                ?.authoritiesByUsernameQuery("SELECT username, role FROM users WHERE username=?")
    }

    override fun configure(http: HttpSecurity?) {
        super.configure(http)
    }
}