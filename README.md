# GuideView 非常简单易用的引导工具，觉得不错的话给个Star。
![img](https://github.com/A-Heavy-Rain/GuideView/blob/master/ezgif.com-video-to-gif.gif)

## 基本使用示例
```
new GuideViewHelper(MainActivity.this)
                .addView(btn_light1, new RightTopStyle(deco_view1))
                .addView(tv_light2, new CenterRightStyle(deco_view2))
                .addView(tv_light3, new LeftBottomStyle(deco_view3, 10))
                .addView(iv_light4, new CenterTopStyle(deco_view4, 10))
                .type(LightType.Oval)
                .autoNext()
                .onDismiss(new GuideView.OnDismissListener() {
                    @Override
                    public void dismiss() {
                    }
                })
                .show();
```
## 方法描述
| GuideViewHelper方法名 | 备注
| --- | ---
|GuideViewHelper(Activity activity)|在Activity中使用
|GuideViewHelper(Fragment fragment)|在Fragment中使用
| addView(View view, LayoutStyle layoutStyle) | 需要高亮的View，装饰布局相对高亮View在布局中的位子（默认提供了8种，用户也可以继承LayoutStyle自定义）。
| addView(int viewId, LayoutStyle layoutStyle) | 需要高亮的View的id，装饰布局相对高亮View在布局中的位子（默认提供了8种，用户也可以继承LayoutStyle自定义）。
| padding(int padding) | 高亮区域内边距。
| type(LightType lightType) | 高亮形状，目前有矩形，圆形，椭圆。
| Blur() | 加模糊效果。
| Blur(int radius,int blurWidth) | 加模糊效果的半径，和模糊效果所显示的宽度。
| onDismiss(GuideView.OnDismissListener listener) | 监听高亮结束事件。
| autoNext() | 点击屏幕自动下一个高亮显示，会拦截子控件点击事件。
| nextLight() | 自己控制高亮的下一个显示。
| show() | 控件测量后依次显示。
| postShow() | 控件未测量依次显示。
| showAll() | 控件测量后全部显示。
| postShowAll() | 控件未测量全部显示。
## 相关类
### GuideView
蒙版View继承FrameLayout，在其中处理高亮显示。
### LayoutStyle
用于控制装饰高亮布局将对于高亮View所处的位子，默认提供了8种，用户也可以继承LayoutStyle自定义。
### ViewInfo
记录了高亮View的宽高，相对屏幕左上角的坐标。
