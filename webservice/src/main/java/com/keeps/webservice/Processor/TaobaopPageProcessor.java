package com.keeps.webservice.Processor;

import com.keeps.webservice.utils.CrawlerUtil;
import com.keeps.webservice.utils.TaobaoConstants;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/** 
 * <p>Title: TaobaopPageProcessor.java</p>  
 * <p>Description: 淘宝数据处理 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月17日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class TaobaopPageProcessor implements PageProcessor{
	
	private Site site = Site.me().setCharset("UTF-8").setUserAgent("Spider").setTimeOut(3000).setRetryTimes(3).setSleepTime(5000); 
	//计数器  
    private static int count = 0; 
    
    private static int type = 0;
	@Override
	public Site getSite() {
		return this.site;
	}

	@Override
	public void process(Page page) {
		//首页    （抓商品链接 、分页链接）  
        //分页     （抓商品链接）  
        //商品细览页  （抓数据）  
        if (CrawlerUtil.isMainPage(page)) { //首页处理  
            //1.查看商品类别  
            type = CrawlerUtil.goodsType(page);  
            //2.将商品图片链接加入到待抓取列表中  
            page.addTargetRequests(page.getHtml().xpath(TaobaoConstants.GOODSLISTRULE).links().all()); //goodslist  
            //3.将分页信息加入到待抓取列表中  
            String lastPageUrl = CrawlerUtil.getLastPageUrl(page, TaobaoConstants.LASTPAGEURL);  
            int lastPageNo = CrawlerUtil.getLastPageNo(lastPageUrl);  
            page.addTargetRequests(CrawlerUtil.getPageUrls(lastPageNo, lastPageUrl));  
        } else if (page.getUrl().regex(TaobaoConstants.PAGEREGEX).match()){  
            //将商品图片链接加入到待抓取列表中  
            page.addTargetRequests(page.getHtml().xpath(TaobaoConstants.GOODSLISTRULE).links().all()); //goodslist  
        } else {  
            //正文爬取  
            count ++;  
            String title = CrawlerUtil.getValue(page, TaobaoConstants.TITLE); //标题  
            String picture = CrawlerUtil.getValue(page, TaobaoConstants.PICTRURE);  
            String describe = CrawlerUtil.getValue(page, TaobaoConstants.DESCRIBE); //商品描述  
            String quanhoujia = CrawlerUtil.getValue(page, TaobaoConstants.QUANHOUJIA); //券后价  
            String zaishoujia = CrawlerUtil.getValue(page, TaobaoConstants.ZAISHOUJIA); //在售价  
            String coupon = CrawlerUtil.getValue(page, TaobaoConstants.YOUHUIQUAN); //优惠券金额  
            String couponRemark = CrawlerUtil.getValue(page, TaobaoConstants.YOUHUIQUANBEIZHU); //优惠券说明  
            String commission = CrawlerUtil.getValue(page, TaobaoConstants.YONGJIN); //佣金  
            String wenan = CrawlerUtil.getValue(page, TaobaoConstants.WENAN); //文案  
            String pcyouhuihref = CrawlerUtil.getValue(page, TaobaoConstants.PCYOUHUIHREF); //PC领取优惠券入口  
            String phoneyouhuihref = CrawlerUtil.getValue(page, TaobaoConstants.PHONEYOUHUIHREF); //手机领取优惠券入口  
            String goodshref = CrawlerUtil.getValue(page, TaobaoConstants.GOODSHREF); //商品链接  
            System.out.println(title);
            System.out.println(picture);
            System.out.println(commission);
        }  
	}
	public static void main(String[] args) {  
        long beginTime ,endTime;  
        System.out.println("========淘客助手爬虫【启动】喽！=========");  
        Spider.create(new TaobaopPageProcessor()).addUrl(TaobaoConstants.NVZHUANG)
        .thread(5)
        .run();  
        System.out.println(String.format("共抓取%s条数据", count));  
        System.out.println("========淘客助手爬虫【结束】喽！=========");  
    }  
}
