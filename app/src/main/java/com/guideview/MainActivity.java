package com.guideview;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guideview.style.CenterLeftStyle;
import com.guideview.style.LeftBottomStyle;
import com.guideview.style.RightBottomStyle;

public class MainActivity extends AppCompatActivity {
    private TextView tv_hello;
    private TextView tv_hello1;
    private TextView tv_hello2;
    private TextView tv_hello3;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tv_hello = findViewById(R.id.tv_hello);
        tv_hello1 = findViewById(R.id.tv_hello1);
        tv_hello2 = findViewById(R.id.tv_hello2);
        tv_hello3 = findViewById(R.id.tv_hello3);
        ll_content = findViewById(R.id.ll_content);
        ll_content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    ll_content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    ll_content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        new GuideViewHelper(MainActivity.this)
                .addView(R.id.tv_hello, new LeftBottomStyle(R.layout.layout_decor,10))
                .addView(tv_hello1, new CenterLeftStyle(R.layout.layout_decor))
                .addView(tv_hello2, new LeftBottomStyle(R.layout.layout_decor,10))
                .addView(tv_hello3, new RightBottomStyle(R.layout.layout_decor,10))
                .padding(0)
                .type(LightType.Rectangle)
                .onDismiss(new GuideView.OnDismissListener() {
                    @Override
                    public void dismiss() {
                        Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_LONG).show();
                    }
                })
                .postShow();
    }
}



