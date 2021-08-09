
package com.tdt.config.datasource;

import cn.stylefeng.roses.core.config.properties.DruidProperties;
import cn.stylefeng.roses.core.mutidatasource.aop.MultiSourceExAop;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import java.util.Properties;

import static com.tdt.base.db.context.DataSourceContext.MASTER_DATASOURCE_NAME;

/**
 * 多数据源配置<br/>
 * <p>
 * 注：由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
 *
 * @author gcj
 * @Date 2017/5/20 21:58
 */
@Configuration
public class DataSourceConfig {

    /**
     * 默认主数据源配置
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidProperties druidProperties() {
        return new DruidProperties();
    }

    /**
     * 主数据源实例
     */
    @Primary
    @Bean
    public DataSource dataSourcePrimary(@Qualifier("druidProperties") DruidProperties druidProperties) {

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        atomikosDataSourceBean.setUniqueResourceName(MASTER_DATASOURCE_NAME);
        atomikosDataSourceBean.setMaxPoolSize(100);
        atomikosDataSourceBean.setBorrowConnectionTimeout(60);

        Properties properties = druidProperties.createProperties();

        //解决oracle数据库包connection holder is null
        if (druidProperties.getUrl().contains("oracle")) {
            properties.setProperty("removeAbandoned", "true");
            properties.setProperty("removeAbandonedTimeoutMillis", "10000");
            properties.setProperty("poolPreparedStatements", "false");
        }

        atomikosDataSourceBean.setXaProperties(properties);

        return atomikosDataSourceBean;
    }

    /**
     * 多数据源切换的aop
     */
    @Bean
    public MultiSourceExAop multiSourceExAop() {
        return new MultiSourceExAop();
    }

}