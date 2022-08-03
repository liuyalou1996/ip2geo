package com.universe.ip2geo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.universe.ip2geo.pojo.domain.Geoip2NetworkDO;

import java.util.List;

/**
 * <p>
 * IP地理位置映射表 Mapper 接口
 * </p>
 *
 * @author liuyalou
 * @since 2022-07-24
 */
public interface Geoip2NetworkMapper extends BaseMapper<Geoip2NetworkDO> {

	void saveNetworkInfo(Geoip2NetworkDO geoip2NetworkDO);

	void batchSaveNetworkInfo(List<Geoip2NetworkDO> geoip2NetworkDOS);
}
