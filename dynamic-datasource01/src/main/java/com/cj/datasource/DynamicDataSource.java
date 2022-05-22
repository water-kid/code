package com.cj.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(LoadDataSource loadDataSource) {
        // 设置所有的数据源
        Map<Object, Object> allDataSource = loadDataSource.getAllDataSource();
        super.setTargetDataSources(allDataSource);
        // 设置默认的    那些没有@DataSource的也需要数据源
        super.setDefaultTargetDataSource(allDataSource.get(DataSourceType.DEFAULT_DATASOURCE_TYPE));

        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSourceName();
    }
}
