package com.universe.ip2geo.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 地理位置详情表
 * </p>
 *
 * @author liuyalou
 * @since 2022-07-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_geoip2_location")
public class Geoip2LocationDO {

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 网络地址定位唯一标识
	 */
	private Integer geonameId;

	/**
	 * 区域码
	 */
	private String localeCode;

	/**
	 * 大陆代码
	 */
	private String continentCode;

	/**
	 * 大陆名称
	 */
	private String continentName;

	/**
	 * 国家ISO代码
	 */
	private String countryIsoCode;

	/**
	 * 国家名称
	 */
	private String countryName;

	/**
	 * 子分区1_ISO代码
	 */
	private String subdivision1IsoCode;

	/**
	 * 子分区1_名称
	 */
	private String subdivision1Name;

	/**
	 * 子分区2_ISO代码
	 */
	private String subdivision2IsoCode;

	/**
	 * 子分区2_名称
	 */
	private String subdivision2Name;

	/**
	 * 城市名
	 */
	private String cityName;

	/**
	 * 地铁代码
	 */
	private Integer metroCode;

	/**
	 * 时区
	 */
	private String timeZone;

	/**
	 * 是否为欧盟成员，0-否，1-是
	 */
	private Integer isInEuropeanUnion;

}
