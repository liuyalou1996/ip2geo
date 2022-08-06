package com.universe.ip2geo;

import com.universe.ip2geo.constant.LanguageEnum;
import com.universe.ip2geo.service.BaiduTranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class BaiduTranslationServiceTest {

	@Autowired
	private BaiduTranslationService baiduTranslationService;

	@Test
	public void translateFromChineseToEnglish() {
		String queryText = "澳大利亚";
		String content = baiduTranslationService.translate(queryText, LanguageEnum.CHINESE, LanguageEnum.ENGLISH);
		System.out.println(content);
	}
}
