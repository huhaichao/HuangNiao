package com.sy.huangniao.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * 数据源配置类
 *
 **/
@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement(proxyTargetClass = true)
public class DataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "com.sy.huangniao.service";
    static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/*.xml";

    @Value("${com.sy.huangniao.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @ConfigurationProperties(prefix = "com.sy.huangniao.datasource")
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .type(dataSourceType)
            .build();
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") final DataSource dataSource)
        throws Exception {
        return this.createSqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
        throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("transactionManager") final
                                                   DataSourceTransactionManager transactionManager)
        throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }
}
