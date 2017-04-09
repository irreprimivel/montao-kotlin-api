package org.irreprimivel.montao.api.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Configuration
@ComponentScan(
        basePackages = arrayOf("org.irreprimivel.montao.api"),
        includeFilters = arrayOf(ComponentScan.Filter(*arrayOf(Service::class, Repository::class))))
class ApplicationContext

