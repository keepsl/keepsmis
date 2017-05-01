package com.keeps.manage.Processor;

import com.keeps.model.SzlGoods;

import net.sf.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/** 
 * <p>Title: CouponPage.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年5月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class CouponPageProcessor implements PageProcessor{
	//private static String couponurl = "http://c.b0yp.com/h.gG7rjM?cv=pB9qZGbcYeR";
	//private static String couponurl = "https://uland.taobao.com/coupon/edetail?e=o3XANiibzvAN%2BoQUE6FNzE6SjBUOFfXEGTxHqY6W28cFPq0Q4ffppawf40y9QYKD%2FEp4SJRa4G5Q50PYIEZ8QEYUejVMpzGc7sRUcQe1PUcnxxW4GL1KxaNoq0mW3%2F30q%2BwIE%2FADZPLnfrOPkk8JP1CrRp9VEh4Z&pid=mm_116483405_23250213_77190087&af=1";
	//中转页URL
	private static String URL_ZHONGZHUAN = "c.b0yp.com";
	//领取优惠券URL
	private static String URL_YOUHUI = "uland.taobao.com/coupon/edetail";
	//优惠券数据URL
	private static String URL_YOUHUI_DATA = "uland.taobao.com/cp/coupon";
	public static SzlGoods goods = null;
	private Site site = Site.me().setCharset("UTF-8").setUserAgent("Spider").setTimeOut(3000).setRetryTimes(3).setSleepTime(5000); 
	@Override
	public Site getSite() {
		return this.site;
	}

	@Override
	public void process(Page page) {
		if (page.getUrl().regex(URL_ZHONGZHUAN).match()) {//中转连接
			//获取优惠券领取连接
			String str = page.getHtml().get();
			str = str.substring(str.indexOf("var url = '")+"var url = '".length());
			String url = str.substring(0,str.indexOf("';"));
            page.addTargetRequest(url);
		}else if (page.getUrl().regex(URL_YOUHUI).match()) {
			//获得数据连接
			page.addTargetRequest(page.getUrl().get().replace("coupon/edetail", "cp/coupon"));
		}else if (page.getUrl().regex(URL_YOUHUI_DATA).match()) {
			if ("true".equals(page.getJson().jsonPath("$.success").get())) {
				goods = new SzlGoods();
				JSONObject jsonObject = JSONObject.fromObject(page.getJson().jsonPath("$.result").get());
				//System.out.println(effectiveStartTime);
				goods.setStarttime(jsonObject.getString("effectiveStartTime"));//开始时间
				goods.setEndtime(jsonObject.getString("effectiveEndTime"));//结束时间
				goods.setCouponprice(Float.valueOf(jsonObject.getString("amount")));//优惠券价格
				JSONObject itemjsonObject = JSONObject.fromObject(jsonObject.get("item"));
				goods.setOriginalprice(Float.valueOf(itemjsonObject.getString("reservePrice")));//原价格，其实在系统中没用到
				goods.setCurrentprice(Float.valueOf(itemjsonObject.getString("discountPrice")));//现价格
				goods.setCouponafterprice(goods.getCurrentprice()-goods.getCouponprice());//折后价格
				goods.setGoodsname(itemjsonObject.getString("title"));//商品标题
				goods.setGoodsimageurl(itemjsonObject.getString("picUrl"));
				goods.setTolongurl(itemjsonObject.getString("clickUrl"));
				goods.setTocouponsurl(page.getUrl().get().replace("cp/coupon", "coupon/edetail"));
				goods.setItemid(Long.parseLong(itemjsonObject.getString("itemId")));
			}
		}
	}
	
	public static void main(String[] args) {
        System.out.println("========淘客助手爬虫【启动】喽！=========");  
        Spider.create(new CouponPageProcessor()).addUrl("http://c.b0yp.com/h.gG7rjM?cv=pB9qZGbcYeR")
        .thread(1)
        .run();  
        System.out.println("========淘客助手爬虫【结束】喽！=========");  
    }  

}
