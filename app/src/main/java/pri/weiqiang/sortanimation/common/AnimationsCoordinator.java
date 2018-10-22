package pri.weiqiang.sortanimation.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import pri.weiqiang.sortanimation.R;
import pri.weiqiang.sortanimation.ui.customview.BubbleView;

/**
 * Handles all animation which should be done in scope of sorting algorithm visualization.
 */

public class AnimationsCoordinator implements AlgorithmStepsInterface {

    private String TAG = AnimationsCoordinator.class.getSimpleName();
    private ViewGroup bubblesContainer;
    private ArrayList<AlgorithmAnimationListener> listeners;
    private ValueAnimator blinkAnimation;

    public AnimationsCoordinator(ViewGroup bubblesContainer) {
//        Log.e(TAG, "AnimationsCoordinator");
        this.bubblesContainer = bubblesContainer;
    }

    @Override
    public void showSwapStep(final int curPosition, final int nextPosition, final boolean isBubbleOnFinalPosition) {
//        Log.e(TAG, "showSwapStep");
        if (bubblesContainer != null && bubblesContainer.getChildCount() > 0 && bubblesContainer.getChildCount() > nextPosition) {
            Log.e(TAG, "curPosition:" + curPosition + ",nextPosition:" + nextPosition);
            final BubbleView curBubbleView = (BubbleView) bubblesContainer.getChildAt(curPosition);
            final BubbleView nextBubbleView = (BubbleView) bubblesContainer.getChildAt(nextPosition);

            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 5);
            blinkAnimation.setDuration(3000);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    int value = (Integer) animation.getAnimatedValue();
//                    Log.e(TAG,"!!!onAnimationUpdate");
//                    Log.e(TAG,"!!!showSwapStep onAnimationUpdate value:"+value);
                    if (value % 2 == 0) {
                        curBubbleView.setBubbleSelected(false);
                        nextBubbleView.setBubbleSelected(false);
                    } else {
                        curBubbleView.setBubbleSelected(true);
                        nextBubbleView.setBubbleSelected(true);
                    }
                }
            });


            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
//                    Log.e(TAG,"!!!onAnimationEnd");
                    super.onAnimationEnd(animation);
                    curBubbleView.setBubbleSelected(false);
                    curBubbleView.setBubbleIsOnFinalPlace(isBubbleOnFinalPosition);
                    nextBubbleView.setBubbleSelected(false);
                    bubblesContainer.removeView(curBubbleView);
                    bubblesContainer.addView(curBubbleView, nextPosition);
                    bubblesContainer.removeView(nextBubbleView);
                    bubblesContainer.addView(nextBubbleView, curPosition);
                    notifySwapStepAnimationEnd(curPosition);
                }
            });

            blinkAnimation.start();
        }
    }

    @Override
    public void showNonSwapStep(final int curPosition, final int nextPosition, final boolean isBubbleOnFinalPlace) {
//        Log.e(TAG, "showNonSwapStep");
        if (bubblesContainer != null && bubblesContainer.getChildCount() > 0 && bubblesContainer.getChildCount() > nextPosition) {
            final BubbleView curBubbleView = (BubbleView) bubblesContainer.getChildAt(curPosition);
            final BubbleView nextBubbleView = (BubbleView) bubblesContainer.getChildAt(nextPosition);

            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 7);
            blinkAnimation.setDuration(1000);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
//                    Log.e(TAG,"!!!howNonSwapStep onAnimationUpdate value:"+value);
                    if (value % 2 == 0) {
                        curBubbleView.setBubbleSelected(false);
                        nextBubbleView.setBubbleSelected(false);
                    } else {
                        curBubbleView.setBubbleSelected(true);
                        nextBubbleView.setBubbleSelected(true);
                    }
                }
            });

            blinkAnimation.start();
            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    curBubbleView.setBubbleSelected(false);
                    nextBubbleView.setBubbleSelected(false);
                    nextBubbleView.setBubbleIsOnFinalPlace(isBubbleOnFinalPlace);
                    notifySwapStepAnimationEnd(curPosition);
                }
            });
        }
    }

    @Override
    public void showFinish() {
//        Log.e(TAG, "showFinish");
        if (bubblesContainer != null && bubblesContainer.getChildCount() > 0) {
            ((BubbleView) bubblesContainer.getChildAt(0)).setBubbleIsOnFinalPlace(true);
        }
        Toast.makeText(bubblesContainer.getContext(), R.string.sort_finish, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelAllVisualisations() {
//        Log.e(TAG, "cancelAllVisualisations");
        if (blinkAnimation != null) {
            blinkAnimation.removeAllListeners();
            blinkAnimation.cancel();
            bubblesContainer.clearAnimation();
        }
    }

    private void notifySwapStepAnimationEnd(int position) {
//        Log.e(TAG, "notifySwapStepAnimationEnd");
        if (listeners != null && !listeners.isEmpty()) {
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; ++i) {
                listeners.get(i).onSwapStepAnimationEnd(position);
            }
        }
    }

    public void addListener(AlgorithmAnimationListener listener) {
//        Log.e(TAG, "addListener");
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void removeListener(Animator.AnimatorListener listener) {
//        Log.e(TAG, "removeListener");
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
        if (listeners.size() == 0) {
            listeners = null;
        }
    }
}