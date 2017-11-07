package com.guideview.style;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.guideview.LayoutStyle;
import com.guideview.ViewInfo;

/**
 * Created by zhaozhibo on 2017/11/6.
 * 在高亮区域正右方
 */

public class CenterRightStyle extends LayoutStyle {
    public CenterRightStyle(int layoutRes,int offset) {
        super(layoutRes,offset);
    }

    public CenterRightStyle(int layoutRes) {
        super(layoutRes);
    }

    public CenterRightStyle(View decoView, int offset) {
        super(decoView, offset);
    }

    public CenterRightStyle(View decoView) {
        super(decoView);
    }

    @Override

    public void showDecorationOnScreen(final ViewInfo viewInfo, ViewGroup parent) {
        if (decoView==null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            decoView = inflater.inflate(layoutRes, parent, false);
        }
        parent.addView(decoView);
        decoView.setVisibility(View.INVISIBLE);
        decoView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    decoView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    decoView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                FrameLayout.LayoutParams child_params = (FrameLayout.LayoutParams) decoView.getLayoutParams();
                child_params.leftMargin = viewInfo.offsetX + viewInfo.width+offset;
                child_params.topMargin = viewInfo.offsetY +(viewInfo.height - decoView.getHeight()) / 2;
                decoView.requestLayout();
                //多一次布局监听，防止view闪烁
                decoView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < 16) {
                            decoView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            decoView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        decoView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}
