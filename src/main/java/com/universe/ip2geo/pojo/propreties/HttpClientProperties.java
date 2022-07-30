package com.universe.ip2geo.pojo.propreties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author 刘亚楼
 * @date 2022/5/10
 */
@Data
@ConfigurationProperties(prefix = "http.client")
public class HttpClientProperties {

	/**
	 * 连接最大空闲时间
	 */
	private Duration maxIdleTime = Duration.ofSeconds(5);

	/**
	 * 与服务端建立连接超时时间
	 */
	private Duration connectionTimeout = Duration.ofSeconds(5);

	/**
	 * 客户端从服务器读取数据超时时间
	 */
	private Duration socketTimeout = Duration.ofSeconds(10);

	/**
	 * 从连接池获取连接超时时间
	 */
	private Duration connectionRequestTimeout = Duration.ofSeconds(3);

	/**
	 * 连接池最大连接数
	 */
	private int maxTotal = 500;

	/**
	 * 每个路由(即ip+端口)最大连接数
	 */
	private int defaultMaxPerRoute = 50;

}
