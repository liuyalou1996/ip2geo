package com.universe.ip2geo.pojo.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author 刘亚楼
 * @date 2022/8/3
 */
@Data
public class BaiduApiTranslationResp {

	/**
	 * 原语种
	 */
	private String from;

	/**
	 * 目标语种
	 */
	private String to;

	/**
	 * 翻译结果
	 */
	@JSONField(name = "trans_result")
	private List<TransResult> transResult;

	/**
	 * 错误码
	 */
	@JSONField(name = "error_code")
	private String errorCode;

	/**
	 * 错误消息
	 */
	@JSONField(name = "error_msg")
	private String errorMsg;

	@Data
	public static class TransResult {

		private String src;
		private String dst;
	}
}
