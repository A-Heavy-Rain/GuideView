package com.guideview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaozhibo on 2017/10/31.
 */

public class GuideViewHelper {
    private ViewGroup rootView;
    private GuideView guideView;
    private Context context;
    private List<ViewInfo> viewInfos;
    private List<View> lightViews;
    private List<LayoutStyle> layoutStyles;
    private int padding;
    private LightType lightType = LightType.Rectangle;
    private int blurWidth;


    public GuideViewHelper(Activity activity) {
        this.rootView = (ViewGroup) activity.getWindow().getDecorView();
        this.context = activity;
        guideView = new GuideView(context);
        viewInfos = new ArrayList<>();
        lightViews = new ArrayList<>();
        layoutStyles = new ArrayList<>();
    }

    public GuideViewHelper(Fragment fragment) {
        this(fragment.getActivity());
    }

    /**
     *
     * @param padding 高亮区域内边距
     * @return
     */
    public GuideViewHelper padding(int padding) {
        this.padding = padding;
        return this;
    }



    public GuideViewHelper addView(View view, LayoutStyle layoutStyle) {
        lightViews.add(view);
        layoutStyles.add(layoutStyle);
        return this;
    }

    public GuideViewHelper addView(int viewId, LayoutStyle layoutStyle) {
        lightViews.add(rootView.findViewById(viewId));
        layoutStyles.add(layoutStyle);
        return this;
    }

    public GuideViewHelper type(LightType lightType) {
        this.lightType=lightType;
        guideView.type(lightType);
        return this;
    }

    public GuideViewHelper onDismiss(GuideView.OnDismissListener listener) {
        guideView.setOnDismissListener(listener);
        return this;
    }

    public GuideViewHelper Blur(int radius,int blurWidth) {
        this.blurWidth=blurWidth;
        guideView.setBlur(radius,blurWidth);
        return this;
    }
    public GuideViewHelper Blur() {
        this.blurWidth=15;
        guideView.setBlur(15,15);
        return this;
    }
    public void nextLight(){
        guideView.showHighLight();
    }

    public void show() {
        show(false);
    }
    public GuideViewHelper autoNext(){
        guideView.setOnClickListener(guideView);
        guideView.setAutoNext(true);
        return this;
    }
    public void postShow() {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                show();
            }
        });
    }

    public void showAll() {
        show(true);
    }

    public void postShowAll() {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                showAll();
            }
        });
    }
    private ViewInfo obtainViewInfo(View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        ViewInfo info = new ViewInfo();
        switch (lightType) {
            case Oval:
            case Rectangle:
                info.offsetX = loc[0] - padding;
                info.offsetY = loc[1] - padding;
                info.width = view.getWidth() + 2 * padding;
                info.height = view.getHeight() + 2 * padding;
                break;
            case Circle:
                int diameter = Math.max(view.getWidth() + 2 * padding, view.getHeight() + 2 * padding);
                info.width = diameter;
                info.height = diameter;
                info.offsetX = loc[0] - padding;
                info.offsetY = loc[1] - padding - (diameter / 2 - view.getHeight() / 2 - padding);
                break;
        }
        return info;
    }

    private void show(boolean showAll) {

        for (View lightView : lightViews) {
            viewInfos.add(obtainViewInfo(lightView));
        }
        guideView.setViewInfos(viewInfos);
        if (blurWidth!=0){
            for (LayoutStyle layoutStyle : layoutStyles) {
                layoutStyle.addBlurOffset(blurWidth);
            }
        }
        if (showAll) {
            guideView.showAll();
            for (int i = 0; i < layoutStyles.size(); i++) {
                layoutStyles.get(i).showDecorationOnScreen(viewInfos.get(i), guideView);
            }

        } else {
            layoutStyles.get(0).showDecorationOnScreen(viewInfos.get(0), guideView);
            guideView.setLayoutStyles(layoutStyles);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(guideView, params);
    }


}
