package org.example.commons.utils;

import org.springframework.beans.factory.annotation.Value;
import java.util.concurrent.atomic.AtomicLong;

/**
 * SnowflakeIdWorker : 自定义 雪花ID 生成算法
 *
 */

public class SnowflakeIdUtil {

    // 起始的时间戳 (2010-01-01)
    private final long twepoch = 1288834974657L;

    // 机器标识位数
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;

    // 序列号位数
    private final long sequenceBits = 12L;

    // 工作机器 ID 最大值
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心 ID 最大值
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    // 每一部分向左的偏移量
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    // 时间戳边界值
    private long lastTimestamp = -1L;

    // 工作节点ID(0~31)
    @Value("${java.workerId}")
    private long workerId;

    // 数据中心ID(0~31)
    @Value("${java.datacenterId}")
    private long datacenterId;
    // 每个节点每毫秒内的序列号
    private AtomicLong sequence = new AtomicLong(0L);

    /**
     * 通过专属工作节点ID和数据中心ID构建专属的雪花算法工具类
     */
    public SnowflakeIdUtil() {
        if (this.workerId > maxWorkerId || this.workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (this.datacenterId > maxDatacenterId || this.datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
    }

    /**
     * 分布式唯一ID生成
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行序列号的自增
        if (lastTimestamp == timestamp) {
            sequence.incrementAndGet();
            // 判断是否溢出
            if (sequence.get() > (-1L ^ (-1L << sequenceBits))) {
                // 阻塞到下一个时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，重置序列号
            sequence.set(0L);
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) | sequence.get();
    }

    /**
     * 从给定的最后时间戳中获取下一个时间戳
     *
     * @param lastTimestamp 最后时间戳
     * @return 下一个时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 生成当前时间的毫秒数。
     *
     * @return 当前时间的毫秒数。
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
