package com.cj.enums;

public enum LimitType {
    /**
     * 默认限流策略 ， 针对某个接口进行限流，，某个接口在一定时间只能访问多少次
     */
    DEFAULT,
    /**
     * 根据IP限流，，某个IP在一定时间只能访问多少次
     */
    IP
}
