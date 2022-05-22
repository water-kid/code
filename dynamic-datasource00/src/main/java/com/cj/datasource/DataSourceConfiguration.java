package com.cj.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ds.master")
    public DataSource dataSource1(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ds.slave")
    public DataSource dataSource2(){
        return DruidDataSourceBuilder.create().build();
    }
    @Bean
    DynamicDataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("master",dataSource1());
        map.put("slave",dataSource2());

        dynamicDataSource.setTargetDataSources(map);
        dynamicDataSource.setDefaultTargetDataSource(map.get("master"));
        return dynamicDataSource;
    }

    /**
     * 为什么要注入SqlSessionFactory：  我觉得是因为注入了多个数据源bean， 他不知道用哪个，要设置一下
     * @return
     */
    @Bean
    SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource());
        return factoryBean.getObject();
    }

}
