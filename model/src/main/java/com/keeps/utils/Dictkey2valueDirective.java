package com.keeps.utils;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

public class Dictkey2valueDirective extends Directive{
	@Override
	public String getName() {
		return "dictk2vtag";
	}
	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter internalContext, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		String dicItem = (String) ((SimpleNode) node.jjtGetChild(0)).value(internalContext);//dicItem 值
		Integer key = (Integer) ((SimpleNode) node.jjtGetChild(1)).value(internalContext);//key 值
		String value = Constants.DICT_ITEM_LIST.get(dicItem).get(key);
		writer.write(value);
        return true;
	}
}