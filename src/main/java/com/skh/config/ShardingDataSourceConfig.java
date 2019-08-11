package com.skh.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Preconditions;
import com.skh.util.EncryptUtil;
import io.shardingsphere.core.api.MasterSlaveDataSourceFactory;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.exception.ShardingException;
import io.shardingsphere.core.util.DataSourceUtil;
import io.shardingsphere.jdbc.spring.boot.masterslave.SpringBootMasterSlaveRuleConfigurationProperties;
import io.shardingsphere.jdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import io.shardingsphere.jdbc.spring.boot.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description
 * @auther SHENKAIHUAN
 * @create 2019-08-11 15:56
 */
@Configuration
@EnableConfigurationProperties({SpringBootShardingRuleConfigurationProperties.class, SpringBootMasterSlaveRuleConfigurationProperties.class})
public class ShardingDataSourceConfig implements EnvironmentAware {

    private final static Logger log = LoggerFactory.getLogger (ShardingDataSourceConfig.class);

    @Autowired
    private SpringBootShardingRuleConfigurationProperties shardingProperties;

    @Autowired
    private SpringBootMasterSlaveRuleConfigurationProperties masterSlaveProperties;

    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<> ();

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.filters}")
    private String filters;

    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;

    @Value ("${db.pwd.encrypt}")
    private boolean encrypt;

    /**
     * Get data source bean.
     *
     * @return data source bean
     * @throws SQLException SQL exception
     */
    @Bean
    public DataSource dataSource() throws SQLException {
        return null == masterSlaveProperties.getMasterDataSourceName()
                ? ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingProperties.getShardingRuleConfiguration(), shardingProperties.getConfigMap(), shardingProperties.getProps())
                : MasterSlaveDataSourceFactory.createDataSource(
                dataSourceMap, masterSlaveProperties.getMasterSlaveRuleConfiguration(), masterSlaveProperties.getConfigMap(), masterSlaveProperties.getProps());
    }

    @Override
    public final void setEnvironment(final Environment environment) {
        setDataSourceMap(environment);
    }

    @SuppressWarnings("unchecked")
    private void setDataSourceMap(final Environment environment) {
        String prefix = "sharding.jdbc.datasource.";
        String dataSources = environment.getProperty(prefix + "names");
        for (String each : dataSources.split(",")) {
            try {
                Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + each, Map.class);
                Preconditions.checkState(!dataSourceProps.isEmpty(), "Wrong datasource properties!");
                DruidDataSource dataSource = (DruidDataSource) DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);

                if(encrypt){
                    dataSource.setPassword (EncryptUtil.getInstance ().Base64Decode (dataSource.getPassword ()));
                }
                dataSource.setInitialSize (initialSize);
                dataSource.setMaxActive (maxActive);
                dataSourceMap.put(each, dataSource);
            } catch (final ReflectiveOperationException ex) {
                throw new ShardingException ("Can't find datasource type!", ex);
            }
        }
    }
}
