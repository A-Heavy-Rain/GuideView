package com.guideview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhaozhibo on 2017/11/6.
 */

public abstract class LayoutStyle {
    protected int offset;
    protected int layoutRes;
    protected View decoView;


    public LayoutStyle(int layoutRes, int offset) {
        this.offset = offset;
        this.layoutRes = layoutRes;
    }
    public LayoutStyle(View decoView, int offset) {
        this.offset = offset;
        this.decoView = decoView;
    }

    public LayoutStyle(int layoutRes) {
        this(layoutRes,0);
    }
    public LayoutStyle(View decoView) {
        this(decoView,0);
    }
    public void addBlurOffset(int blurWidth){
        offset+=blurWidth;
    }

    public abstract void showDecorationOnScreen(ViewInfo viewInfo, ViewGroup parent);
}
