package com.universe.ip2geo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.universe.pojo.domain.Geoip2LocationDO;

import java.util.List;

/**
 * <p>
 * 地理位置详情表 Mapper 接口
 * </p>
 *
 * @author liuyalou
 * @since 2022-07-24
 */
public interface Geoip2LocationMapper extends BaseMapper<Geoip2LocationDO> {

	void batchSaveLocationInfo(List<Geoip2LocationDO> geoip2LocationDOS);
}
