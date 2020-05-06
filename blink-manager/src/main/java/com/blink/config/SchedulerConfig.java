package com.blink.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
public class SchedulerConfig {


    @Autowired
    Environment env;

    public DataSourceProperties schedulerDataSourceProperties()
    {
        DataSourceProperties dsp = new DataSourceProperties();
        dsp.setUrl(env.getProperty("scheduler.datasource.url", String.class));
        dsp.setPassword(env.getProperty("scheduler.datasource.password", String.class));
        dsp.setUsername(env.getProperty("scheduler.datasource.username", String.class));
        dsp.setDriverClassName(env.getProperty("scheduler.datasource.driverClassName", String.class));
        dsp.setPlatform(env.getProperty("scheduler.datasource.platform", String.class));

        return dsp;
    }

    public HikariDataSource schedulerDataSource()
    {
        HikariDataSource ds =  schedulerDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        ds.setMinimumIdle(env.getProperty("scheduler.datasource.hikari.minimum-idle", Integer.class, 1));
        ds.setMaximumPoolSize(env.getProperty("scheduler.datasource.hikari.maximum-pool-size", Integer.class, 1));
        ds.setConnectionTimeout(env.getProperty("scheduler.datasource.hikari.connection-timeout", Integer.class, 5000));
        ds.setValidationTimeout(env.getProperty("scheduler.datasource.hikari.validation-timeout", Integer.class, 1000));
        ds.setMaxLifetime(env.getProperty("scheduler.datasource.hikari.max-lifetime", Integer.class, 6000));
        return ds;
    }

    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer()
    {
        return bean ->
        {
            bean.setDataSource(schedulerDataSource());
            bean.setTransactionManager(schedulerTransactionManager());
        };
    }


    public PlatformTransactionManager schedulerTransactionManager()
    {
        final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(schedulerDataSource());

        return transactionManager;
    }
}
