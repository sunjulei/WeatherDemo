package com.example.administrator.weatherdemo;

import android.util.Log;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2018/1/8 0008.
 */

public class GetData {

    //参数

    private static String result;

    private GetUtil getUtil;

    //当天
    private String city;
    private String todayTime;
    private String todayTem;
    private String todayType;
    private String todayHighTem;
    private String todayLowTem;
    private String notice;
    private String zhil;
    private String fx;
    private String fl;

    //明天
    private String tomTime;
    private String tomType;
    private String tomHighTem;
    private String tomLowTem;

    //后天
    private String afterTomTime;
    private String afterTomType;
    private String afterTomHighTem;
    private String afterTomLowTem;




    //初始化
    public  GetData(String c) {
        getUtil = new GetUtil();
        result = getUtil.getjson(c);
        isNull();
    }

    public String isNull(){
        return result;
    }

    //取forecast的今天
    public JSONObject getForecast() {
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray jsonArray = data.getJSONArray("forecast");
        JSONObject mydata = JSONObject.fromObject(jsonArray.getString(0));
        return mydata;
    }

    //取forecast的明天
    public JSONObject getForecast1() {
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray jsonArray = data.getJSONArray("forecast");
        JSONObject jsontodaytime = JSONObject.fromObject(jsonArray.getString(1));
        return jsontodaytime;
    }

    //取forecast的后天
    public JSONObject getForecast2() {
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray jsonArray = data.getJSONArray("forecast");
        JSONObject mydata = JSONObject.fromObject(jsonArray.getString(2));
        return mydata;
    }


    //获取城市
    public String getCity() {
        JSONObject jsonObject = JSONObject.fromObject(result);
        city = jsonObject.getString("city");
        return city;
    }

    //获取今天的信息
    public String getTodayTime() {

        JSONObject mydata = getForecast();
        todayTime = mydata.getString("date");
        return todayTime;
    }

    public String getTodayTem() {
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        todayTem = data.getString("wendu");
        return todayTem;
    }

    public String getTodayType() {
        JSONObject mydata = getForecast();
        todayType = mydata.getString("type");
        return todayType;
    }

    public String getTodayHighTem() {
        JSONObject mydata = getForecast();
        todayHighTem = mydata.getString("high");
        todayHighTem = todayHighTem.substring(3, todayHighTem.length() - 1);
        return todayHighTem;
    }

    public String getTodayLowTem() {
        JSONObject mydata = getForecast();
        todayLowTem = mydata.getString("low");
        todayLowTem = todayLowTem.substring(3);
        return todayLowTem;
    }

    public String getZhil() {
        JSONObject mydata = getForecast();
        zhil = mydata.getString("aqi");
        return zhil;
    }

    public String getFx() {
        JSONObject mydata = getForecast();
        fx = mydata.getString("fx");
        return fx;
    }

    public String getFl() {
        JSONObject mydata = getForecast();
        fl = mydata.getString("fl");
        return fl;
    }

    public String getNotice() {
        JSONObject mydata = getForecast();
        notice = mydata.getString("notice");
        return notice;
    }

    //获取明天的信息
    public String getTomTime() {
        JSONObject jsonObject = getForecast1();
        tomTime = jsonObject.getString("date");
        return tomTime;
    }

    public String getTomType() {
        JSONObject jsonObject = getForecast1();
        tomType = jsonObject.getString("type");
        return tomType;
    }

    public String getTomHighTem() {
        JSONObject jsonObject = getForecast1();
        tomHighTem = jsonObject.getString("high");
        tomHighTem = tomHighTem.substring(3, tomHighTem.length() - 1);
        return tomHighTem;
    }

    public String getTomLowTem() {
        JSONObject jsonObject = getForecast1();
        tomLowTem = jsonObject.getString("low");
        tomLowTem = tomLowTem.substring(3);
        return tomLowTem;
    }

    //获取后天的信息

    public String getAfterTomTime() {
        JSONObject jsonObject = getForecast2();
        afterTomTime = jsonObject.getString("date");
        return afterTomTime;
    }

    public String getAfterTomType() {
        JSONObject jsonObject = getForecast2();
        afterTomType = jsonObject.getString("type");
        return afterTomType;
    }

    public String getAfterTomHighTem() {
        JSONObject jsonObject = getForecast2();
        afterTomHighTem = jsonObject.getString("high");
        afterTomHighTem = afterTomHighTem.substring(3, afterTomHighTem.length() - 1);
        return afterTomHighTem;
    }

    public String getAfterTomLowTem() {
        JSONObject jsonObject = getForecast2();
        afterTomLowTem = jsonObject.getString("low");
        afterTomLowTem = afterTomLowTem.substring(3);
        return afterTomLowTem;
    }
}
