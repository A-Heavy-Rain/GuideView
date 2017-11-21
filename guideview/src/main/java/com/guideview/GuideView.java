package com.guideview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by zhaozhibo on 2017/10/31.
 */

public class GuideView extends FrameLayout implements View.OnClickListener {
    private Paint maskPaint;
    private int maskColor;
    private boolean isShowAll;
    private int currentPos;
    private LightType lightType;
    private int radius;
    private boolean isAutoNext;
    private PorterDuffXfermode porterDuffXfermode;
    private BlurMaskFilter blurMaskFilter;
    private List<ViewInfo> viewInfos;
    private List<LayoutStyle> layoutStyles;

    public GuideView(@NonNull Context context) {
        this(context, null);
    }

    public GuideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAlpha(int alpha) {
        maskColor = Color.argb(alpha, 0, 0, 0);
    }

    public void setBlur(int radius) {
        this.radius = radius;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        blurMaskFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER);
    }

    public void setAutoNext(boolean autoNext) {
        isAutoNext = autoNext;
    }

    public void type(LightType type) {
        lightType = type;
    }

    public void showAll() {
        isShowAll = true;
    }


    public void setViewInfos(List<ViewInfo> viewInfos) {
        this.viewInfos = viewInfos;
    }

    public void setLayoutStyles(List<LayoutStyle> styles) {
        this.layoutStyles = styles;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(maskColor);
        if (isShowAll) {
            for (int i = 0; i < viewInfos.size(); i++) {
                ViewInfo viewInfo = viewInfos.get(i);
                drawBlackRegion(canvas, viewInfo);
            }
            maskPaint.setXfermode(porterDuffXfermode);
            for (int i = 0; i < viewInfos.size(); i++) {
                ViewInfo viewInfo = viewInfos.get(i);
                drawHighLight(canvas, viewInfo);
            }
        } else {
            ViewInfo viewInfo = viewInfos.get(currentPos);
            drawBlackRegion(canvas, viewInfo);
            maskPaint.setXfermode(porterDuffXfermode);
            drawHighLight(canvas, viewInfo);
        }
        maskPaint.setXfermode(null);
        canvas.restoreToCount(saved);
        if (radius > 0) {
            maskPaint.setMaskFilter(blurMaskFilter);
            if (isShowAll) {
                for (int i = 0; i < viewInfos.size(); i++) {
                    ViewInfo viewInfo = viewInfos.get(i);
                    drawBlur(canvas, viewInfo);
                }
            } else {
                ViewInfo viewInfo = viewInfos.get(currentPos);
                drawBlur(canvas, viewInfo);
            }
            maskPaint.setMaskFilter(null);
        }
    }
    private void init() {
        maskPaint = new Paint();
        maskPaint.setColor(Color.WHITE);
        maskPaint.setStyle(Paint.Style.FILL);
        maskPaint.setAntiAlias(true);
        lightType = LightType.Rectangle;
        maskColor = Color.argb(0xCC, 0, 0, 0);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        setWillNotDraw(false);
        setClickable(true);
    }

    private void drawBlur(Canvas srcCanvas, ViewInfo viewInfo) {
        switch (lightType) {
            case Rectangle:
                srcCanvas.drawRect(viewInfo.offsetX, viewInfo.offsetY, viewInfo.offsetX + viewInfo.width, viewInfo.offsetY + viewInfo.height, maskPaint);
                break;
            case Circle:
                srcCanvas.drawCircle(viewInfo.offsetX + viewInfo.width / 2, viewInfo.offsetY + viewInfo.width / 2, viewInfo.width / 2, maskPaint);
                break;
            case Oval:
                srcCanvas.drawOval(new RectF(viewInfo.offsetX, viewInfo.offsetY, viewInfo.offsetX + viewInfo.width, viewInfo.offsetY + viewInfo.height), maskPaint);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isAutoNext) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    private void drawBlackRegion(Canvas srcCanvas, ViewInfo viewInfo) {
        switch (lightType) {
            case Rectangle:
                srcCanvas.drawRect(viewInfo.offsetX, viewInfo.offsetY, viewInfo.offsetX + viewInfo.width, viewInfo.offsetY + viewInfo.height, maskPaint);
                break;
            case Circle:
                srcCanvas.drawCircle(viewInfo.offsetX + viewInfo.width / 2, viewInfo.offsetY + viewInfo.width / 2, viewInfo.width / 2, maskPaint);
                break;
            case Oval:
                srcCanvas.drawOval(new RectF(viewInfo.offsetX, viewInfo.offsetY, viewInfo.offsetX + viewInfo.width, viewInfo.offsetY + viewInfo.height), maskPaint);
                break;
            default:
                break;
        }
    }

    private void drawHighLight(Canvas srcCanvas, ViewInfo viewInfo) {
        drawBlackRegion(srcCanvas, viewInfo);
    }

    public interface OnDismissListener {
        void dismiss();
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.listener = listener;
    }

    private OnDismissListener listener;

    @Override
    public void onClick(View v) {
        showHighLight();
    }

    public void showHighLight() {
        if (isShowAll || currentPos == viewInfos.size() - 1) {
            ((ViewGroup) this.getParent()).removeView(this);
            if (listener != null) {
                listener.dismiss();
            }
        } else {
            this.removeAllViews();
            currentPos++;
            layoutStyles.get(currentPos).showDecorationOnScreen(viewInfos.get(currentPos), this);
        }
    }
}
