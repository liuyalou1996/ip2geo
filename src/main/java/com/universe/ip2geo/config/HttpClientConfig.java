package com.universe.ip2geo.config;

import com.universe.ip2geo.manager.HttpClientManager;
import com.universe.ip2geo.pojo.propreties.HttpClientProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author 刘亚楼
 * @date 2022/5/10
 */
@Configuration
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientConfig {

	@Bean
	public HttpClientManager httpClientManager(HttpClientProperties httpClientProperties) {
		long maxIdleTime = httpClientProperties.getMaxIdleTime().toMillis();
		long connectionTimeout = httpClientProperties.getConnectionTimeout().toMillis();
		long socketTimeout = httpClientProperties.getSocketTimeout().toMillis();
		long connectionRequestTimeout = httpClientProperties.getConnectionRequestTimeout().toMillis();

		// 连接最大空闲时间为5秒，超出5秒后即释放
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(maxIdleTime, TimeUnit.MILLISECONDS);
		// 最大总连接数(多个路由连接数之和)，默认为20
		connectionManager.setMaxTotal(httpClientProperties.getMaxTotal());
		// 每个路由(即ip+端口)最大连接数，默认为2
		connectionManager.setDefaultMaxPerRoute(httpClientProperties.getDefaultMaxPerRoute());

		RequestConfig requestConfig = RequestConfig.custom()
			.setConnectTimeout((int) connectionTimeout) // 与服务端建立连接超时时间
			.setSocketTimeout((int) socketTimeout) // 客户端从服务器读取数据超时时间
			.setConnectionRequestTimeout((int) connectionRequestTimeout) // 从连接池获取连接超时时间
			.build();

		HttpClient httpClient = HttpClients.custom()
			.setConnectionManager(connectionManager)
			.setDefaultRequestConfig(requestConfig)
			.build();

		return new HttpClientManager(httpClient);
	}
}
