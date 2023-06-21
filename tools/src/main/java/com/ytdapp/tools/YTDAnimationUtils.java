package com.ytdapp.tools;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;


public class YTDAnimationUtils {

    /**
     * 添加动画
     * @param box 需要做动画的view
     * @param type 动画类型: translationX, translationY, alpha, scaleX, scaleY, rotation
     * @param startValue 起始值
     * @param endValue 结束值
     * @param duration 持续时间
     * @return
     */
    public static ObjectAnimator setAnimation(View box, String type, float startValue, float endValue, int duration) {
        return setAnimation(box, type, startValue, endValue, duration, 0, "restart", 0, 0);
    }

    /**
     * 添加动画
     * @param box 需要做动画的view
     * @param type 动画类型: translationX, translationY, alpha, scaleX, scaleY, rotation
     * @param startValue 起始值
     * @param endValue 结束值
     * @param duration 持续时间
     * @param repeat 重复次数: -1为无限循环, 0为不循环, >0为循环次数
     * @param repeatType 重复类型: restart重新执行, reverse反转执行
     * @param interpolator 加速器
     * @param delay 延迟时间执行动画
     * @return
     */
    public static ObjectAnimator setAnimation(View box, String type, float startValue, float endValue,
        int duration, int repeat, String repeatType, int interpolator, int delay) {
        box.clearAnimation();
        Interpolator inter = new LinearInterpolator();

        switch (interpolator) {
            case 0:
                inter = new LinearInterpolator();
                break;
            case 1:
                //加速
                inter = new AccelerateInterpolator();
                break;
            case 2:
                //减速
                inter = new DecelerateInterpolator();
                break;
            case 3:
                //弹跳
                inter = new BounceInterpolator();
                break;
            case 4:
                //回荡
                inter = new AnticipateInterpolator();
                break;
            case 5:
                inter = new AnticipateOvershootInterpolator();
                break;
            case 6:
                inter = new OvershootInterpolator();
                break;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(box, type, startValue, endValue);
        animator.setDuration(duration);
        animator.setInterpolator(inter);
        animator.setStartDelay(delay);
        if (repeat == -1) {
            animator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        } else if (repeat != 0){
            animator.setRepeatCount(repeat);
        }
        if (repeatType != null && repeatType.equals("restart")) {//重新开始
            animator.setRepeatMode(ValueAnimator.RESTART);
        } else if (repeatType != null && repeatType.equals("reverse")) {//反转执行
            animator.setRepeatMode(ValueAnimator.REVERSE);
        }
        box.setCameraDistance(YTDUIDisplayHelper.dp2px(ContextUtil.getContext(), 16000));
        return animator;
    }

    public static void startAnimation(ObjectAnimator animator, AnimationEndListener listenerAdapter) {
        if (animator == null) {
            return;
        }
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                if (listenerAdapter != null) {
                    listenerAdapter.onAnimationEnd();
                }
            }
        });
        animator.start();
    }

    public interface AnimationEndListener {
        void onAnimationEnd();
    }
}
