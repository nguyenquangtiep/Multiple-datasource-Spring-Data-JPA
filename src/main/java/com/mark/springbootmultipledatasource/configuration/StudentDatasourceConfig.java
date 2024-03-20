package com.mark.springbootmultipledatasource.configuration;

import com.mark.springbootmultipledatasource.student.entity.Student;
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
        basePackages = "com.mark.springbootmultipledatasource.student.repository",
        entityManagerFactoryRef = "studentEntityManagerFactory",
        transactionManagerRef = "studentTransactionManager"
)
public class StudentDatasourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.student")
    public DataSourceProperties studentDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.student.configuration")
    public DataSource studentDataSource() {
        return studentDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "studentEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean studentEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(studentDataSource())
                .packages(Student.class)
                .build();
    }

    @Bean(name = "studentTransactionManager")
    @Primary
    public PlatformTransactionManager studentTransactionManager(final @Qualifier("studentEntityManagerFactory") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }
}
