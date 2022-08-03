package com.universe.ip2geo.service;

import com.universe.ip2geo.mapper.Geoip2LocationMapper;
import com.universe.ip2geo.mapper.Geoip2NetworkMapper;
import com.universe.ip2geo.pojo.domain.Geoip2LocationDO;
import com.universe.ip2geo.pojo.domain.Geoip2NetworkDO;
import com.universe.ip2geo.pojo.propreties.biz.Ip2GeoProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 刘亚楼
 * @date 2022/7/30
 */
@Slf4j
@Service
public class GeoIp2ResourceImporService extends AbstractImportResourceService {

	@Autowired
	private Ip2GeoProperties ip2GeoProperties;

	@Autowired
	private Geoip2NetworkMapper geoip2NetworkMapper;

	@Autowired
	private Geoip2LocationMapper geoip2LocationMapper;

	public void importGeoIp2NetworkInfo() {
		String blocksFilePath = ip2GeoProperties.getDbSource().getGeoip2Blocks();

		List<Geoip2NetworkDO> geoip2NetworkDOS = new ArrayList<>();
		try (InputStream is = new FileInputStream(blocksFilePath);
				 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			// 跳过第一行
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(",", -1);
				Geoip2NetworkDO geoip2NetworkDO = Geoip2NetworkDO.builder()
					.networkStart(items[0])
					.networkEnd(items[1])
					.geonameId(Optional.ofNullable(items[2]).filter(StringUtils::isNotBlank).map(Integer::parseInt).orElse(null))
					.registeredCountryGeonameId(Optional.ofNullable(items[3]).filter(StringUtils::isNotBlank).map(Integer::parseInt).orElse(null))
					.representedCountryGeonameId(Optional.ofNullable(items[4]).filter(StringUtils::isNotBlank).map(Integer::parseInt).orElse(null))
					.isAnonymousProxy(Optional.ofNullable(items[5]).filter(StringUtils::isNotBlank).map(Boolean::parseBoolean).orElse(false))
					.isSatelliteProvider(Optional.ofNullable(items[6]).filter(StringUtils::isNotBlank).map(Boolean::parseBoolean).orElse(false))
					.postalCode(Optional.ofNullable(items[7]).filter(StringUtils::isNotBlank).orElse(StringUtils.EMPTY))
					.latitude(Optional.ofNullable(items[8]).filter(StringUtils::isNotBlank).map(Float::parseFloat).orElse(null))
					.longitude(Optional.ofNullable(items[9]).filter(StringUtils::isNotBlank).map(Float::parseFloat).orElse(null))
					.accuracyRadius(Optional.ofNullable(items[10]).filter(StringUtils::isNotBlank).map(Integer::parseInt).orElse(null))
					.build();
				geoip2NetworkDOS.add(geoip2NetworkDO);
			}
			super.doImport(() -> geoip2NetworkDOS, geoip2NetworkMapper::batchSaveNetworkInfo, 10000, 50);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void importGeoIp2LocationInfo() {
		String locationsFilePath = ip2GeoProperties.getDbSource().getGeoip2Locations();

		List<Geoip2LocationDO> geoip2LocationDOList = new ArrayList<>();
		try (InputStream is = new FileInputStream(locationsFilePath);
				 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			// 跳过第一行
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(",", -1);
				Geoip2LocationDO geoip2LocationDO = Geoip2LocationDO.builder()
					.geonameId(Integer.parseInt(items[0]))
					.localeCode(items[1])
					.continentCode(items[2])
					.continentName(items[3].replace("\"", StringUtils.EMPTY))
					.countryIsoCode(items[4])
					.countryName(items[5].replace("\"", StringUtils.EMPTY))
					.subdivision1IsoCode(items[6])
					.subdivision1Name(Optional.ofNullable(items[7]).map(v -> v.replace("\"", StringUtils.EMPTY)).orElse(StringUtils.EMPTY))
					.subdivision2IsoCode(items[8])
					.subdivision2Name(Optional.ofNullable(items[9]).map(v -> v.replace("\"", StringUtils.EMPTY)).orElse(StringUtils.EMPTY))
					.cityName(Optional.ofNullable(items[10]).map(v -> v.replace("\"", StringUtils.EMPTY)).orElse(StringUtils.EMPTY))
					.metroCode(Optional.ofNullable(items[11]).filter(StringUtils::isNotBlank).map(Integer::parseInt).orElse(0))
					.timeZone(items[12])
					.isInEuropeanUnion(Optional.ofNullable(items[13]).filter(StringUtils::isNotBlank).map(Integer::parseInt).orElse(0))
					.build();
				geoip2LocationDOList.add(geoip2LocationDO);
			}
			super.doImport(() -> geoip2LocationDOList, geoip2LocationMapper::batchSaveLocationInfo, 10000, 50);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
