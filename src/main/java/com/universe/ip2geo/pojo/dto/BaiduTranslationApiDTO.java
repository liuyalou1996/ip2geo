package com.universe.ip2geo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 刘亚楼
 * @date 2022/8/3
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaiduTranslationApiDTO {

	/**
	 * 翻译内容
	 */
	private String q;

	/**
	 * 源语种
	 */
	private String from;

	/**
	 * 目标语种
	 */
	private String to;

	/**
	 * appId
	 */
	private String appid;

	/**
	 * 随机颜值
	 */
	private String salt;

	/**
	 * 签名
	 */
	private String sign;
}
