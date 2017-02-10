package com.keeps.tools.utils;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

/** 
 * <p>Title: StringUtilsDirective.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月8日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class SubCutDirective extends Directive{

	@Override
	public String getName() {
		return "subcut";
	}
	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter internalContext, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		if (((SimpleNode) node.jjtGetChild(0)).value(internalContext)==null) {
			writer.write("");
			return true;
		};
		String str = (String)((SimpleNode) node.jjtGetChild(0)).value(internalContext);
		Integer length = (Integer) ((SimpleNode) node.jjtGetChild(1)).value(internalContext);
		if (str.length()>length) {
			str = str.substring(0, length)+"...";
		}
		writer.write(str);
        return true;
	}

}
