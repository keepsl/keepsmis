package com.keeps.webservice.utils;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;

/** 
 * <p>Title: CrawlerUtil.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月17日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class CrawlerUtil {
    /** 
     * 功能描述：根据xpath查询符合条件列表 
     */  
   public final static List<String> getLinksList(Page page, String xpath) {  
       return page.getHtml().xpath(xpath).links().all();  
   }  
 
   /** 
    * 功能描述：根据xpath查询值 
    */  
   public final static String getValue(Page page, String xpath) {  
       return page.getHtml().xpath(xpath).get();  
   }  
 
   /** 
    * 功能描述：是否是主页面 
    */  
   public static boolean isMainPage(Page page) {  
       if (TaobaoConstants.MAINPAGELIST.contains(page.getUrl().toString())) {  
           return true;  
       }  
       return false;  
   }  
 
   /** 
    * 功能描述：返回商品类型 
    */  
   public static int goodsType(Page page) {  
       int type = 0;  
       switch (page.getUrl().toString()) {  
       case TaobaoConstants.NVZHUANG : type =  1; break;  
       case TaobaoConstants.NANZHUANG : type =  2; break;  
       case TaobaoConstants.XIEBAO : type =  3; break;  
       case TaobaoConstants.JIAJU : type =  5; break;  
       case TaobaoConstants.WENTI : type =  6; break;  
       case TaobaoConstants.DIANQI : type =  7; break;  
       case TaobaoConstants.PEISHI : type =  8; break;  
       case TaobaoConstants.SHUMA : type =  9; break;  
       case TaobaoConstants.MEISHI : type =  10; break;  
       case TaobaoConstants.MEIZHUANG : type =  11; break;  
       case TaobaoConstants.MUYING : type =  12; break;  
       }  
       return type;  
   }  
 
   /** 
    * 功能描述：返回分页链接 
    */  
   public static List<String> getPageUrls(int lastPageNo, String lastPageUrl) {  
       List<String> list = new ArrayList<>();  
       String head = lastPageUrl.substring(0,lastPageUrl.indexOf("page=")+5);  
       String tail = lastPageUrl.substring(lastPageUrl.lastIndexOf("#new"));  
       for (int i=1 ; i<=lastPageNo; i++) {  
           list.add(head + i + tail);  //拼出所有分页链接  
       }  
       return list;  
   }  
 
   /** 
    * 功能描述：最后一页链接 
    */  
   public static String getLastPageUrl(Page page, String regex) {  
       List<String> urls = CrawlerUtil.getLinksList(page, regex);  
       return urls.get(urls.size() - 2);  
   }  
 
   /** 
    * 功能描述：最后一页号 
    */  
   public static int getLastPageNo(String lastPageUrl) {  
       String pageNo = lastPageUrl.substring(lastPageUrl.lastIndexOf("page=")+5,lastPageUrl.lastIndexOf("#new"));  
       return Integer.parseInt(pageNo);  
   }  
}
