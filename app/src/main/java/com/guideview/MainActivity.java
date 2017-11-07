package com.guideview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guideview.style.CenterRightStyle;
import com.guideview.style.CenterTopStyle;
import com.guideview.style.LeftBottomStyle;
import com.guideview.style.RightTopStyle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_light1;
    private TextView tv_light2;
    private TextView tv_light3;
    private ImageView iv_light4;
    private GuideViewHelper helper;
    private Button btn_tip1;
    private Button btn_tip2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_light1 = findViewById(R.id.btn_light1);
        tv_light2 = findViewById(R.id.tv_light2);
        tv_light3 = findViewById(R.id.tv_light3);
        iv_light4 = findViewById(R.id.iv_light4);
        btn_tip1=findViewById(R.id.btn_tip1);
        btn_tip2=findViewById(R.id.btn_tip2);
        btn_tip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClickDecoToNext();
            }
        });
        btn_tip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickShowAll();
            }
        });
        showOnCreate();
    }


    @Override
    public void onClick(View v) {
        helper.nextLight();
    }

    /**
     * 在OnCreate方法中调用,此时View宽高没测量,若是一次全显示的话用postShowAll(),依次的话postShow();
     * 自动消失的话需加上autoDismiss();
     */
    private void showOnCreate(){

        View deco_view1 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view2 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view3 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view4 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);


        ((TextView)deco_view1.findViewById(R.id.tv_deco)).setText("右上布局");
        ((TextView)deco_view2.findViewById(R.id.tv_deco)).setText("正右方布局");
        ((TextView)deco_view3.findViewById(R.id.tv_deco)).setText("左下方布局");
        ((TextView)deco_view4.findViewById(R.id.tv_deco)).setText("正上方布局");

        //注意这里要是addView第一个参数传的是View
        // 一定注意LayoutInflater.from(this).inflate中第二个一定要传入个ViewGroup
        //为了保存XML中的LayoutParams
        helper = new GuideViewHelper(MainActivity.this)
                .addView(btn_light1, new RightTopStyle(deco_view1))
                .addView(tv_light2, new CenterRightStyle(deco_view2))
                .addView(tv_light3, new LeftBottomStyle(deco_view3, 10))
                .addView(iv_light4, new CenterTopStyle(deco_view4, 10))
                .type(LightType.Circle)
                .autoNext()
                .Blur()
                .onDismiss(new GuideView.OnDismissListener() {
                    @Override
                    public void dismiss() {
                        Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_LONG).show();
                    }
                });
        helper.postShow();

    }
    /**
     *自己控制显示下一个高亮
     */
    private void showClickDecoToNext(){

        View deco_view1 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view2 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view3 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view4 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);

        deco_view1.setOnClickListener(this);
        deco_view2.setOnClickListener(this);
        deco_view3.setOnClickListener(this);
        deco_view4.setOnClickListener(this);

        ((TextView)deco_view1.findViewById(R.id.tv_deco)).setText("右上布局\n点击下一个");
        ((TextView)deco_view2.findViewById(R.id.tv_deco)).setText("正右方布局\n点击下一个");
        ((TextView)deco_view3.findViewById(R.id.tv_deco)).setText("左下方布局\n点击下一个");
        ((TextView)deco_view4.findViewById(R.id.tv_deco)).setText("正上方布局\n点击下一个");

        //注意这里要是addView第一个参数传的是View
        // 一定注意LayoutInflater.from(this).inflate中第二个一定要传入个ViewGroup
        //为了保存XML中的LayoutParams
        helper = new GuideViewHelper(MainActivity.this)
                .addView(btn_light1, new RightTopStyle(deco_view1))
                .addView(tv_light2, new CenterRightStyle(deco_view2))
                .addView(tv_light3, new LeftBottomStyle(deco_view3, 10))
                .addView(iv_light4, new CenterTopStyle(deco_view4, 10))
                .type(LightType.Circle)
                .onDismiss(new GuideView.OnDismissListener() {
                    @Override
                    public void dismiss() {
                        Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_LONG).show();
                    }
                });
        helper.show();

    }

    private void clickShowAll(){

        View deco_view1 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view2 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view3 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);
        View deco_view4 = LayoutInflater.from(this).inflate(R.layout.layout_decor, (ViewGroup) getWindow().getDecorView(), false);

        ((TextView)deco_view1.findViewById(R.id.tv_deco)).setText("右上布局");
        ((TextView)deco_view2.findViewById(R.id.tv_deco)).setText("正右方布局");
        ((TextView)deco_view3.findViewById(R.id.tv_deco)).setText("左下方布局");
        ((TextView)deco_view4.findViewById(R.id.tv_deco)).setText("正上方布局");
        new GuideViewHelper(MainActivity.this)
                .addView(btn_light1, new RightTopStyle(deco_view1))
                .addView(tv_light2, new CenterRightStyle(deco_view2))
                .addView(tv_light3, new LeftBottomStyle(deco_view3, 10))
                .addView(iv_light4, new CenterTopStyle(deco_view4, 10))
                .type(LightType.Circle)
                .autoNext()
                .onDismiss(new GuideView.OnDismissListener() {
                    @Override
                    public void dismiss() {
                        Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_LONG).show();
                    }
                })
                .showAll();
    }
}



