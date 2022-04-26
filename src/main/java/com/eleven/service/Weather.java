package com.eleven.service;


import com.eleven.bean.Temperature;
import com.google.gson.Gson;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @ClassName Temperature
 * @Description 爬取天气数据的爬虫
 * @Author ELeven
 * @Date 2022/4/19 21:08
 * @Version 1.0
 **/
public class Weather extends Father {

    public Weather() throws Exception {
        super("https://tianqi.2345.com/");
    }
    public Temperature getWeather(Elements elements){
        String[] lows = new String[8];
        String[] highs = new String[8];
        int i = 0;
        int maxLow = 100;
        int keyLow = 0;
        for(Element element : elements){
            //获取标签内文本数据  7~16°
            String temperature= element.html();
            //低温
            String low =  temperature.split("[~°]")[0];
            //高温
            String high = temperature.split("[~°]")[1];
            //Integer.parseInt(字符串数字)//极为重要！！！字符串数字转纯数字
            if(Integer.parseInt(low) < maxLow){
                maxLow = Integer.parseInt(low);
                keyLow = i;
            }
            lows[i] = low;
            highs[i] = high;
            i++;
        }
        Temperature t = new Temperature();
        //对象/数组 转JSON串
        t.lows = new Gson().toJson(lows);
        System.out.println(t.lows);
        t.highs = new Gson().toJson(highs);
        System.out.println(t.highs);
        //字符串数字 = 纯数字 + ""; //极为重要
        t.keyLow = keyLow + "";
        t.maxLow = maxLow + "";
        System.out.println("x--" + keyLow + ",y--" + maxLow);
        return t;
    }
    public String getTimes(Elements elements){
        String[] times = new String[8];
        int i = 0;
        for(Element element : elements){
            times[i] = element.html().trim();
            i++;
        }
        return new Gson().toJson(times);
    }

}
