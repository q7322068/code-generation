package com.onecoderspace.generator.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.onecoderspace.generator.dao.GeneratorHelperDao;
import com.onecoderspace.generator.domain.settingobject;

@Controller
public class DbShowController {
	private static Logger logger = LoggerFactory.getLogger(DbShowController.class);
	@Autowired
	GeneratorHelperDao generatorHelperDao;
	
	@RequestMapping("/index")
	public String getAllSchema(HttpServletRequest request,ModelMap model){
		List<String> list = generatorHelperDao.findTableSchema();	
		model.put("tableSchema", list);
		return "index";
	}
	
	
	@RequestMapping("/showschema")
	public String getAllTables(HttpServletRequest request,ModelMap model,@RequestParam(value = "schemaname", defaultValue = "0") final String schemaname){
		List<String> tablelist = generatorHelperDao.findAllTablesFromSchema(schemaname);
		model.put("tablelist", tablelist);	
		try {
			List<String> templatelist = generatorHelperDao.findAllTemlatesFromSchema();
			model.put("templatelist", templatelist);
		} catch (Exception e) {
			logger.error(String.format("此数据库没有db_templete_setting这个表"), e);
		}
		return "showschema";
	}
	
	 @RequestMapping(value="/setProperty",method=RequestMethod.POST)
	 public String getSettingObject(settingobject settingobject,Model model) {
	        String projectPath=settingobject.getProjectPath();
	        String packagePath=settingobject.getPackagePath();
	        String templetePath=settingobject.getTempletePath();
	        settingobject newsettingobject=new settingobject();
	        newsettingobject.setProjectPath(projectPath);
	        newsettingobject.setPackagePath(packagePath);
	        newsettingobject.setTempletePath(templetePath);
	        //generatorHelperDao.save(newsettingobject);	
	        return "setProperty";
     }
	 
	 

	 /*  @Controller
	    public class DbShowController  {
		   @Autowired
		   GeneratorHelperDao generatorHelperDao;
	       @RequestMapping(value = "/index",method = RequestMethod.GET)
	       public String getTableSchema(Model model) {
	        	List<String> list = generatorHelperDao.findTableSchema();
	    		System.out.println(list);
	            model.addAttribute("tableSchema", list);
	            return "index";
	        }*/
	

}
	




