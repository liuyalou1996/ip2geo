package com.universe.ip2geo.pojo.propreties.biz;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 刘亚楼
 * @date 2022/7/31
 */
@Data
@Component
@ConfigurationProperties(prefix = "baidu.translation")
public class BaiduTranslationProperties {

	/**
	 * 百度翻译调用地址
	 */
	private String apiUrl;

	/**
	 * appId
	 */
	private String appId;

	/**
	 * appSecret
	 */
	private String appSecret;
}
