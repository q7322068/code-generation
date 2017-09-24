package com.onecoderspace.generator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "generator_helper")
public class GeneratorHelper {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/** 用户账号 */
	private String version;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "GeneratorHelper [id=" + id + ", version=" + version + "]";
	}

}
