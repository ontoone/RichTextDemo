package com.mansoul.richtextdemo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView text1, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);

        //1.让文本和图片一起显示
        text1.setText(showTextWithImage("我是文本[大兵]", R.drawable.ic_launcher));

        //2.让某段文字变色
        text2.setText(showTextWithColor("王二,小明,大兵等觉得很赞", Color.BLUE));

        //3.让某段文字可以被点击并跳转超链接
        String text = "详情请点击<a href='http://www.baidu.com'>百度</a>";
        Spanned spanned = Html.fromHtml(text);
        text3.setText(spanned);
        text3.setMovementMethod(LinkMovementMethod.getInstance());//设置可以点击超链接

        //3.让某段文字可以被点击并自定义点击的逻辑操作
        String string = "王二,小明,大兵等觉得很赞";
        SpannableString ss = new SpannableString(string);
        MyUrlSpan urlSpan = new MyUrlSpan(string.substring(0, string.indexOf(",")));
        ss.setSpan(urlSpan, 0, 2, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        text4.setText(ss);
        text4.setMovementMethod(LinkMovementMethod.getInstance());

    }


    /**
     * 让某几个文字显示颜色
     *
     * @param string
     * @param color
     * @return
     */
    private CharSequence showTextWithColor(String string, int color) {
        SpannableString ss = new SpannableString(string);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        int end = string.indexOf("等");
        ss.setSpan(colorSpan, 0, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 让图片和文字一起显示
     *
     * @param text
     * @param imageRes
     * @return
     */
    private SpannableString showTextWithImage(String text, int imageRes) {
        SpannableString ss = new SpannableString(text);
        Drawable drawable = getResources().getDrawable(imageRes);

        //设置边界
//		drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, 20, 20);
        ImageSpan span = new ImageSpan(drawable);

        int start = text.indexOf("[");
        int end = text.indexOf("]") + 1;
        ss.setSpan(span, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE); //包含头不包含尾

        return ss;
    }

    class MyUrlSpan extends URLSpan {
        public MyUrlSpan(String url) {
            super(url);
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(MainActivity.this, getURL(), Toast.LENGTH_SHORT).show();
            widget.clearFocus();
        }

        //可以更改文本颜色
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.GREEN);
            ds.setUnderlineText(true); //下划线
        }
    }
}