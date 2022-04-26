package com.eleven;


import com.eleven.bean.Temperature;
import com.eleven.service.Weather;
import com.eleven.util.FileUtil;


public class Test {
	public static void main(String[] args) {
		//Spider spider = null;
		try {

			/**
			 * 天气蜘蛛
			 */
			Weather weather = new Weather();
			weather.elements = weather.document.getElementsByClass("banner-right-con-list-temp");
			Temperature t =  weather.getWeather(weather.elements);
			weather.elements = weather.document.getElementsByClass("banner-right-con-list-time");
			t.times = weather.getTimes(weather.elements);
			String text = FileUtil.read("D:\\IdeaProjects\\Eleven\\project\\weather\\src\\main\\java\\com\\eleven\\web\\天气折线图模板.html");
			text = text.replace("${星期几}", t.times)
					.replace("${最高气温}",t.highs)
					.replace("${最低气温}",t.lows)
					.replace("${最低温y轴}",t.maxLow )
					.replace("${最低温x轴}", t.keyLow);

			FileUtil.write("D:\\IdeaProjects\\Eleven\\project\\weather\\src\\main\\java\\com\\eleven\\web\\天气折线图.html",text);

		} catch (Exception e) {
			System.out.println("网不行,或对方网站挂了");
			e.printStackTrace();
		}
	}
}