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
 * 在高亮区域正左方
 */

public class CenterLeftStyle extends LayoutStyle {
    public CenterLeftStyle(int layoutRes, int offset) {
        super(layoutRes,offset);
    }

    public CenterLeftStyle(int layoutRes) {
        super(layoutRes);
    }

    @Override
    public void showDecorationOnScreen(final ViewInfo viewInfo, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(layoutRes, parent, false);
        parent.addView(view);
        view.setVisibility(View.INVISIBLE);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                FrameLayout.LayoutParams child_params = (FrameLayout.LayoutParams) view.getLayoutParams();
                child_params.leftMargin = viewInfo.offsetX -view.getWidth()-offset;
                child_params.topMargin = viewInfo.offsetY +(viewInfo.height - view.getHeight()) / 2;
                view.requestLayout();
                //多一次布局监听，防止view闪烁
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < 16) {
                            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        view.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}
