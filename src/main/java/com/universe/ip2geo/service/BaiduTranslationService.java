package com.universe.ip2geo.service;

import com.universe.ip2geo.constant.LanguageEnum;
import com.universe.ip2geo.manager.HttpClientManager;
import com.universe.ip2geo.manager.HttpClientManager.HttpClientResp;
import com.universe.ip2geo.pojo.dto.BaiduTranslationApiDTO;
import com.universe.ip2geo.pojo.propreties.biz.BaiduTranslationProperties;
import com.universe.ip2geo.pojo.resp.BaiduApiTranslationResp;
import com.universe.ip2geo.pojo.resp.BaiduApiTranslationResp.TransResult;
import com.universe.ip2geo.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 刘亚楼
 * @date 2022/8/2
 */
@Slf4j
@Service
public class BaiduTranslationService {

	@Autowired
	private HttpClientManager httpClientManager;

	@Autowired
	private BaiduTranslationProperties baiduTranslationProperties;

	public String translate(String queryText, LanguageEnum fromLanguage, LanguageEnum toLanguage) {
		String appId = baiduTranslationProperties.getAppId();
		String appSecret = baiduTranslationProperties.getAppSecret();
		String salt = String.valueOf(System.currentTimeMillis());
		String signature = DigestUtils.md5Hex(appId + queryText + salt + appSecret);
		BaiduTranslationApiDTO baiduTranslationApiDTO = BaiduTranslationApiDTO.builder()
			.q(queryText)
			.from(fromLanguage.getCode())
			.to(toLanguage.getCode())
			.appid(appId)
			.salt(salt)
			.sign(signature)
			.build();

		try {
			Map<String, Object> params = JsonUtils.javaBeanToMap(baiduTranslationApiDTO);
			HttpClientResp httpClientResp = httpClientManager.get(baiduTranslationProperties.getApiUrl(), params);
			if (!httpClientResp.isSuccessful()) {
				log.error("HTTP请求失败, 状态码:{}", httpClientResp.getStatusCode());
				return StringUtils.EMPTY;
			}

			BaiduApiTranslationResp translationResp = JsonUtils.toJavaBean(httpClientResp.getRespContent(), BaiduApiTranslationResp.class);
			if (StringUtils.isNotBlank(translationResp.getErrorCode())) {
				log.error("请求百度翻译Api失败, 错误码:{}, 错误消息:{}", translationResp.getErrorCode(), translationResp.getErrorMsg());
				return StringUtils.EMPTY;
			}

			TransResult transResult = translationResp.getTransResult().stream().findFirst().orElseGet(TransResult::new);
			return transResult.getDst();
		} catch (Exception e) {
			log.error("请求百度翻译Api失败:{}", e.getMessage(), e);
			return StringUtils.EMPTY;
		}
	}

	public static void main(String[] args) {
		String q = "广州市";
		String from = "zh";
		String to = "cn";
		String appId = "20220727001284985";
		String appSecret = "vgYz4M1LyEkYqoCkFQl3";
		String salt = String.valueOf(System.currentTimeMillis());
		String signature = DigestUtils.md5Hex(appId + q + salt + appSecret);
	}

}
