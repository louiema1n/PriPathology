package com.example.louiemain.pripathology.utils;/**
 * @description
 * @author&date Created by louiemain on 2018/3/19 19:43
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * @Pragram: PrimaryPathological
 * @Type: Class
 * @Description: 自定义view-实现Button的Drawable与text一起居中效果
 * XML中需设置 includeFontPadding=false
 * @Author: louiemain
 * @Created: 2018/3/19 19:43
 **/
public class DrawableVerticalButton extends android.support.v7.widget.AppCompatButton {
    public DrawableVerticalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas = getTopCanvas(canvas);
        super.onDraw(canvas);
    }

    private Canvas getTopCanvas(Canvas canvas) {
        // 获取4个Drawable
        Drawable[] drawables = getCompoundDrawables();
        if (drawables == null) {
            return canvas;
        }
        Drawable drawable = drawables[1];// 上面的drawable
        if(drawable == null){
            drawable = drawables[3];// 下面的drawable
        }

        float textSize = getPaint().getTextSize();  // text的大小
        int drawHeight = drawable.getIntrinsicHeight(); // drawable的高
        int drawPadding = getCompoundDrawablePadding(); // padding
        float contentHeight = textSize + drawHeight + drawPadding;  // drawable+text+padding的高度
        int topPadding = (int) (getHeight() - contentHeight);   // 距离顶部的高度
        setPadding(0, topPadding , 0, 0);   // 将文字移动到图片边缘
        float dy = (contentHeight - getHeight())/2;
        // 将图片与文字一起移动到中央
        canvas.translate(0, dy);
        return canvas;
    }
}
