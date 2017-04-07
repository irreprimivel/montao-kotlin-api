package org.unstoppable.montao.backend.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = arrayOf("org.unstoppable.montao"),
        includeFilters = arrayOf(ComponentScan.Filter(RestController::class)))
class WebConfig : WebMvcConfigurerAdapter()