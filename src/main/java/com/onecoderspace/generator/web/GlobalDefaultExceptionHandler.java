package com.onecoderspace.generator.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * 全局异常捕获
 * @author yangwenkui
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Map<String, Object> defaultErrorHandler(HttpServletRequest req,HttpServletResponse response, Exception e) {
		// 打印异常信息：
		logger.error("defaultErrorHandler:", e);
		response.setStatus(500);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", "");
		map.put("msg", "系统异常");
		return map;
	}

}
