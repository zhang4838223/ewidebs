package net.ewide.platform.interfaces.utils;

import net.ewide.platform.interfaces.utils.Children;
import net.ewide.platform.interfaces.vo.Data;

/**
 * @author wanghaozhe
 *
 */
public class Node extends Data{
	private static final long serialVersionUID = 1L;
	 /** 
	  * 节点编号 
	  */  
	 private String id;  
	 /** 
	  * 节点内容 
	  */  
	 private String name;  
	 /** 
	  * 父节点编号 
	  */  
	 private String parentId;  
	 /** 
	  * 孩子节点列表 
	  */  
	 private Children children = new Children();  
	 
	 private String leaf;
	   
	 // 先序遍历，拼接JSON字符串  
	 public String toString() {    
	  String result = "{"  
	   + "\"id\":\"" + id + "\""  
	   + ",\"text\":\"" + name + "\"";  
	    
	  if (children != null && children.getSize() != 0) {  
	   result += ",\"children\":" + children.toString();  
	  } else {  
	   result += ",\"leaf\":true";  
	  }  
	  return result + "}";  
	 }  
	   
	 // 兄弟节点横向排序  
	 public void sortChildren() {  
	  if (children != null && children.getSize() != 0) {  
	   children.sortChildren();  
	  }  
	 }  
	   
	 // 添加孩子节点  
	 public void addChild(Node node) {  
	  this.children.addChild(node);  
	 }
	 
	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}  
	}  
	  

