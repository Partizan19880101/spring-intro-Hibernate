package com.epam.training.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Properties;
import java.util.ResourceBundle;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.epam.training.repository"}
)
public class DbConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.destroy();
        em.setDataSource(getDataSource());
        em.setPackagesToScan("com.epam.training.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        return properties;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        ResourceBundle rb = ResourceBundle.getBundle("connection");
        config.setDriverClassName(rb.getString("spring.datasource.driver-class-name"));
        config.setJdbcUrl(rb.getString("app.datasource.url"));
        config.setUsername(rb.getString("app.datasource.username"));
        config.setPassword(rb.getString("app.datasource.password"));
        config.addDataSourceProperty("spring.jpa.generate-ddl", true);
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }
}
