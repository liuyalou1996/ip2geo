package com.universe.ip2geo.pojo.propreties.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author 刘亚楼
 * @date 2022/7/31
 */
@Data
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {

	/**
	 * 核心线程数
	 */
	private int corePoolSize;

	/**
	 * 最大线程数
	 */
	private int maxPoolSize;

	/**
	 * 队列容量
	 */
	private int queueCapacity;

	/**
	 * 空闲线程存活时长
	 */
	private Duration keepAliveSeconds;

	/**
	 * 线程名前缀
	 */
	private String threadNamePrefix;

	/**
	 * 销毁时是否等待任务执行完
	 */
	private boolean waitForTasksToCompleteOnShutdown;
}
