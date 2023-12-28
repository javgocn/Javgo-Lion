package cn.javgo.lion.common.core.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 唯一ID生成工具类<br>
 * 实现原理：<br>
 * 1. 基于 Twitter 的 Snowflake 算法，生成 64 位的长整型唯一ID。<br>
 * 2. ID结构：1位符号位 + 41位时间戳 + 10位机器标识（5位datacenterId + 5位workerId）+ 12位序列号。
 * 3. 支持每个节点每毫秒生成多个ID，序列号部分保证同一毫秒内的多个ID的唯一性。
 * 4. 使用 SystemClock 防止系统时钟回拨。
 *
 * @author javgo.cn
 * @date 2023/12/27
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IdGenerateUtil {

    /**
     * 主机和进程的机器码
     */
    private static final Sequence worker = new Sequence();

    /**
     * 生成全局唯一 ID
     */
    public static long getId() {
        return worker.nextId();
    }

    /**
     * 生成去掉 "-" 的 UUID 字符串
     */
    public static synchronized String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    static class Sequence {
        private final long TWEPOCH = 1288834974657L;
        private final long WORKER_ID_BITS = 5L;
        private final long DATACENTER_ID_BITS = 5L;
        private final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
        private final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
        private final long SEQUENCE_BITS = 12L;
        private final long WORKER_ID_SHIFT = SEQUENCE_BITS;
        private final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
        private final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
        private final long SEQ_MASK = ~(-1L << SEQUENCE_BITS);

        private long workerId;
        private long datacenterId;
        private long sequence = 0L;
        private long lastTimestamp = -1L;

        /**
         * 构造方法，自动配置 workerId 和 datacenterId
         */
        public Sequence() {
            this.datacenterId = getDatacenterId(MAX_DATACENTER_ID);
            this.workerId = getMaxWorkerId(datacenterId, MAX_WORKER_ID);
        }

        /**
         * 构造方法，手动指定 workerId 和 datacenterId
         */
        public Sequence(long workerId, long datacenterId) {
            if (workerId > MAX_WORKER_ID || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id不能大于%d或小于0", MAX_WORKER_ID));
            }
            if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id不能大于%d或小于0", MAX_DATACENTER_ID));
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        /**
         * 根据数据中心ID获取最大的工作机器ID
         */
        protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
            StringBuilder mpid = new StringBuilder();
            mpid.append(datacenterId);
            String name = ManagementFactory.getRuntimeMXBean().getName();
            if (!name.isEmpty()) {
                mpid.append(name.split("@")[0]);
            }
            return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
        }

        /**
         * 获取数据中心ID
         */
        protected static long getDatacenterId(long maxDatacenterId) {
            long id = 0L;
            try {
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network != null) {
                    byte[] mac = network.getHardwareAddress();
                    if (mac != null) {
                        id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                        id = id % (maxDatacenterId + 1);
                    }
                }
            } catch (Exception e) {
                log.error("获取数据中心ID异常: " + e.getMessage());
            }
            return id;
        }

        /**
         * 生成下一个ID
         */
        public synchronized long nextId() {
            long timestamp = timeGen();
            if (timestamp < lastTimestamp) {
                long offset = lastTimestamp - timestamp;
                if (offset <= 5) {
                    try {
                        wait(offset << 1);
                        timestamp = timeGen();
                        if (timestamp < lastTimestamp) {
                            throw new RuntimeException(String.format("时钟回拨。拒绝生成id %d 毫秒", offset));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("等待时钟同步时发生异常", e);
                    }
                } else {
                    throw new RuntimeException(String.format("时钟回拨。拒绝生成id %d 毫秒", offset));
                }
            }

            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & SEQ_MASK;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = ThreadLocalRandom.current().nextLong(1, 3);
            }

            lastTimestamp = timestamp;

            // 组装ID的各个部分，生成最终的ID
            return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
                    | (datacenterId << DATACENTER_ID_SHIFT)
                    | (workerId << WORKER_ID_SHIFT)
                    | sequence;
        }

        /**
         * 等待直到下一个毫秒
         */
        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        /**
         * 获取当前时间戳
         */
        protected long timeGen() {
            return SystemClock.now();
        }
    }

    static class SystemClock {

        private final long period;
        private final AtomicLong now;

        /**
         * 构造方法，设置时间更新周期
         */
        private SystemClock(long period) {
            this.period = period;
            this.now = new AtomicLong(System.currentTimeMillis());
            scheduleClockUpdating();
        }

        /**
         * 获取SystemClock实例
         */
        private static SystemClock instance() {
            return InstanceHolder.INSTANCE;
        }

        /**
         * 获取当前时间
         */
        public static long now() {
            return instance().currentTimeMillis();
        }

        /**
         * 获取当前日期时间
         */
        public static String nowDate() {
            return new Timestamp(instance().currentTimeMillis()).toString();
        }

        /**
         * 定时更新当前时间
         */
        private void scheduleClockUpdating() {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
                Thread thread = new Thread(runnable, "System Clock");
                thread.setDaemon(true);
                return thread;
            });
            scheduler.scheduleAtFixedRate(() -> now.set(System.currentTimeMillis()), period, period, TimeUnit.MILLISECONDS);
        }

        /**
         * 获取当前时间戳
         */
        private long currentTimeMillis() {
            return now.get();
        }

        /**
         * 保证单例
         */
        private static class InstanceHolder {
            public static final SystemClock INSTANCE = new SystemClock(1);
        }
    }
}
