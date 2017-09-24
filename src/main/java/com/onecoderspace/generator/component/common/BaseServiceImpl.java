package com.onecoderspace.generator.component.common;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

	public abstract PagingAndSortingRepository<T, ID> getDAO();

	@Override
	public T save(T t){
		return getDAO().save(t);
	}
	
	@Override
	public Iterable<T> save(Iterable<T> entities){
		return getDAO().save(entities);
	}
	
	@Override
	public void del(ID id){
		getDAO().delete(id);;
	}
	
	@Override
	public void del(T t) {
		getDAO().delete(t);
	}
	
	@Override
	public T findById(ID id) {
		return getDAO().findOne(id);
	}
	
	@Override
	public Page<T> findAll(Pageable pageable){
		return getDAO().findAll(pageable);
	}
	
	
}
