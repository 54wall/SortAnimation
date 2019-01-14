package com.ukhanoff.bubblesort.common;

/**
 * Created by ukhanoff on 2/6/17.
 */

public interface AlgorithmStepsInterface {

    /**
     * Visualizes step, when elements should change their places with each other
     *  交换位置
     * @param position             position of the firs element, which should be changed
     * @param isBubbleOnFinalPlace set true, when element after swapping is on the right place and his position is final
     */
    void showSwapStep(int position, boolean isBubbleOnFinalPlace);

    /**
     * Visualizes step, when elements should stay on the same places;
     * 不交换位置
     * @param position             position of the firs element
     * @param isBubbleOnFinalPlace set true, when element on position+1 is on the right place and his position is final
     */
    void showNonSwapStep(int position, boolean isBubbleOnFinalPlace);

    /**
     * Call when last item was sorted. Notifies user that sorting is finished.
     * 结束全部动画，小球将处于最后排序完成后的颜色
     */
    void showFinish();

    /**
     * Cancel all current animations
     */
    void cancelAllVisualisations();
}
