package org.unstoppable.montao.backend.config

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class WebInit : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getRootConfigClasses(): Array<Class<*>> = arrayOf(
            ApplicationContext::class.java,
            JpaConfig::class.java)

    override fun getServletMappings(): Array<String> = arrayOf("/")

    override fun getServletConfigClasses(): Array<Class<*>> = arrayOf(WebConfig::class.java)
}


