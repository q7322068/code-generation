package com.onecoderspace.generator.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.onecoderspace.generator.dao.GeneratorHelperDao;
import com.onecoderspace.generator.service.GeneratorHelperService;
import com.onecoderspace.generator.vo.ColumnInfo;
import com.onecoderspace.generator.vo.TableInfo;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

@Service("generatorHelperService")
public class GeneratorHelperServiceImpl implements GeneratorHelperService {

	private static Logger logger = LoggerFactory
			.getLogger(GeneratorHelperServiceImpl.class);

	private static Map<String, String> propertyMap = Maps.newConcurrentMap();

	@Autowired
	GeneratorHelperDao generatorHelperDao;
	
	@Value("${generator.project.path}")
	private String localProjectPath;

	@Override
	public boolean setProperty(Map<String, String> map) {
		propertyMap.putAll(map);
		return true;
	}

	@Override
	public boolean allTables(String dbname) {
		List<Object> tables = generatorHelperDao.listAllTable(dbname);
		for (Object item : tables) {
			Object[] arr = (Object[]) item;
			TableInfo tableInfo = new TableInfo(arr);
			boolean success = generateCode(dbname, tableInfo);
			if (!success) {
				logger.error("generator code table=[{}] fail", item.toString());
			}
		}
		return true;
	}

	@Override
	public boolean oneTable(String dbname, String tableName) {
		Object entity = generatorHelperDao.findTableInfo(dbname, tableName);
		Object[] arr = (Object[]) entity;
		TableInfo tableInfo = new TableInfo(arr);
		return generateCode(dbname, tableInfo);
	}

	private boolean generateCode(String dbname, TableInfo item) {
		List<Object> list = generatorHelperDao.listColumInfos(dbname,
				item.getTabelName());
		List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>(list.size());
		String idType = "String";
		for (Object obj : list) {
			Object[] arr = (Object[]) obj;
			ColumnInfo columnInfo = new ColumnInfo(arr);
			columnInfos.add(columnInfo);
			if("id".equals(arr[0])){
				if("int".equals(columnInfo.getModelType())){
					idType = "Integer";
				} else if("long".equals(columnInfo.getModelType())){
					idType = "Long";
				}
			}
		}
		item.setIdType(idType);
		createModel(item, columnInfos);
		createOther(item,"dao");
		createOther(item,"service");
		createOther(item,"serviceImpl");
		createOther(item,"controller");
		return true;
	}

	
	private void createModel(TableInfo item, List<ColumnInfo> columnInfos) {
		String path = getUpPath();
		String dir = String.format("%s/domain",path);
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		String templetePath = String.format("%s%s", localProjectPath,propertyMap.get("templetePath"));
		String filePath = String.format("%s/%s.java", dir,item.getModleName());
		
		Map<String, Object> data = Maps.newHashMap();
		data.put("proList", columnInfos);
		data.put("modelParam", String.format("%s%s",item.getModleName().substring(0, 1).toLowerCase(),item.getModleName().substring(1)));
		data.put("modellower", item.getModleName().toLowerCase());
		data.put("tableInfo", item);
		data.put("packagePath", Joiner.on(".").join(propertyMap.get("packagePath").split("/")).substring(15));
		createTempleteFile(filePath,templetePath,"domain.flt",data);
		
	}

	private void createTempleteFile(String filename, String templetePath,String templeteName,
			Map<String, Object> data) {
		try {
			Configuration cfg = new Configuration();  
			cfg.setDirectoryForTemplateLoading(new File(templetePath)); 
			cfg.setObjectWrapper(new DefaultObjectWrapper());  
			  
			//设置字符集  
			cfg.setDefaultEncoding("UTF-8");  
			  
			//设置尖括号语法和方括号语法,默认是自动检测语法  
			//  自动 AUTO_DETECT_TAG_SYNTAX  
			//  尖括号 ANGLE_BRACKET_TAG_SYNTAX  
			//  方括号 SQUARE_BRACKET_TAG_SYNTAX  
			cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);  
  
			Writer out = new OutputStreamWriter(new FileOutputStream(filename),"UTF-8");  
			Template temp = cfg.getTemplate(templeteName);  
			temp.process(data, out); 
			out.flush();
			out.close();
		}catch (Exception e) {
			logger.error("process due to erro",e);
		}  
	}


	private String getUpPath() {
		return String.format("%s/%s", propertyMap.get("projectPath"),
				propertyMap.get("packagePath"));
	}

	private void createOther(TableInfo item,String type) {
		String path = getUpPath();
		String lastDir = type;
		if("serviceImpl".equals(type)){
			lastDir = "service/impl";
		}
		
		String dir = String.format("%s/%s",path,lastDir);
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		String templetePath = String.format("%s%s", localProjectPath,propertyMap.get("templetePath"));
		String filePath = String.format("%s/%s.java", dir,item.getModleName()+type.substring(0,1).toUpperCase()+type.substring(1));
		
		Map<String, Object> data = Maps.newHashMap();
		data.put("modelParam", String.format("%s%s",item.getModleName().substring(0, 1).toLowerCase(),item.getModleName().substring(1)));
		data.put("modellower", item.getModleName().toLowerCase());
		data.put("item", item);
		data.put("packagePath", Joiner.on(".").join(propertyMap.get("packagePath").split("/")).substring(15));
		
		createTempleteFile(filePath,templetePath,String.format("%s.flt", type),data);
	}

}
