package com.keeps.tools.utils;

import java.util.HashMap;
import java.util.Map;

/** 
 * <p>Title: AsyncTreeNode.java</p>  
 * <p>Description: 用于映射的树节点JSON对象</p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月10日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class AsyncTreeNode {
	private Integer id;		//节点ID
	private String name;	//节点名称
	private boolean spread;	//是否展开状态（默认false）
	private String href;	//节点链接（可选），未设则不会跳转
	private Object children;//同nodes节点，可无限延伸
	private String icon;//图标样式
	private String alias;	//别名
	private Map<String,Object> attributes = new HashMap<String,Object>();	//自定义的属性
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSpread() {
		return spread;
	}
	public void setSpread(boolean spread) {
		this.spread = spread;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Object getChildren() {
		return children;
	}
	public void setChildren(Object children) {
		this.children = children;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	
}
