package com.keeps.ksxtmanager.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.ksxtmanager.system.dao.DictDao;
import com.keeps.ksxtmanager.system.service.DictService;
import com.keeps.model.TDict;
import com.keeps.model.TDictType;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: DictServiceImpl.java</p>  
 * <p>Description: 字典SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class DictServiceImpl extends AbstractService implements DictService {
	
	@Autowired
	private DictDao dictDao;

	@Override
	public Page queryDictList(TDict dict) {
		return dictDao.queryDictList(dict);
	}

	@Override
	public List<TDict> getDictList(TDict dict){
		return dictDao.getDictList(dict);
	}

	public List<TreeNode> getDictTree(String code){
		List<TreeNode> newlistnode = new ArrayList<>();
		TreeNode node  = new TreeNode();
		/*node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("产品分类");*/
		List<TreeNode> listtreenode  = dictDao.getDictTypeTree(code);
		for (TreeNode treeNode : listtreenode) {
			treeNode.setNocheck(true);
			TDict newmo = new TDict();
			newmo.setTypeid(Integer.parseInt(treeNode.getId()));
			List<TDict> dictlist = dictDao.getDictList(newmo);
			for (TDict tDict : dictlist) {
				node = new TreeNode();
				node.setId(String.valueOf(tDict.getId()));
				node.setName(tDict.getName()+"￥"+tDict.getValue());
				node.setpId(treeNode.getId());
				node.setNocheck(false);
				node.setObj(tDict.getValue());
				node.setType(2);
				newlistnode.add(node);
			}
			newlistnode.add(treeNode);
		}
		//listtreenode.add(node);
		return newlistnode;
	}

	@Override
	public List<TDictType> getDictTypeList(TDictType tDictType){
		return dictDao.getDictTypeList(tDictType);
	}

	@Override
	public TDict getDictById(Integer id) {
		return dictDao.getDictById(id);
	}

	@Override
	public TDictType geTDictTypeById(Integer id) {
		return super.get(TDictType.class, id);
	}

	@Override
	public String deleteDictById(String ids) {
		Integer count = dictDao.countDictFixedByIds(ids);
		Assert.isTrue(count<=0, "要删除的字典有内置字典,不允许删除.");
		super.removeEntity(TDict.class, ids,IdTypes.Integer);
		return "删除成功!";
	}

	@Override
	public String deleteDictTypeById(Integer id) {
		TDictType typemo = super.get(TDictType.class, id);
		Assert.isTrue(typemo.getFixed()!=1, "该分类为内置分类,不允许删除.");
		Integer count = dictDao.countDictTypeById(id);
		Assert.isTrue(count<=0, "要删除的分类下有子分类,不允许删除.");
		count = dictDao.countDictByTypeid(id);
		Assert.isTrue(count<=0, "要删除的分类下有数据,不允许删除.");
		super.removeEntity(TDictType.class, id);
		return "删除成功!";
	}

	@Override
	public List<TreeNode> getDictTypeTree() {
		TreeNode node  = new TreeNode();
		node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("字典分类");
		List<TreeNode> listtreenode  = dictDao.getDictTypeTree("");
		listtreenode.add(node);
		return listtreenode;
	}
	
	@Override
	public List<TreeNode> getDicttypeTreeByCode(String code){
		List<TDictType> listdicttype  = dictDao.getDictTypeTreeByCode(code);
		List<TreeNode> listtreenode = new ArrayList<>();
		int pid = getPid(listdicttype);
		if (pid==0) {
			return null;
		}
		for (TDictType tDictType : listdicttype) {
			TreeNode node  = new TreeNode();
			if (tDictType.getPid()==0) {
				node.setId("0");
				node.setpId("0");
			}else{
				node.setId(tDictType.getId()+"");
				if (tDictType.getPid()==pid) {
					node.setpId("0");
				}else{
					node.setpId(tDictType.getPid()+"");
				}
			}
			node.setOpen(1);
			node.setName(tDictType.getName());
			listtreenode.add(node);
		}
		return listtreenode;
	}

	public List<TreeNode> getDictTypeSelectByCode(String code){
		List<TDictType> listdicttype  = dictDao.getDictTypeTreeByCode(code);
		List<TreeNode> listtreenode = new ArrayList<>();
		int pid = getPid(listdicttype);
		if (pid==0) {
			return null;
		}
		for (TDictType tDictType : listdicttype) {
			TreeNode node  = new TreeNode();
			if (tDictType.getPid()==0) {
				continue;
			}
			node.setId(tDictType.getId()+"");
			if (tDictType.getPid()==pid) {
				node.setpId("0");
			}else{
				node.setpId(tDictType.getPid()+"");
			}
			node.setOpen(1);
			node.setName(tDictType.getName());
			listtreenode.add(node);
		}
		return listtreenode;
	}

	public int getPid(List<TDictType> listdicttype){
		for (TDictType tDictType : listdicttype) {
			if (tDictType.getPid()==0) {
				return tDictType.getId();
			}
		}
		return 0;
	}
	
	public String saveOrUpdateDict(TDict dict){
		if (dict.getFixed()!=null && dict.getFixed()==1) {
			if(dict.getSort()==null){
				dict.setSort(0);
			}
			super.update(dict, EditType.NULL_UN_UPDATE);
			return null;
		}
		Assert.isTrue(StringUtils.hasText(dict.getTypecode()), "字典分类不能为空!");
		Assert.isTrue(dict.getTypeid()!=null, "字典ID不能为空!");
		Assert.isTrue(StringUtils.hasText(dict.getName()), "字典名称不能为空!");
		Assert.isTrue(StringUtils.hasText(dict.getValue()), "字典值不能为空!");
		//判断字典值是否正确
		TDictType typemo = super.get(TDictType.class, dict.getTypeid());
		if(typemo.getValuetype().indexOf("float(")!=-1){//判断是否是小数
			Integer d  = Integer.parseInt(CommonUtils.getStrMatchergroup(typemo.getValuetype(), "(", ")"));
			Assert.isTrue(CommonUtils.isDecimal(dict.getValue(),d), "字典值必须是数值类型，可保留"+d+"位小数!");
		}else if(typemo.getValuetype().indexOf("int(")!=-1){
			Assert.isTrue(CommonUtils.isNumeric(dict.getValue()), "字典值必须是数字类型!");
			String str  = CommonUtils.getStrMatchergroup(typemo.getValuetype(), "(", ")");
			String[] strp = str.split("~");
			Integer minnum = Integer.parseInt(strp[0]);
			Integer maxnum = Integer.parseInt(strp[1]);
			Integer values = Integer.parseInt(dict.getValue());
			Assert.isTrue(values>=minnum, "字典值必须是数字类型,且不能小于"+minnum+",大于"+maxnum+"!");
			Assert.isTrue(values<=maxnum, "字典值必须是数字类型,且不能小于"+minnum+",大于"+maxnum+"!");
		}else if(typemo.getValuetype().indexOf("int")!=-1){
			Assert.isTrue(CommonUtils.isNumeric(dict.getValue()), "字典值必须是数字类型!");
		}
		if(!dictDao.isUnique(dict, new String[]{"name","typecode"})){
			throw new CapecException("["+dict.getName()+"]字典名称已经存在,不允许重复添加!");
		}
		if(dict.getStatus()==null){
			dict.setStatus(1);
		}
		if(dict.getSort()==null){
			dict.setSort(0);
		}
		if(dict.getId()==null){
			dict.setFixed(2);//是否内置，一般添加的都不是内置字典
		}
		super.saveOrUpdateEntity(dict, EditType.NULL_UN_UPDATE);
		return null;
	}
	
	public String saveOrUpdateDictType(TDictType dictType){
		if (dictType.getFixed()!=null && dictType.getFixed()==1) {
			if(dictType.getSort()==null){
				dictType.setSort(0);
			}
			super.update(dictType, EditType.NULL_UN_UPDATE);
			return null;
		}
		Assert.isTrue(dictType.getPid()!=null, "上级分类不能为空");
		Assert.isTrue(StringUtils.hasText(dictType.getName()), "分类名不能为空!");
		if (dictType.getId()==null&&dictType.getPid()==0) {
			Assert.isTrue(StringUtils.hasText(dictType.getCode()), "分类CODE不能为空!");
		}
		if(!dictDao.isUnique(dictType, new String[]{"name"})){
			throw new CapecException("["+dictType.getName()+"]分类名称已经存在,不允许重复添加!");
		}
		if (dictType.getId()==null&&dictType.getPid()==0) {
			if(!dictDao.isUnique(dictType, new String[]{"code"})){
				throw new CapecException("["+dictType.getCode()+"]分类CODE已经存在,不允许重复添加!");
			}
		}	
		if (dictType.getPid()==0) {
			dictType.setPname("一级字典分类");
			dictType.setFullpid("0");
			dictType.setValuetype("string(1,50)");
		}else{
			TDictType typemo = super.get(TDictType.class, dictType.getPid());
			dictType.setPname(typemo.getName());
			dictType.setCode(typemo.getCode());
			if ("0".equals(typemo.getFullpid())) {
				dictType.setFullpid(typemo.getId()+"");	
			}else{
				dictType.setFullpid(typemo.getFullpid()+","+typemo.getId());
			}
			dictType.setValuetype(typemo.getValuetype());
			dictType.setDescription(typemo.getDescription());
		}
		if(dictType.getStatus()==null){
			dictType.setStatus(1);
		}
		if(dictType.getSort()==null){
			dictType.setSort(0);
		}
		if (dictType.getId()==null) {
			dictType.setFixed(2);
		}
		super.saveOrUpdateEntity(dictType, EditType.NULL_UN_UPDATE);
		return null;
	}
	
	public List<TDictType> getDictTypeByCode(String code){
		return dictDao.getDictTypeByCode(code);
	}

	public List<TDict> getDictByCode(String code){
		return dictDao.getDictByCode(code);
	}

}
