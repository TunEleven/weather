package com.eleven.service;


import com.eleven.util.HttpConn;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * @ClassName Temperature
 * @Description 父类
 * @Author ELeven
 * @Date 2022/4/19 21:08
 * @Version 1.0
 **/
public abstract class Father {
    /**
     * Spider类成员属性  //用构造方法的到一个文档
     */
    public Document document;
    public Elements elements;

    /**
     * throws抛出Exception异常      //有参构造,传入url,得到html文档Document  //传入网址爬得标签数组
     * @param url 连接的网站地址
     */
    public Father(String url) throws Exception {
        //使用http请求,怕网站,能拿到什么?
        String html = HttpConn.get(url);
        //Jsoup解析html源码得到的网页文档
        document = Jsoup.parse(html);
    }

}
