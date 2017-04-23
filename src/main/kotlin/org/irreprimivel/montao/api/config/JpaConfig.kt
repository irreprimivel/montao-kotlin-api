package org.irreprimivel.montao.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@PropertySource(value = *arrayOf("classpath:hibernate.properties"))
class JpaConfig(val environment: Environment) {
    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactory = LocalContainerEntityManagerFactoryBean()
        entityManagerFactory.dataSource = dataSource()
        entityManagerFactory.jpaVendorAdapter = jpaVendorAdapter()
        entityManagerFactory.setPackagesToScan("org.irreprimivel.montao.api")
        entityManagerFactory.setJpaProperties(hibernateProperties())
        return entityManagerFactory
    }

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"))
        dataSource.url = environment.getRequiredProperty("jdbc.url")
        dataSource.username = environment.getRequiredProperty("jdbc.username")
        dataSource.password = environment.getRequiredProperty("jdbc.password")
        return dataSource
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory
        return transactionManager
    }

    @Bean
    fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
        return PersistenceExceptionTranslationPostProcessor()
    }

    @Bean
    fun jpaVendorAdapter(): JpaVendorAdapter {
        return HibernateJpaVendorAdapter()
    }

    /**
     * Takes hibernate properties from properties file that sets by PropertySource annotation.

     * @return Properties.
     */
    private fun hibernateProperties(): Properties {
        val properties = Properties()
        with(properties) {
            put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"))
            put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"))
            put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"))
            put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"))
        }
        return properties
    }
}