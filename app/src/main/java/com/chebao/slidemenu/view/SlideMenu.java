package com.chebao.slidemenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 *
 */
public class SlideMenu extends FrameLayout {

    private View menuView, mainView;
    private int menuWidth = 0;
    private int downX;

    public SlideMenu(Context context) {
        super(context);
        init();
    }


    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    /**
     * 1
     * 当1级的子View全部加载完调用
     * 可以用来初始化子View的引用
     * 注意：这里无法获取子View的宽高
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);  //<include layout="@layout/layout_menu" />
        mainView = getChildAt(1); //<include layout="@layout/layout_main" />

        menuWidth = menuView.getLayoutParams().width;

    }

    /**
     * 2
     *
     * @param changed
     * @param l       : 当前子view在左边在父view的坐标系中的X坐标
     * @param t       : 当前子view在左边在父view的坐标系中的Y坐标
     * @param r       : 屏幕的宽
     * @param b       ： 屏幕的高
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("menuView", "l:" + l + ", t: " + t + ", r :" + r + ", b :" + b);
        menuView.layout(-menuWidth, 0, 0, menuView.getMeasuredHeight());
        mainView.layout(0, 0, r, b);
    }

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();

                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int deltaX = (int) (moveX - downX);

                int newScrollX = getScrollX() - deltaX;

                if (newScrollX < -menuWidth) newScrollX = -menuWidth;
                if (newScrollX > 0) newScrollX = 0;
                scrollTo(newScrollX, 0);
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
