package com.guideview;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaozhibo on 2017/10/31.
 */

public class GuideView extends FrameLayout implements View.OnClickListener {
    private Paint maskPaint;
    private List<Bitmap> desBitmaps;
    private Canvas desCanvas;
    private int maskColor;
    private boolean isShowAll;
    private int bitmapPos;
    private LightType lightType;

    private int blurWidth;
    private boolean isAutoNext;


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

    private void init() {
        maskPaint = new Paint();
        maskPaint.setColor(Color.WHITE);
        maskPaint.setStyle(Paint.Style.FILL);
        maskPaint.setAntiAlias(true);
        lightType=LightType.Rectangle;
        maskColor = 0xCC000000;
        setWillNotDraw(false);
        desCanvas = new Canvas();
    }

    public void setBlur(int radius, int blurWidth) {
        this.blurWidth = blurWidth;
        maskPaint.setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID));
    }

    public void setAutoNext(boolean autoNext) {
        isAutoNext = autoNext;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void type(LightType type) {
        lightType = type;
    }

    public void showAll() {
        isShowAll = true;
    }


    public void setViewInfos(List<ViewInfo> viewInfos) {
        Log.i("blurWidth", blurWidth + "");
        this.viewInfos = viewInfos;
        if (desBitmaps == null) {
            desBitmaps = new ArrayList<>();
        }
        for (ViewInfo viewInfo : viewInfos) {
            switch (lightType) {
                case Rectangle:
                    desBitmaps.add(Bitmap.createBitmap(viewInfo.width + 2 * blurWidth, viewInfo.height + 2 * blurWidth, Bitmap.Config.ARGB_8888));
                    break;
                case Circle:
                    int diameter = Math.max(viewInfo.width, viewInfo.height) + 2 * blurWidth;
                    desBitmaps.add(Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888));
                    break;
                case Oval:
                    desBitmaps.add(Bitmap.createBitmap(viewInfo.width + 2 * blurWidth, viewInfo.height + 2 * blurWidth, Bitmap.Config.ARGB_8888));
                    break;
                default:
                    break;
            }
        }
    }

    public void setLayoutStyles(List<LayoutStyle> styles) {
        this.layoutStyles = styles;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR)); // 设置 Xfermode
        canvas.drawColor(maskColor);
        if (isShowAll) {
            for (int i = 0; i < desBitmaps.size(); i++) {
                ViewInfo viewInfo = viewInfos.get(i);
                Bitmap bitmap = desBitmaps.get(i);
                bitmap.eraseColor(Color.TRANSPARENT);
                drawHighLight(canvas, bitmap, viewInfo);
            }
        } else {
            ViewInfo viewInfo = viewInfos.get(bitmapPos);
            Bitmap bitmap = desBitmaps.get(bitmapPos);
            bitmap.eraseColor(Color.TRANSPARENT);
            drawHighLight(canvas, bitmap, viewInfo);
        }
        maskPaint.setXfermode(null); // 用完及时清除 Xfermode
        canvas.restoreToCount(saved);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isAutoNext) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    private void drawHighLight(Canvas srcCanvas, Bitmap desBitmap, ViewInfo viewInfo) {
        switch (lightType) {
            case Rectangle:
                desCanvas.setBitmap(desBitmap);
                desCanvas.drawRect(blurWidth, blurWidth, viewInfo.width + blurWidth, viewInfo.height + blurWidth, maskPaint);
                srcCanvas.drawBitmap(desBitmap, viewInfo.offsetX - blurWidth, viewInfo.offsetY - blurWidth, maskPaint);
//                加圆角的话可以在这
//                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//                    desCanvas.drawRoundRect(0, 0, viewInfo.width, viewInfo.height,3,3, maskPaint);
//                }else {
//                    desCanvas.drawRect(0, 0, viewInfo.width, viewInfo.height, maskPaint);
//                }
                break;
            case Circle:
                desCanvas.setBitmap(desBitmap);
                desCanvas.drawCircle(desBitmap.getWidth() / 2, desBitmap.getWidth() / 2, viewInfo.width / 2, maskPaint);
                srcCanvas.drawBitmap(desBitmap, viewInfo.offsetX - blurWidth, viewInfo.offsetY - blurWidth, maskPaint);
                break;
            case Oval:
                desCanvas.setBitmap(desBitmap);
                desCanvas.drawOval(new RectF(blurWidth, blurWidth, viewInfo.width + blurWidth, viewInfo.height + blurWidth), maskPaint);
                srcCanvas.drawBitmap(desBitmap, viewInfo.offsetX - blurWidth, viewInfo.offsetY - blurWidth, maskPaint);
                break;
            default:
                break;
        }
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
        if (isShowAll || bitmapPos == viewInfos.size() - 1) {
            ((ViewGroup) this.getParent()).removeView(this);
            if (listener != null) {
                listener.dismiss();
            }
        } else {
            this.removeAllViews();
            bitmapPos++;
            layoutStyles.get(bitmapPos).showDecorationOnScreen(viewInfos.get(bitmapPos), this);
        }
    }
}
