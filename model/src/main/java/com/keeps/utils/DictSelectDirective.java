package com.keeps.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

public class DictSelectDirective extends Directive{
	@Override
	public String getName() {
		return "dictselecttag";
	}
	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter internalContext, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		String name = (String) ((SimpleNode) node.jjtGetChild(0)).value(internalContext);//select name 值
		String dicItem = (String) ((SimpleNode) node.jjtGetChild(1)).value(internalContext);//dicItem 值
		Integer key = 0;
		if(node.jjtGetNumChildren()>2){
			key = (Integer) ((SimpleNode) node.jjtGetChild(2)).value(internalContext);
			key = key==null?0:key;
		}
		String insertitem = "0";//是否增加全部标签
		if(node.jjtGetNumChildren()>3){
			insertitem = (String) ((SimpleNode) node.jjtGetChild(3)).value(internalContext);
			insertitem = insertitem==null?"0":insertitem;
		}
		Map<Integer, String> mapdata = Constants.DICT_ITEM_LIST.get(dicItem);
		StringBuffer sb = new StringBuffer();
		sb.append("<select class='form-control' name=\""+name+"\" >"); 
		Iterator<Map.Entry<Integer, String>> entries = mapdata.entrySet().iterator();
		if ("1".equals(insertitem)) {
			sb.append("<option value = ''>全部</option>");
		}
		while (entries.hasNext()) {
			Map.Entry<Integer, String> entry = entries.next();
			if("2".equals(insertitem)){
				if (key.equals(entry.getKey())) {
					writer.write(entry.getValue());
			        return true;
				}
			}else{
				if (key.equals(entry.getKey())) {
					sb.append("<option value = '"+entry.getKey()+"' selected>"+entry.getValue()+"</option>");
				}else{
					sb.append("<option value = '"+entry.getKey()+"'>"+entry.getValue()+"</option>");
				}
			}
		}
		sb.append("</select>");
		writer.write(sb.toString());
        return true;
	}
}