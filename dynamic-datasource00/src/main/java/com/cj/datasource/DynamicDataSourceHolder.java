package com.cj.datasource;

public class DynamicDataSourceHolder {
   private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getDataSourceName(){
        return threadLocal.get();
    }

    public static void setDataSourceName(String dataSourceName){
         threadLocal.set(dataSourceName);
    }

    public static void removeDataSourceName(){
        threadLocal.remove();
    }
}
