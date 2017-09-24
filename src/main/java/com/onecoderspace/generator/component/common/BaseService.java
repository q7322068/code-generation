package com.onecoderspace.generator.component.common;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T,ID extends Serializable> {

	/**
	 * 新增或更新
	 */
	T save(T t);
	
	/**
	 * 新增或更新
	 * 注意数量不要太大，特别是数据迁移时不要使用该方法
	 */
	Iterable<T> save(Iterable<T> entities);
	
	/**
	 * 根据ID删除
	 */
	void del(ID id);
	
	/**
	 * 根据实体删除
	 */
	void del(T t);
	
	/**
	 * 根据ID查找对象
	 */
	T findById(ID id);
	
	/**
	 * 分页排序获取数据
	 * 禁止使用该接口进行count操作
	 * Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC,"id"));
	 * @param pageable
	 * @return
	 */
	Page<T> findAll(Pageable pageable);
	
}
