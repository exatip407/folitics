package com.ohmuk.folitics;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Value("${db.driver}")
    private String DB_DRIVER;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Value("${hibernate.c3p0.acquireIncrement}")
    private String HIBERNATE_C3P0_ACQUIREINCREMENT;

    @Value("${hibernate.c3p0.validate}")
    private String HIBERNATE_C3P0_VALIDATE;

    @Value("${hibernate.c3p0.checkoutTimeout}")
    private String HIBERNATE_C3P0_CHECKOUTTIMEOUT;

    @Value("${hibernate.c3p0.contextClassLoaderSource}")
    private String HIBERNATE_C3P0_CONTEXTCLASSLOADERSOURCE;

    @Value("${hibernate.c3p0.min_size}")
    private String HIBERNATE_C3P0_MIN_SIZE;

    @Value("${hibernate.c3p0.max_size}")
    private String HIBERNATE_C3P0_MAX_SIZE;

    @Value("${hibernate.c3p0.timeout}")
    private String HIBERNATE_C3P0_TIMEOUT;

    @Value("${hibernate.c3p0.max_statements}")
    private String HIBERNATE_C3P0_MAX_STATEMENTS;

    @Value("${hibernate.c3p0.idle_test_period}")
    private String HIBERNATE_C3P0_IDLE_TEST_PERIOD;

    @Value("${hibernate.c3p0.unreturnedConnectionTimeout}")
    private String HIBERNATE_C3P0_UNRETURNEDCONNECTIONTIMEOUT;

    @Value("${hibernate.c3p0.preferredTestQuery}")
    private String HIBERNATE_C3P0_PREFERREDTESTQUERY;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${hibernate.cache.use_second_level_cache}")
    private String HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;

    @Value("${hibernate.cache.use_query_cache}")
    private String HIBERNATE_CACHE_USE_QUERY_CACHE;

    @Value("${cache.provider_class}")
    private String CACHE_PROVIDER_CLASS;

    @Value("${hibernate.order_inserts}")
    private String HIBERNATE_ORDER_INSERTS;

    @Value("${hibernate.jdbc.batch_size}")
    private String HIBERNATE_JDBC_BATCH_SIZE;

    @Value("${hibernate.order_updates}")
    private String HIBERNATE_ORDER_UPDATES;

    @Value("${hibernate.cache.auto_evict_collection_cache}")
    private String HIBERNATE_CACHE_AUTO_EVICT_COLLECTION_CACHE;

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        hibernateProperties.put("hibernate.c3p0.acquireIncrement", HIBERNATE_C3P0_ACQUIREINCREMENT);
        hibernateProperties.put("hibernate.c3p0.validate", HIBERNATE_C3P0_VALIDATE);
        hibernateProperties.put("hibernate.c3p0.checkoutTimeout", HIBERNATE_C3P0_CHECKOUTTIMEOUT);
        hibernateProperties.put("hibernate.c3p0.contextClassLoaderSource", HIBERNATE_C3P0_CONTEXTCLASSLOADERSOURCE);
        hibernateProperties.put("hibernate.c3p0.min_size", HIBERNATE_C3P0_MIN_SIZE);
        hibernateProperties.put("hibernate.c3p0.max_size", HIBERNATE_C3P0_MAX_SIZE);
        hibernateProperties.put("hibernate.c3p0.timeout", HIBERNATE_C3P0_TIMEOUT);
        hibernateProperties.put("hibernate.c3p0.max_statements", HIBERNATE_C3P0_MAX_STATEMENTS);
        hibernateProperties.put("hibernate.c3p0.idle_test_period", HIBERNATE_C3P0_IDLE_TEST_PERIOD);
        hibernateProperties.put("hibernate.c3p0.unreturnedConnectionTimeout",
                HIBERNATE_C3P0_UNRETURNEDCONNECTIONTIMEOUT);
        hibernateProperties.put("hibernate.c3p0.preferredTestQuery", HIBERNATE_C3P0_PREFERREDTESTQUERY);
        hibernateProperties.put("hibernate.cache.use_second_level_cache", HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE);
        hibernateProperties.put("hibernate.cache.use_query_cache", HIBERNATE_CACHE_USE_QUERY_CACHE);
        hibernateProperties.put("cache.provider_class", CACHE_PROVIDER_CLASS);
        hibernateProperties.put("hibernate.order_inserts", HIBERNATE_ORDER_INSERTS);
        hibernateProperties.put("hibernate.jdbc.batch_size", HIBERNATE_JDBC_BATCH_SIZE);
        hibernateProperties.put("hibernate.order_updates", HIBERNATE_ORDER_UPDATES);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
