package com.keeps.tools.utils.velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.html.HtmlCreator;

/**
 * <p>
 * Title: VelocityStaticView.java
 * </p>
 * <p>
 * Description: @TODO
 * </p>
 * <p>
 * Copyright: Copyright (c) KEEPS
 * </p>
 * 
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日 修改日期： 修改人： 复审人：
 */
public class VelocityStaticView extends VelocityLayoutView {
	public static String STATIC_HTML = "STATIC_HTML";

	public static String MAIN_PAGE_INDEX = "index.html";

	private static HtmlCreator creator = null;

	public static void setHtmlCreator(HtmlCreator creator) {
		creator = creator;
	}

	protected void renderScreenContent(Context velocityContext) throws Exception {
		StringWriter sw = new StringWriter();
		Template screenContentTemplate = getTemplate();
		screenContentTemplate.merge(velocityContext, sw);
		velocityContext.put("screen_content", sw.toString());
	}

	protected void doRender2(Context context, Writer write) throws Exception {
		renderScreenContent(context);
		mergeTemplate2Write(context, write);
	}

	protected void mergeTemplate2Write(Context context, Writer write) throws Exception {
		String layoutUrlToUse = (String) context.get("layout");
		Template template = getTemplate(layoutUrlToUse);
		template.merge(context, write);
	}

	private void createHTML(Context velocityContext, HttpServletRequest request, HttpServletResponse response) {
		String basePath = request.getSession().getServletContext().getRealPath("/");
		String requestHTML = requestHTML(request);

		String htmlPath = basePath + requestHTML;
		File htmlFile = new File(htmlPath);
		if (!(htmlFile.getParentFile().exists())) {
			htmlFile.getParentFile().mkdirs();
		}
		if (!(htmlFile.exists())) {
			Writer write = null;
			try {
				htmlFile.createNewFile();
				write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
				doRender2(velocityContext, write);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (write != null)
					try {
						write.flush();
						write.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		try {
			StringBuffer urlstring = new StringBuffer();
			urlstring.append(request.getContextPath());
			urlstring.append(requestHTML);
			if (StringUtils.hasText(request.getQueryString())) {
				urlstring.append("?");
				urlstring.append(request.getQueryString());
			}
			response.sendRedirect(urlstring.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String requestHTML(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		requestURI = requestURI.replaceFirst(contextPath, "");

		if (requestURI.endsWith("/"))
			requestURI = requestURI + MAIN_PAGE_INDEX;
		else {
			requestURI = requestURI + "/" + MAIN_PAGE_INDEX;
		}

		return requestURI;
	}
}
