package com.chebao.slidemenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 侧滑菜单
 */
public class SlideMenu extends FrameLayout {

    private View menuView, mainView;
    private int menuWidth = 0;
    private int downX;

    private Scroller scroller; // 2

    public SlideMenu(Context context) {
        super(context);
        init();
    }


    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        scroller  = new Scroller(getContext()); // 2

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
     * onInterceptTouchEvent 触摸拦截事件
     * 让左侧菜单可以左右滑动
     * 鼠标悬停拦截事件 ，true 拦截，false 不拦截
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX(); //按下时的X坐标

                break;
            case MotionEvent.ACTION_MOVE:
                //滑动的像素
                int deltaX = (int) (event.getX() - downX);
                if (Math.abs(deltaX) > 8 ){ //滑动大于8像素

                    return true; //拦截，

                }
                break;
        }

//        return super.onInterceptHoverEvent(event);  //默认false,不拦截
        return true;  //默认false,不拦截
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

                //方法1 ： 使用自定义动画

//                ScrollAnimation scrollAnimation;
//                if (getScrollX() > -menuWidth / 2){
//                    //关闭菜单
////                    scrollTo(0, 0); //瞬间执行
//                    scrollAnimation = new ScrollAnimation(this,0);
//
//                }else{
//                    //打开菜单
////                    scrollTo(-menuWidth , 0);
//                    scrollAnimation = new ScrollAnimation(this,-menuWidth);
//                }
//                startAnimation(scrollAnimation); //执行动画


                //方法2： 使用Scroller
                if(getScrollX() > - menuWidth/2){
                    //关闭菜单
                    closeMenu();

                }else{
                    //打开菜单
                    openMenu();

                }

                break;
        }
        return true;
    }

    private void closeMenu(){
        //关闭菜单
        /**
         * startX,
         * startY,
         * dx,
         * dy,
         * 400 效果持续时间
         */

        scroller.startScroll(getScrollX(),0,0-getScrollX(),0,400);
        invalidate();//刷新

    }
    private void openMenu(){
        //打开菜单
        scroller.startScroll(getScrollX(),0,-menuWidth-getScrollX(),0,400);
        invalidate();//刷新
    }

    /**
     * 2
     * Scroller不会主动调用这个方法
     * 但是invalidate会主动调用这个方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();

        if (scroller.computeScrollOffset()){ //返回true,表示动画没有结束
            // arg X ,Y
            scrollTo(scroller.getCurrX(),0);
            invalidate(); //递归调用这个方法

        }
    }

    /**
     * 切换菜单的开和关
     */
    public void switchMenu() {
        if (getScrollX() ==0){
            //需要打开
            openMenu();

        }else {
            //需要关闭
            closeMenu();

        }


    }
}
