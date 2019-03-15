package com.example.administrator.weatherdemo;

import android.icu.util.ULocale;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/1/8 0008.
 */

public class GetUtil {
    String city = null, jsonUrl, result;
    URL url = null;
    InputStream is = null;

    public String getjson(String c) {
        try {

            city = URLEncoder.encode(c, "utf-8");
            jsonUrl = String.format("http://www.sojson.com/open/api/weather/json.shtml?city=%s", city);

            url = new URL(jsonUrl);
            //打开url对应的资源的输入流
            is = url.openStream();
            //获取资源
            result = IOUtils.toString(is, "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}