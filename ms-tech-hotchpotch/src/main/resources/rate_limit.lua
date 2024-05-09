-- 传入的要操作的key:tokenKey
local tokenKey = KEYS[1]
-- 传入的要操作的key:timestampKey
local timestampKey = KEYS[2]

-- 参数1：令牌桶的大小
local capacity = tonumber(ARGV[1])
-- 参数2：生成令牌的速度
local rate = tonumber(ARGV[2])
-- 参数3：当前时间的秒数
local nowTimestamp = tonumber(ARGV[3])
-- 参数4：请求令牌数
local requested = tonumber(ARGV[4])

-- redis.log(redis.LOG_NOTICE,"tokenKey:" .. tokenKey)
-- redis.log(redis.LOG_NOTICE,"timestampKey:" .. timestampKey)
-- redis.log(redis.LOG_NOTICE,"capacity:" .. capacity)
-- redis.log(redis.LOG_NOTICE,"rate:" .. rate)
-- redis.log(redis.LOG_NOTICE,"nowTimestamp:" .. nowTimestamp)
-- redis.log(redis.LOG_NOTICE,"requested:" .. requested)

-- 计算令牌桶填充时间，令牌桶的大小/生成令牌的速度
local fillTime = capacity / rate
-- 失效时间向下取整，采用两倍填充时间保证失效时间充足
local expireTime = math.floor(fillTime * 2)

-- 从redis获取上一次tokenKey的值，如果返回nil，则初始化令牌桶，结果转为数字
local lastToken = tonumber(redis.call("get", tokenKey) or capacity)
-- 从redis获取上一次timestampKey的值，如果返回nil，则时间设置为0，结果转为数字
local lastTimestamp = tonumber(redis.call("get", timestampKey) or 0)
-- 当前时间和最后一次获取的时间的差值：秒数取值范围是从0到expireTime 或者 当前时间值
local timeGaps = math.max(0, nowTimestamp - lastTimestamp)

-- redis.log(redis.LOG_NOTICE,"fillTime:" .. fillTime)
-- redis.log(redis.LOG_NOTICE,"expireTime:" .. expireTime)
-- redis.log(redis.LOG_NOTICE,"lastToken:" .. lastToken)
-- redis.log(redis.LOG_NOTICE,"lastTimestamp:" .. lastTimestamp)
-- redis.log(redis.LOG_NOTICE,"timeGaps:" .. timeGaps)

-- 同1秒内的timeGaps的值都是0，令牌桶的数不会增加，直到扣减完，超过1秒的都会填充令牌
local filledToken = math.min(capacity, lastToken + (timeGaps * rate))
-- 新拿到的令牌值默认是填充后的filledToken
local newToken = filledToken
-- 令牌数大于等于请求令牌数说明可以获取到令牌
local allowed = filledToken >= requested

-- 如果可以拿到令牌，则令牌数扣减掉请求数，得到令牌值
if allowed
then
    newToken = filledToken - requested
end

-- redis.log(redis.LOG_NOTICE,"filledToken:" .. filledToken)
-- redis.log(redis.LOG_NOTICE,"allowed:" .. tostring(allowed))
-- redis.log(redis.LOG_NOTICE,"newToken:" .. newToken)

-- 通过redis设置tokenKey的值为newToken，失效时间为expireTime
redis.call("setex", tokenKey, expireTime, newToken)
-- 通过redis设置timestampKey的值为nowTimestamp，失效时间为expireTime
redis.call("setex", timestampKey, expireTime, nowTimestamp)

-- 返回结果：是否拿到令牌
return allowed
