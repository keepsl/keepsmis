package com.keeps.utils;
/** 
 * <p>Title: TreeNode.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月20日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class TreeNode {
	private String id;
	private String pId;
	private String name;
	private String iconSkin;
	private String icon;
	private Integer parent;
	private Integer open;
	private Integer checked;
	private boolean nocheck;
	private boolean setChkDisabled;
	private Integer sortid;
	private String desc;
	private String funcIcon;
	private Integer leaf;
	private String obj;
	private Integer type;//自定义属性type；用来业务处理区分不同菜单
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public Integer getOpen() {
		return open;
	}
	public void setOpen(Integer open) {
		this.open = open;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	public boolean getNocheck() {
		return this.nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	public Integer getSortid() {
		return sortid;
	}
	public void setSortid(Integer sortid) {
		this.sortid = sortid;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFuncIcon() {
		return funcIcon;
	}
	public void setFuncIcon(String funcIcon) {
		this.funcIcon = funcIcon;
	}
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	public boolean isSetChkDisabled() {
		return setChkDisabled;
	}
	public void setSetChkDisabled(boolean setChkDisabled) {
		this.setChkDisabled = setChkDisabled;
	}
	
	
}
