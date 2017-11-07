package com.guideview;

import android.view.ViewGroup;

/**
 * Created by zhaozhibo on 2017/11/6.
 */

public abstract class LayoutStyle {
    protected int offset;
    protected int layoutRes;


    public LayoutStyle(int layoutRes, int offset) {
        this.offset = offset;
        this.layoutRes = layoutRes;
    }

    public LayoutStyle(int layoutRes) {
        this.layoutRes = layoutRes;
    }
    public void addBlurOffset(int blurWidth){
        offset+=blurWidth;
    }

    public abstract void showDecorationOnScreen(ViewInfo viewInfo, ViewGroup parent);
}
