package com.ukhanoff.bubblesort.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ukhanoff.bubblesort.R;
import com.ukhanoff.bubblesort.ui.customview.BubbleView;

import java.util.ArrayList;

/**
 * Handles all animation which should be done in scope of sorting algorithm visualization.
 */

public class AnimationsCoordinator implements AlgorithmStepsInterface {

    private String TAG = AnimationsCoordinator.class.getSimpleName();
    private ViewGroup bubblesContainer;
    private ArrayList<AlgorithmAnimationListener> listeners;
    private ValueAnimator blinkAnimation;

    public AnimationsCoordinator(ViewGroup bubblesContainer) {
        Log.e(TAG, "AnimationsCoordinator");
        this.bubblesContainer = bubblesContainer;
    }

    @Override
    public void showSwapStep(final int position, final boolean isBubbleOnFinalPosition) {
        Log.e(TAG, "showSwapStep position:"+position+",isBubbleOnFinalPosition:"+isBubbleOnFinalPosition);
        if (bubblesContainer != null && bubblesContainer.getChildCount() > 0 && bubblesContainer.getChildCount() > position + 1) {
            final BubbleView tempView = (BubbleView) bubblesContainer.getChildAt(position);
            final BubbleView nextTempView = (BubbleView) bubblesContainer.getChildAt(position + 1);

            //值为0到5，偶数为选中状态，蓝色，基数为未选中状态，粉色，所以，视觉表现为闪烁2次
            blinkAnimation = ValueAnimator.ofInt(0, 5);
            blinkAnimation.setDuration(1000);//动画时长为2s
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = ((Integer) animation.getAnimatedValue()).intValue();
//                    Log.e(TAG,"showSwapStep addUpdateListener value:"+value);
                    if (value % 2 == 0) {
                        tempView.setBubbleSelected(false);
                        nextTempView.setBubbleSelected(false);
                    } else {
                        tempView.setBubbleSelected(true);
                        nextTempView.setBubbleSelected(true);
                    }
                }
            });


            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {//
                    super.onAnimationEnd(animation);
                    tempView.setBubbleSelected(false);
                    tempView.setBubbleIsOnFinalPlace(isBubbleOnFinalPosition);
                    nextTempView.setBubbleSelected(false);
                    bubblesContainer.removeView(tempView);
                    bubblesContainer.addView(tempView, position + 1);

                    notifySwapStepAnimationEnd(position);
                }
            });

            blinkAnimation.start();
        }
    }

    @Override
    public void showNonSwapStep(final int position, final boolean isBubbleOnFinalPlace) {
        Log.e(TAG, "showNonSwapStep position:"+position+",isBubbleOnFinalPosition:"+isBubbleOnFinalPlace);
        if (bubblesContainer != null && bubblesContainer.getChildCount() > 0 && bubblesContainer.getChildCount() > position + 1) {
            final BubbleView tempView = (BubbleView) bubblesContainer.getChildAt(position);
            final BubbleView nextTempView = (BubbleView) bubblesContainer.getChildAt(position + 1);

            //值为0到7，偶数为选中状态，蓝色，基数为未选中状态，粉色，所以，视觉表现为闪烁3次
            blinkAnimation = ValueAnimator.ofInt(0, 7);
            blinkAnimation.setDuration(1200);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = ((Integer) animation.getAnimatedValue()).intValue();
//                    Log.e(TAG,"showNonSwapStep addUpdateListener value:"+value);
                    if (value % 2 == 0) {
                        tempView.setBubbleSelected(false);
                        nextTempView.setBubbleSelected(false);
                    } else {
                        tempView.setBubbleSelected(true);
                        nextTempView.setBubbleSelected(true);
                    }
                }
            });

            blinkAnimation.start();
            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    tempView.setBubbleSelected(false);
                    nextTempView.setBubbleSelected(false);
                    nextTempView.setBubbleIsOnFinalPlace(isBubbleOnFinalPlace);

                    notifySwapStepAnimationEnd(position);
                }
            });
        }
    }

    @Override
    public void showFinish() {
        Log.e(TAG, "showFinish");
        if (bubblesContainer != null && bubblesContainer.getChildCount() > 0) {
            ((BubbleView) bubblesContainer.getChildAt(0)).setBubbleIsOnFinalPlace(true);
        }
        Toast.makeText(bubblesContainer.getContext(), R.string.sort_finish, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelAllVisualisations() {
        Log.e(TAG, "cancelAllVisualisations");
        if (blinkAnimation != null) {
            blinkAnimation.removeAllListeners();
            blinkAnimation.cancel();
            bubblesContainer.clearAnimation();
        }
    }

    private void notifySwapStepAnimationEnd(int position) {
        Log.e(TAG, "notifySwapStepAnimationEnd:"+position);
        if (listeners != null && !listeners.isEmpty()) {
            int numListeners = listeners.size();
            //这里直接只用listeners.get(0).onSwapStepAnimationEnd(position);即可，因为addListener(AlgorithmAnimationListener listener)仅使用了一次
            Log.e(TAG, "numListeners:"+numListeners);
            for (int i = 0; i < numListeners; ++i) {
                Log.e(TAG, "onSwapStepAnimationEnd i:"+i+",position:"+position);
                //将会调用SortFragment: onSwapStepAnimationEnd中实现具体的方法
                listeners.get(i).onSwapStepAnimationEnd(position);
            }
        }
    }

    public void addListener(AlgorithmAnimationListener listener) {
        Log.e(TAG, "addListener");
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void removeListener(Animator.AnimatorListener listener) {
        Log.e(TAG, "removeListener");
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
        if (listeners.size() == 0) {
            listeners = null;
        }
    }
}
