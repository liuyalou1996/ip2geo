package com.universe.ip2geo.constant;

/**
 * @author 刘亚楼
 * @date 2022/8/3
 */
public enum LanguageEnum {

	/**
	 * 中文
	 */
	CHINESE("zh"),
	/**
	 * 英文
	 */
	ENGLISH("en"),
	/**
	 * 日语
	 */
	JAPANESE("jp"),
	/**
	 * 韩语
	 */
	KOREAN("kor"),
	/**
	 * 法语
	 */
	FRENCH("fra"),
	/**
	 * 西班牙语
	 */
	SPANISH("spa"),
	/**
	 * 俄语
	 */
	RUSIAN("ru");

	private String code;

	LanguageEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
