package com.chebao.slidemenu.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 让指定View在一定时间内scrollTo到指定位置
 */
class ScrollAnimation extends Animation {

    private View view;
    private int targetScrollX;
    private int startScrollX;
    private int totalValue;

    /**
     * 构造方法
     * @param view
     * @param targetScrollX
     */
    public ScrollAnimation(View view, int targetScrollX) {
        this.view = view;
        this.targetScrollX = targetScrollX;

        startScrollX = view.getScrollX(); //view传入时获取当前值
        totalValue = this.targetScrollX - startScrollX;

        int time = Math.abs(totalValue);

        setDuration(time); //动画持续时间

//            setDuration(400); //动画持续时间
    }

    /**
     * 在指定的时间内一直执行该方法，直到动画结束
     * @param interpolatedTime 动画执行进度百分比 0-1
     * @param t
     *
     *        起始值    interpolatedTime    总差值
     * time :  0           -  0.5           -   1
     * value:  10          -  60            -    110
     * 当前值 = 起始值 + 总的差值 * interpolatedTime
     *
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        int currentScrollX = (int) (startScrollX + totalValue * interpolatedTime);
        view.scrollTo(currentScrollX,0);


    }
}
