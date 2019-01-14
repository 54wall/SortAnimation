package pri.weiqiang.sortanimation.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import pri.weiqiang.sortanimation.R;
import pri.weiqiang.sortanimation.ui.customview.RectView;
import pri.weiqiang.sortanimation.ui.fragment.SortFragment;
import pri.weiqiang.sortanimation.util.Util;


/**
 * 归并算法动画控制类
 */
public class MergeAnimationsCoordinator implements MergeStepsInterface {

    private String TAG = MergeAnimationsCoordinator.class.getSimpleName();
    private ViewGroup originalContainer;
    private ViewGroup tempContainer;
    private ArrayList<MergeAnimationListener> listeners;
    private ValueAnimator blinkAnimation;
    private LinearLayout.LayoutParams lp;
    private Context context;

    public MergeAnimationsCoordinator(Context context, ViewGroup originalContainer, ViewGroup tempContainer) {
        Log.e(TAG, "MergeAnimationsCoordinator");
        this.context = context;
        this.originalContainer = originalContainer;
        this.tempContainer = tempContainer;
        //放在这里报错 java.lang.NullPointerException: Attempt to read from field 'int android.view.ViewGroup$LayoutParams.width' on a null object reference
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        int marginInPx = Util.dpToPx(context, SortFragment.RECT_MARGIN);
//        lp.setMargins(0, 0, marginInPx, 0);
    }

    /**
     * 从原数组拿取元素，按大小添加下方的新矩形数列中
     *
     * @param originalPosition 在原数组中的位置
     * @param tempPosition     在新生成的数组中的位置
     * @param isMerge          是否是处于将新生成的数组放置回原数组的那个步骤
     */
    @Override
    public void createTempView(final int originalPosition, final int tempPosition, final boolean isMerge) {

        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginInPx = Util.dpToPx(context, SortFragment.RECT_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);

        if (originalContainer != null && originalContainer.getChildCount() > 0 && originalContainer.getChildCount() > tempPosition) {
            final RectView originalView = (RectView) originalContainer.getChildAt(originalPosition);
            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 5);
            blinkAnimation.setDuration(1000);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    int value = (Integer) animation.getAnimatedValue();
                    if (value % 2 == 0) {
                        originalView.setSelected(false);
                    } else {
                        originalView.setSelected(true);
                    }
                }
            });


            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.e(TAG, "生成临时矩形!");
                    super.onAnimationEnd(animation);
                    originalView.setSelected(false);
                    originalView.setIsOnFinalPlace(isMerge);
                    originalContainer.removeView(originalView);
                    int tempNumber = originalView.getNumber();
                    originalView.setMinimumHeight(1);
                    originalView.setImageBitmap(createSpaceBitmap(SortFragment.mRectWidth));
                    originalView.setNumber(1);
                    originalContainer.addView(originalView, originalPosition, lp);

                    RectView tempRectView = new RectView(context);
                    tempRectView.setImageBitmap(createCalculatedBitmap(SortFragment.mRectWidth, tempNumber));
                    tempRectView.setNumber(tempNumber);
                    tempContainer.addView(tempRectView, tempPosition, lp);
                    notifySwapStepAnimationEnd(originalPosition);
                }
            });

            blinkAnimation.start();
        }
    }

    /**
     * 将下列排序好的矩形按顺序填回到原矩形序列
     *
     * @param originalPosition 在原数组中的位置
     * @param tempPosition     在新生成的数组中的位置
     * @param isMerge          是否是处于将新生成的数组放置回原数组的那个步骤
     */
    @Override
    public void mergeOriginalView(final int originalPosition, final int tempPosition, final boolean isMerge) {

        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginInPx = Util.dpToPx(context, SortFragment.RECT_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);
        if (originalContainer != null && originalContainer.getChildCount() > 0 && originalContainer.getChildCount() > tempPosition) {
            final RectView originalView = (RectView) originalContainer.getChildAt(originalPosition);
            final RectView tempRectView = (RectView) tempContainer.getChildAt(tempPosition);
            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 6);
            blinkAnimation.setDuration(1200);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
                    if (value % 2 == 0) {
                        originalView.setSelected(false);
                        tempRectView.setSelected(false);
                    } else {
                        originalView.setSelected(true);
                        tempRectView.setSelected(true);
                    }
                }
            });

            blinkAnimation.start();
            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    originalView.setSelected(false);
                    tempRectView.setSelected(false);
                    tempRectView.setIsOnFinalPlace(isMerge);
                    tempContainer.removeView(tempRectView);
                    int tempNumber = tempRectView.getNumber();

                    tempRectView.setMinimumHeight(1);
                    tempRectView.setImageBitmap(createSpaceBitmap(SortFragment.mRectWidth));
                    tempRectView.setNumber(1);
// 不能设置矩形不可见，还是会报The specified child already has a parent. You must call removeView() on the child's parent first.
//                    tempRectView.setVisibility(View.INVISIBLE);
                    tempContainer.addView(tempRectView, tempPosition, lp);


                    originalContainer.removeView(originalView);
                    RectView originalView = new RectView(context);
                    originalView.setImageBitmap(createCalculatedBitmap(SortFragment.mRectWidth, tempNumber));
                    originalView.setNumber(tempNumber);
                    originalContainer.addView(originalView, originalPosition, lp);
                    notifySwapStepAnimationEnd(originalPosition);
                }
            });
        }
    }

    @Override
    public void showFinish() {
        Log.e(TAG, "showFinish");
        if (originalContainer != null && originalContainer.getChildCount() > 0) {
            for (int i = 0; i < originalContainer.getChildCount(); i++) {
                ((RectView) originalContainer.getChildAt(i)).setIsOnFinalPlace(true);//排序完成后，全部设置为最终颜色
            }
        }
        Toast.makeText(originalContainer.getContext(), R.string.sort_finish, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelAllVisualisations() {
        Log.e(TAG, "cancelAllVisualisations");
        if (blinkAnimation != null) {
            blinkAnimation.removeAllListeners();
            blinkAnimation.cancel();
            originalContainer.clearAnimation();
        }
    }

    private void notifySwapStepAnimationEnd(int position) {
        Log.e(TAG, "notifySwapStepAnimationEnd");
        if (listeners != null && !listeners.isEmpty()) {
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; ++i) {
                listeners.get(i).onSwapStepAnimationEnd(position);
            }
        }
    }

    public void addListener(MergeAnimationListener listener) {
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

    /**
     * 绘制指定高度的RectView
     */
    private Bitmap createCalculatedBitmap(int mRectWidth, Integer currentIntValue) {

        int mRectHeight = SortFragment.lineHeightArray.get(currentIntValue);
        if (mRectHeight < SortFragment.minHeight) {
            mRectHeight = SortFragment.minHeight;
        }
        return Bitmap.createBitmap(mRectWidth, mRectHeight, Bitmap.Config.ALPHA_8);
    }


    /**
     * 绘制高度为1的空白的RectView 用来占位
     * 为什么不从完全空的容器增加，那样每次位置都会乱
     */
    private Bitmap createSpaceBitmap(int mRectWidth) {
        return Bitmap.createBitmap(mRectWidth, 1, Bitmap.Config.ALPHA_8);
    }
}
