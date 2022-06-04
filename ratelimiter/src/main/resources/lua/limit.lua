local key = KEYS[1]
local time = tonumber(ARGV[1])
-- 限流次数
local count = tonumber(ARGV[2])

-- 获取redis中的key ，，， 如果接口没有调用过，，有可能没有值，
local current = redis.call('get',key)

-- current超过限流了
if current and tonumber(current)>count then
    return tonumber(current)
end

-- 第一次访问  ,,自增1  。。 并发的时候，另外一个线程可能也执行了自增1
current =  redis.call("incr",key)

-- 没有其他线程
if tonumber(current)==1 then
    redis.call("expire",key,time)
end

-- 不等于1，，其他线程已经设置过期时间
return tonumber(current)


-- 返回current，，在java中判断是限流还是放行
