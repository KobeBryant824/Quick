package com.cxh.library.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;


/**
 * 让指定 View 在一段时间内改变到指定高度
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class ResetHeightAnimation extends Animation {
    private View view;
    private int targetHeight;
    private int orginalHeight;
    private int totalValue;

    public ResetHeightAnimation(View view, int targetHeight) {
        super();
        this.view = view;
        this.targetHeight = targetHeight;

        orginalHeight = view.getHeight();
        totalValue = targetHeight - orginalHeight;

        setDuration(400);
//		setInterpolator(new BounceInterpolator());
        setInterpolator(new OvershootInterpolator());
//		setInterpolator(new LinearInterpolator());
    }

    /**
     * interpolatedTime:0-1  标识动画执行的进度或者百分比
     * view: 10 - 60  -  110
     * time: 0  - 0.5 -  1
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        int newHeight = (int) (orginalHeight + totalValue * interpolatedTime);
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }

}