package com.cj.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {
    private String type;
    private String driverClassName;
    private Map<String, Map<String, String>> ds;

    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;

    /**
     * 设置数据源的共有属性
     * @param dataSource  传入的datasource只包含核心属性，url,username,password
     * @return
     */
    public DruidDataSource getDataSource(DruidDataSource dataSource) {
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        return dataSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Map<String, Map<String, String>> getDs() {
        return ds;
    }

    public void setDs(Map<String, Map<String, String>> ds) {
        this.ds = ds;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }
}
