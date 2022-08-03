package com.universe.ip2geo.mybatis;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;

/**
 * 具体配置参考：<a href="https://baomidou.com/pages/981406/">MyBatis Plus Generator配置</a>
 * 代码示例参考：<a href="https://github.com/baomidou/generator/tree/develop/mybatis-plus-generator/src/test/java/com/baomidou/mybatisplus/generator/samples">代码生成示例</a>
 * @author 刘亚楼
 * @date 2022/4/2l
 */
public class MybatisPlusCodeAutoGeneration {

	private static final String URL = "jdbc:mysql://gz-cdb-drh06wqx.sql.tencentcdb.com:57613/insurance";
	private static final String USERNAME = "lingbomanbu";
	private static final String PASSWORD = "Lyl14786632348";

	/**
	 * 自动生成代码输出目录，这里默认类路径下的src/test/java/com/universe包下，即当前工程的test目录下。
	 */
	private static final String OUTPUT_DIR = System.getProperty("user.dir") + File.separator + "src/test/java/com/universe";

	public static void main(String[] args) {
		FastAutoGenerator.create(URL, USERNAME, PASSWORD)
			// 开启fileOverrride重新旧文件，disableOpenDir禁用代码生成后打开输出目录对话框
			.globalConfig(builder -> builder.author("liuyalou").fileOverride().disableOpenDir().outputDir(OUTPUT_DIR))
			// parent指定生成的代码在哪个包下，entity可以指定实体(DO)所在的包名
			.packageConfig(builder -> builder.parent("com.universe").entity("pojo.domain"))
			// addInclude指定包含的表名，不调用该方法默认为所有表生成代码；addTablePrefix可以过滤表前缀，即t_user变成user
			.strategyConfig(builder -> builder.addTablePrefix("t_").addInclude("t_geoip2_network").addInclude("t_geoip2_location")
				// 禁用为实体类生成序列化ID；formatFileName格式化生成的实体类名称，即t_user -> UserDO
				.entityBuilder().disableSerialVersionUID()
												.formatFileName("%sDO")
				// formatMapperFileName格式化Mapper接口名称，即t_user -> UserMapper
				// formatXmlFileName格式化Mapper.xml文件名称，即t_user -> UserMapper.xml
				.mapperBuilder().formatMapperFileName("%sMapper")
												.formatXmlFileName("%sMapper")
												.enableBaseColumnList()
												.enableBaseResultMap()
				.build()
			)
			// MyBatis-Plus代码生成器是通过模板引擎来渲染文件的，默认模板引擎是Velocity，这里使用Freemarker
			.templateEngine(new FreemarkerTemplateEngine())
			.execute();
	}

}
