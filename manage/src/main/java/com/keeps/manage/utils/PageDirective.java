package com.keeps.manage.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

import com.keeps.tools.utils.StringUtils;

public class PageDirective extends Directive{
	/** **************样式常量******************** */
	public static final String LAYUI = "layui";
	private String className = LAYUI;	//样式
	private String url;//提交URL
	private Integer page;//当前页
	private Integer rows;//每页显示数
	private Integer total;//总页数
	private Integer pagecount;//总页数
	@Override
	public String getName() {
		return "pagetag";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter internalContext, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		className = (String) ((SimpleNode) node.jjtGetChild(0)).value(internalContext);//样式名
		page = (Integer) ((SimpleNode) node.jjtGetChild(1)).value(internalContext);//当前页
		total = (Integer) ((SimpleNode) node.jjtGetChild(2)).value(internalContext);//总页数
		rows = (Integer) ((SimpleNode) node.jjtGetChild(3)).value(internalContext);//每页显示数
		
		this.pagecount = ((this.total - 1) / this.rows) + 1;
		
		ViewToolContext context = (ViewToolContext)internalContext.getInternalUserContext();
		HttpServletRequest request = context.getRequest();
		url = getParamUrl(request);
		String pagetool = pagetool(className);
		writer.write(pagetool);
        return true;
	}
	
	public String pagetool(String className) {
		StringBuffer str = new StringBuffer();
		if (className.equals(LAYUI)) {
			str.append("<div class='layui-box layui-laypage' id='layui-laypage-0'>");
			if (page==1) {
				str.append("<a href='javascript:;' class='layui-laypage_first' title='首页'>首页</a>");
				str.append("<a href='javascript:;' class='layui-laypage-prev'>上一页</a>");
			} else {
				str.append("<a href='"+url+"page=1&rows="+rows+"' class='laypage_first' title='首页'>首页</a>");
				str.append("<a href='"+url+"page="+(page-1)+"&rows="+rows+"' class='layui-laypage-prev'>上一页</a>");
			}
			for (int i = 1; i <= pagecount; i++) {
				if (page==i) {
					str.append("<span class='layui-laypage-curr'><em class='layui-laypage-em'></em><em>"+i+"</em></span>");
				} else {
					str.append("<a href='"+url+"page="+i+"&rows="+rows+"'>"+i+"</a>");
				}
			}
			if (page==pagecount) {
				str.append("<a href='javascript:;' class='layui-laypage-next'>下一页</a>");
				str.append("<a href='javascript:;' class='layui-laypage-last' title='尾页'>末页</a>");
			}else{
				str.append("<a href='"+url+"page="+(page+1)+"&rows="+rows+"' class='layui-laypage-next'>下一页</a>");
				str.append("<a href='"+url+"page="+pagecount+"&rows="+rows+"' class='layui-laypage-last' title='尾页'>末页</a>");
			}
			str.append("</div>");
		}
		return str.toString();
	}
	
	public String getParamUrl(HttpServletRequest request) {
		String url = request.getContextPath()+"/"+request.getServletPath();
		String totalParams = "";
		Map params = request.getParameterMap();// 得到所有参数名
		if(params!=null){
			Iterator<Map.Entry<String, String []>> entries = params.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String []> entry = entries.next();
				String[] vArr = entry.getValue();
				for (int i = 0; i < vArr.length; i++) {
					String key = entry.getKey();
					if (!"rows".equals(key) && !"page".equals(key)) {
						if (totalParams.equals("")) {
							totalParams = key + "=" + vArr[i];
						} else {
							totalParams += "&" + key + "="+ vArr[i];
						}
					}
				}
			}
		}
		if (StringUtils.hasText(totalParams)) {
			return url +"?"+totalParams+"&";
		}else{
			return url+"?";
		}
	}
}
