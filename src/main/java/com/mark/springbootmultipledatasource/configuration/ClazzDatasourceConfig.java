package com.mark.springbootmultipledatasource.configuration;

import com.mark.springbootmultipledatasource.clazz.entity.Clazz;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.mark.springbootmultipledatasource.clazz.repository",
        entityManagerFactoryRef = "clazzEntityManagerFactory",
        transactionManagerRef = "clazzTransactionManager"
)
public class ClazzDatasourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.clazz")
    public DataSourceProperties clazzDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.clazz.configuration")
    public DataSource clazzDataSource() {
        return clazzDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "clazzEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean clazzEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(clazzDataSource())
                .packages(Clazz.class)
                .build();
    }

    @Bean(name = "clazzTransactionManager")
    @Primary
    public PlatformTransactionManager clazzTransactionManager(final @Qualifier("clazzEntityManagerFactory") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }
}
