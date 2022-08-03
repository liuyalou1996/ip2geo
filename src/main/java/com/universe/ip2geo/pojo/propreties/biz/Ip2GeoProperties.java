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
@ConfigurationProperties(prefix = "ip2geo")
public class Ip2GeoProperties {

	private Ip2GeoDbPath dbPath = new Ip2GeoDbPath();

	private Ip2GeoDbSource dbSource = new Ip2GeoDbSource();

	@Data
	public static class Ip2GeoDbPath {

		/**
		 * geoip2 ip数据库
		 */
		private String geoip2;

		/**
		 * ip2region ip数据库
		 */
		private String ip2region;
	}

	@Data
	public static class Ip2GeoDbSource {

		/**
		 * geoip2 ip地理位置映射文件
		 */
		private String geoip2Blocks;

		/**
		 * geoip2 地理位置详情文件
		 */
		private String geoip2Locations;

		/**
		 * ip2 Region地理位置映射文件
		 */
		private String ip2Region;
	}
}
