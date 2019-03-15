package com.example.administrator.weatherdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class SetBackground {

    //大雪
    public void getDx(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.dx);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //多云白天
    public void getDybt(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.dybt);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //多云夜晚
    public void getDyyw(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.dyyw);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //雷阵雨
    public void getLzy(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.lzy);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //默认
    public void getMr(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.mr);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //晴天白天
    public void getQtbt(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.qtbt);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //晴天夜晚
    public void getQtyw(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.qtyw);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //雨白天
    public void getYbt(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.ybt);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //雨夜晚
    public void getYyw(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.yyw);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //阵雪
    public void getZx(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.zx);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //阵雨
    public void getZy(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.zy);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //雾
    public void getW(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.w);
        layout.setBackgroundDrawable(btnDrawable);
    }

    //阴天
    public void getYt(Context context,Resources resources,Drawable btnDrawable,View layout){
        resources = context.getResources();
        btnDrawable = resources.getDrawable(R.drawable.yt);
        layout.setBackgroundDrawable(btnDrawable);
    }
}
