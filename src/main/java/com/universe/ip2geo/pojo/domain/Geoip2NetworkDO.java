package com.universe.ip2geo.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * IP地理位置映射表
 * </p>
 *
 * @author liuyalou
 * @since 2022-07-24
 */
@Data
@Builder
@AllArgsConstructor
@TableName("t_geoip2_network")
public class Geoip2NetworkDO {

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * IP起始地址
	 */
	private String networkStart;

	/**
	 * IP结尾地址
	 */
	private String networkEnd;

	/**
	 * 网络地址定位唯一标识
	 */
	private Integer geonameId;

	private Integer registeredCountryGeonameId;

	private Integer representedCountryGeonameId;

	private Boolean isAnonymousProxy;

	private Boolean isSatelliteProvider;

	/**
	 * 邮编
	 */
	private String postalCode;

	/**
	 * 纬度
	 */
	private Float latitude;

	/**
	 * 经度
	 */
	private Float longitude;

	/**
	 * 地理位置准确半径
	 */
	private Integer accuracyRadius;

}
