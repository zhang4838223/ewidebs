/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.entity;

import javax.validation.constraints.NotNull;

import net.ewide.platform.common.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 数据字典Entity
 * 
 * @author wangtao
 * @version 2016-04-26
 */
public class Dict extends DataEntity<Dict> {

	private static final long serialVersionUID = 1L;
	private String value; // 数据值
	private String label; // 标签名
	private String dictType; // 数据字典类型（数据字典类型：TYPE，数据字典值：VALUE）
	private Integer sort; // 排序（升序）
	private String orId;  //关联字典id
	private String orLabel; //关联字典name
	public String getOrLabel() {
		return orLabel;
	}

	public void setOrLabel(String orLabel) {
		this.orLabel = orLabel;
	}

	public String getOrId() {
		return orId;
	}

	public void setOrId(String orId) {
		this.orId = orId;
	}

	private String parentId; // 父级编号

	public Dict() {
		super();
		this.sort = 30;
	}

	public Dict(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "数据值长度必须介于 1 和 100 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Length(min = 1, max = 100, message = "标签名长度必须介于 1 和 100 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Length(min = 1, max = 20, message = "数据字典类型（数据字典类型：TYPE，数据字典值：VALUE）长度必须介于 1 和 20 之间")
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	@NotNull(message = "排序（升序）不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@JsonBackReference
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}