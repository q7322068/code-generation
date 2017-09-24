package com.onecoderspace.generator.dao;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.onecoderspace.generator.domain.GeneratorHelper;

/**
 *用户相关接口 对应 AuthUser这个表
 *
 */

public interface GeneratorHelperDao extends PagingAndSortingRepository<GeneratorHelper, Integer>{
	

	@Query(value="select COLUMN_NAME as columnName,COLUMN_TYPE as columnType,COLUMN_DEFAULT as columnDefault,COLUMN_COMMENT as columnComment,CHARACTER_MAXIMUM_LENGTH as columnCharacterMaximumLength from information_schema.columns where table_schema = ?1 and table_name = ?2 ; ",nativeQuery=true)
	List<Object> listColumInfos(String dbname,String tableName);

	@Query(value="SELECT TABLE_NAME as tableName,TABLE_COMMENT as tableComment from information_schema.`TABLES` where TABLE_SCHEMA=?1",nativeQuery=true)
	List<Object> listAllTable(String dbname);

	@Query(value="SELECT TABLE_NAME as tableName,TABLE_COMMENT as tableComment from information_schema.`TABLES` where TABLE_SCHEMA=?1 and TABLE_NAME=?2",nativeQuery=true)
	Object findTableInfo(String dbname, String tableName);
	
	@Query(value="select DISTINCT(TABLE_SCHEMA) from information_schema.`TABLES`;",nativeQuery=true)
	List<String>  findTableSchema();
	
	@Query(value="SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA =?1",nativeQuery=true)
	List<String>  findAllTablesFromSchema(String dbname);
	
	@Query(value="SELECT templetepath FROM db_templete_setting;",nativeQuery=true)
	List<String>  findAllTemlatesFromSchema();
	
}
