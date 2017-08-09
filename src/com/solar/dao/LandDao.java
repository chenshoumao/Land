package com.solar.dao;

import java.util.List;
import java.util.Map;

import com.solar.bean.Version;

public interface LandDao {
	
	public List<Map<String, Object>> analysisVersion(String host,String versionInfo);
	
	public Map<String, Object> versionFilter(String key,List<String> keyList,Map<String, Object> allVersion,String shipVerion,String moduleVersionOfLand);
	
	public List<Version> getVersionFromPath(String path);
	
	public boolean generateIncrement(String host,String key,String fileName,String moduleVersionOfShip,String moduleVersionOfLand);
	
	//声明解压包的接口
	public Map<String, Object> unzip(String key,String path);
}
