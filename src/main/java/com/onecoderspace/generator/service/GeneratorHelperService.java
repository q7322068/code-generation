package com.onecoderspace.generator.service;

import java.util.Map;

public interface GeneratorHelperService {
	
	boolean setProperty(Map<String, String> map);

	boolean allTables(String dbname);

	boolean oneTable(String dbname, String tableName);

}
