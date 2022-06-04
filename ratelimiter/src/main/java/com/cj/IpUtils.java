package com.cj;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 获取用户访问ip地址
 */
public class IpUtils {
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }


        // 获取到多个ip时取第一个作为客户端真实ip
        if(ip !=null && ip.length() != 0 && ip.contains(",")){
            String[] ipArray = ip.split(",");
            ip = ipArray[0];
        }

        return ip;
    }

    public static void main(String[] args) {
        String s= "abc";
        String[] split = s.split(",");
        System.out.println("split.length = " + split.length);
        System.out.println(Arrays.toString(split));
    }
}
