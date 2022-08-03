package com.universe.ip2geo.config;

import com.universe.ip2geo.pojo.propreties.common.ThreadPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/**
 * @author 刘亚楼
 * @date 2022/7/31
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfig {

	@Autowired
	private ThreadPoolProperties threadPoolProperties;

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
		executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
		executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
		executor.setKeepAliveSeconds((int) threadPoolProperties.getKeepAliveSeconds().getSeconds());
		executor.setWaitForTasksToCompleteOnShutdown(threadPoolProperties.isWaitForTasksToCompleteOnShutdown());
		executor.setRejectedExecutionHandler(new CallerRunsPolicy());
		executor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());
		return executor;
	}

}
