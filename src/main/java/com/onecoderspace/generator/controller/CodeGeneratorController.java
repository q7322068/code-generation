package com.onecoderspace.generator.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.onecoderspace.generator.service.GeneratorHelperService;

@RestController
@RequestMapping("/generate")
public class CodeGeneratorController {
	
	private static int isSetting = 0;
	
	/**
	 * 设置公共参数
	 * e.g. http://localhost:8009/generate/setProperty?projectPath=D:/workspace/code-generator&packagePath=/src/main/java/com/hexun/bdc/generator&templetePath=/src/main/resources/code-templete
	 * @param projectPath 要生成代码的项目的路径：  D:/workspace/code-generator
	 * @param packagePath 要生成代码的项目的包的根路径：/src/main/java/com/hexun/bdc/generator
	 * @param templetePath 本项目内代码模板路径： /src/main/resources/code-templete
	 * @return
	 */
	@RequestMapping("/setProperty")
	public boolean setProperty(String projectPath,String packagePath,String templetePath){
		isSetting = 1;
		Map<String, String> map = Maps.newHashMap();
		map.put("projectPath", projectPath);
		map.put("packagePath", packagePath);
		map.put("templetePath", templetePath);
		boolean success = generatorHelperService.setProperty(map);
		return success;
	}

	/**
	 * 生成某个库下所有表对应的模板代码 小心使用，避免覆盖现有代码
	 * @param dbname 数据库名称
	 * @return
	 */
	@RequestMapping("/tables")
	public String tables(String dbname){
		if(isSetting == 0){
			return "请先设置公共参数";
		}
		boolean success = generatorHelperService.allTables(dbname);
		return success ? "success" : "fail";
	}
	
	/**
	 * 生成某个库下某个表对应的模板代码
	 * @param dbname  数据库名称
	 * @param tableName 表名称
	 * @return
	 */
	@RequestMapping("/table")
	public String table(String dbname,String tableName){
		if(isSetting == 0){
			return "请先设置公共参数";
		}
		boolean success = generatorHelperService.oneTable(dbname,tableName);
		return success ? "success" : "fail";
	}
	
	@Autowired
	private GeneratorHelperService generatorHelperService;
	
}
