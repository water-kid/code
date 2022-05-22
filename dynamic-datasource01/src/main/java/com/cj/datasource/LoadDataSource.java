package com.cj.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Component
@EnableConfigurationProperties(DruidProperties.class)
public class LoadDataSource {

    @Autowired
    DruidProperties druidProperties;

    public Map<Object, Object> getAllDataSource() {
        Map<String, Map<String, String>> ds = druidProperties.getDs();
        // 要传入AbstractRoutingDataSource的map
        HashMap<Object, Object> map = new HashMap<>();

        try {
            Set<String> keySet = ds.keySet();
            for (String key : keySet) {
    //            DruidDataSourceBuilder.create().build()
                // 创建 druidDataSource 核心属性
                DruidDataSource dataSource = ((DruidDataSource) DruidDataSourceFactory.createDataSource(ds.get(key)));
                // 设置 druidDataSource 其他属性
                map.put(key, druidProperties.getDataSource(dataSource));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  map;
    }
}
