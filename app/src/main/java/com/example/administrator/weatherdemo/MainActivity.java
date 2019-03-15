package com.example.administrator.weatherdemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.gesture.GestureUtils;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button refresh;
    Button setCity_tb;
    private static Boolean isRefresh = true;

    ImageView todayImage;
    ImageView tomImage;
    ImageView afterTomImage;
    ImageView gg;

    TextView city_tv;

    TextView todayTime_tv;
    TextView todayType_tv;
    TextView todayTem_tv;
    TextView todayNotice_tv;
    TextView zhil_tv;
    TextView fen_tv;

    TextView tomTime_tv;
    TextView tomType_tv;
    TextView tomTem_tv;

    TextView afterTomTime_tv;
    TextView afterTomType_tv;
    TextView afterTomTem_tv;

    String c;
    int i;

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

    Handler handler;
    private WebView webView;
    private View layout;
    private Resources resources;
    private Drawable btnDrawable;
    SetBackground setBg;
    private Date curDate;
    private int timeInt;
    private GetData getData;
    private TextView wendu;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bindView();

        setRefresh();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {

                    String err = msg.obj.toString();
                    String red = readCity();
                    city_tv.setTextColor(MainActivity.this.getResources().getColor(R.color.red));
                    todayType_tv.setTextColor(MainActivity.this.getResources().getColor(R.color.red));
                    wendu.setTextColor(MainActivity.this.getResources().getColor(R.color.red));

                    city_tv.setText(red);
                    todayTime_tv.setText(err);
                    todayType_tv.setText("请检查输入城市是否有误");
                    todayTem_tv.setText(err);
                    todayNotice_tv.setText(err);
                    zhil_tv.setText(err);
                    fen_tv.setText(err);
                    wendu.setText("获取失败");

                    tomTime_tv.setText(err);
                    tomType_tv.setText(err);
                    tomTem_tv.setText(err);

                    afterTomTime_tv.setText(err);
                    afterTomType_tv.setText(err);
                    afterTomTem_tv.setText(err);

                    //天气图片
                    setImage(todayImage, "未知");
                    setImage(tomImage, "未知");
                    setImage(afterTomImage, "未知");
                }
            }
        };


        //刷新
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRefresh();
                refreshGif();
                webView.setVisibility(View.VISIBLE);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        refresh.post(new Runnable() {
                            @Override
                            public void run() {
                                webView.setVisibility(View.GONE);
                                if (isRefresh) {
                                    Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "刷新太频繁，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }.start();


            }
        });

        //点击设置城市
        setCity_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCity();
            }
        });
        //点击设置城市
        city_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCity();
            }
        });


    }


    //绑定控件
    public void bindView() {

        wendu=findViewById(R.id.wendu);

        layout = findViewById(R.id.reLa);

        webView = findViewById(R.id.webView);

        todayImage = findViewById(R.id.todayImage);
        tomImage = findViewById(R.id.tomImage);
        afterTomImage = findViewById(R.id.afterTomImage);

        city_tv = findViewById(R.id.city);
        refresh = findViewById(R.id.refresh);
        setCity_tb = findViewById(R.id.setCity);

        todayTime_tv = findViewById(R.id.todayTime);
        todayType_tv = findViewById(R.id.todayType);
        todayTem_tv = findViewById(R.id.todaytem);
        todayNotice_tv = findViewById(R.id.todayNotice);
        zhil_tv=findViewById(R.id.zhil);
        fen_tv=findViewById(R.id.fen);

        tomTime_tv = findViewById(R.id.tomTime);
        tomType_tv = findViewById(R.id.tomType);
        tomTem_tv = findViewById(R.id.tomTem);

        afterTomTime_tv = findViewById(R.id.afterTomTime);
        afterTomType_tv = findViewById(R.id.afterTomType);
        afterTomTem_tv = findViewById(R.id.afterTomTem);
    }


    public void setRefresh() {
        //通过线程获取json资源
        new Thread() {
            @Override
            public void run() {
                getData = new GetData(readCity());
                i = getData.isNull().length();

//                    layout.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            istodayType();
//                        }
//                    });
                if (i < 55) {
                    String ERROR = "null";
                    Message mes = new Message();
                    mes.what = 0x123;
                    mes.obj = ERROR;
                    handler.sendMessage(mes);

                    isRefresh = true;
                } else if (i == 55) {
                    isRefresh = false;
                } else {
                    city = getData.getCity();
                    todayTime = getData.getTodayTime();
                    todayTem = getData.getTodayTem();
                    todayType = getData.getTodayType();
                    todayHighTem = getData.getTodayHighTem();
                    todayLowTem = getData.getTodayLowTem();
                    notice = getData.getNotice();
                    zhil=getData.getZhil();
                    fx=getData.getFx();
                    fl=getData.getFl();

                    tomTime = getData.getTomTime();
                    tomType = getData.getTomType();
                    tomHighTem = getData.getTomHighTem();
                    tomLowTem = getData.getTomLowTem();

                    afterTomTime = getData.getAfterTomTime();
                    afterTomType = getData.getAfterTomType();
                    afterTomHighTem = getData.getAfterTomHighTem();
                    afterTomLowTem = getData.getAfterTomLowTem();


                    city_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            city_tv.setTextColor(MainActivity.this.getResources().getColor(R.color.cityColor));
                            city_tv.setText(city);
                        }
                    });
                    //关于今天的控件
                    todayTime_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            todayTime_tv.setText(todayTime);
                        }
                    });

                    todayType_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            todayType_tv.setText(todayType);
                        }
                    });

                    todayTem_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            wendu.setTextColor(MainActivity.this.getResources().getColor(R.color.whil));
                            todayType_tv.setTextColor(MainActivity.this.getResources().getColor(R.color.whil));
                            todayTem_tv.setText(todayHighTem + "~" + todayLowTem);
                            zhil_tv.setText("空气质量指数："+zhil+"\n越高污染越严重"+"\n(参考范围：0~300)");
                            fen_tv.setText(fx+"("+fl+")");
                        }
                    });

                    todayNotice_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            todayNotice_tv.setText(notice);
                        }
                    });

                    wendu.post(new Runnable() {
                        @Override
                        public void run() {
                            wendu.setText("   "+todayTem+"℃");
                        }
                    });

                    //关于明天的控件
                    tomTime_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            tomTime_tv.setText(tomTime);
                        }
                    });

                    tomType_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            tomType_tv.setText(tomType);
                        }
                    });

                    tomTem_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            tomTem_tv.setText(tomHighTem + "~" + tomLowTem);
                        }
                    });

                    //关于后天的控件
                    afterTomTime_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            afterTomTime_tv.setText(afterTomTime);
                        }
                    });

                    afterTomType_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            afterTomType_tv.setText(afterTomType);
                        }
                    });

                    afterTomTem_tv.post(new Runnable() {
                        @Override
                        public void run() {
                            afterTomTem_tv.setText(afterTomHighTem + "~" + afterTomLowTem);
                        }
                    });


                    //调用天气图片
                    todayImage.post(new Runnable() {
                        @Override
                        public void run() {
                            setImage(todayImage, todayType);
                        }
                    });

                    tomImage.post(new Runnable() {
                        @Override
                        public void run() {
                            setImage(tomImage, tomType);
                        }
                    });

                    afterTomImage.post(new Runnable() {
                        @Override
                        public void run() {
                            setImage(afterTomImage, afterTomType);
                        }
                    });


                    layout.post(new Runnable() {
                        @Override
                        public void run() {
                            istodayType();
                        }
                    });
                    isRefresh = true;
                }

            }
        }.start();
    }

    public void setCity() {
        final EditText et;
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("请输入城市名称！")
                .setIcon(R.drawable.cityjpg)
                .setView(et = new EditText(MainActivity.this))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        c = et.getText().toString().trim();
                        //保存输入的城市名到"share"
                        saveCity(c);

                        city_tv.setText(readCity());

                        setRefresh();

                        Toast.makeText(MainActivity.this, "已将城市修改为:\n            " + readCity(), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    //存储城市
    public void saveCity(String city) {
        // 指定操作的文件名称
        SharedPreferences share = getSharedPreferences("myCity", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); // 编辑文件
        edit.putString("name", city);      // 根据键值对添加数据
        edit.commit();  // 保存数据信息
    }

    //获取城市
    public String readCity() {
        // 指定操作的文件名称
        SharedPreferences share = getSharedPreferences("myCity", MODE_PRIVATE);
        return share.getString("name", "");
    }



    public void refreshGif() {
        webView.loadDataWithBaseURL(null,
                "<HTML>" +
                            "<body >" +
                                  "<div>" +
                                      "<IMG  src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515688781019&di=453166f4da7d61abf26dd83a39450172&imgtype=0&src=http%3A%2F%2Fimglf0.ph.126.net%2FPMnUVYd49UWsXwAHK6yibw%3D%3D%2F2081507452875505026.gif'/>" +
                                  "</div>" +
                                  "<center>刷新中……</center>"+
                             "</body>" +
                        "</html>",
                "text/html", "UTF-8", null);
    }

    //得到系统时间
    public int getSysTime() {
        curDate = new Date(System.currentTimeMillis());
        timeInt = curDate.getHours();
        return timeInt;
    }


    //判断背景图
    public void istodayType(){
        String type=todayType;
        setBg=new SetBackground();
        if (getSysTime() < 18) {
            //判断天气类型后设置背景
            if (type.equals("小雪")) {
                setBg.getZx(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("多云")) {
                setBg.getDybt(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("雷阵雨")) {
                setBg.getLzy(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("晴")) {
                setBg.getQtbt(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("少云")) {
                setBg.getDybt(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("雾")) {
                setBg.getW(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("小雨")) {
                setBg.getYbt(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("阴")) {
                setBg.getYt(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("阵雨")) {
                setBg.getZy(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("阵雪")) {
                setBg.getZx(MainActivity.this, resources, btnDrawable, layout);
            } else {
                setBg.getMr(MainActivity.this, resources, btnDrawable, layout);
            }
        }else {
            if (type.equals("小雪")) {
                setBg.getZx(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("多云")) {
                setBg.getDyyw(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("雷阵雨")) {
                setBg.getLzy(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("晴")) {
                setBg.getQtyw(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("少云")) {
                setBg.getDyyw(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("雾")) {
                setBg.getW(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("小雨")) {
                setBg.getYyw(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("阴")) {
                setBg.getYt(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("阵雨")) {
                setBg.getZy(MainActivity.this, resources, btnDrawable, layout);
            } else if (type.equals("阵雪")) {
                setBg.getZx(MainActivity.this, resources, btnDrawable, layout);
            } else {
                setBg.getMr(MainActivity.this, resources, btnDrawable, layout);
            }
        }
    }

    //判断天气图
    private void setImage(ImageView image, String type) {
        if (getSysTime() < 18) {

            if (type.equals("小雪")) {
                image.setImageResource(R.drawable.xiaoxue_bt);
            } else if (type.equals("多云")) {
                image.setImageResource(R.drawable.duoyun_bt);
            } else if (type.equals("雷阵雨")) {
                image.setImageResource(R.drawable.leizyu_bt);
            } else if (type.equals("晴")) {
                image.setImageResource(R.drawable.qin_bt);
            } else if (type.equals("少云")) {
                image.setImageResource(R.drawable.shaoyun_bt);
            } else if (type.equals("雾")) {
                image.setImageResource(R.drawable.wuai);
            } else if (type.equals("小雨")) {
                image.setImageResource(R.drawable.xiaoyu_bt);
            } else if (type.equals("阴")) {
                image.setImageResource(R.drawable.yintian_bt);
            } else if (type.equals("阵雨")) {
                image.setImageResource(R.drawable.zhenyu_bt);
            } else if (type.equals("阵雪")) {
                image.setImageResource(R.drawable.xiaoxue_bt);
            } else {
                image.setImageResource(R.drawable.weizhi);
            }

        } else {
            if (type.equals("小雪")) {
                image.setImageResource(R.drawable.xiaoxue_yw);
            } else if (type.equals("多云")) {
                image.setImageResource(R.drawable.duoyun_yw);
            } else if (type.equals("雷阵雨")) {
                image.setImageResource(R.drawable.leizyu_yw);
            } else if (type.equals("晴")) {
                image.setImageResource(R.drawable.qin_yw);
            } else if (type.equals("少云")) {
                image.setImageResource(R.drawable.shaoyun_yw);
            } else if (type.equals("雾")) {
                image.setImageResource(R.drawable.wuai);
            } else if (type.equals("小雨")) {
                image.setImageResource(R.drawable.xiaoyu_yw);
            } else if (type.equals("阴")) {
                image.setImageResource(R.drawable.yintian_yw);
            } else if (type.equals("阵雨")) {
                image.setImageResource(R.drawable.zhenyu_yw);
            } else if (type.equals("阵雪")) {
                image.setImageResource(R.drawable.xiaoxue_yw);
            } else {
                image.setImageResource(R.drawable.weizhi);
            }


        }

    }

}

