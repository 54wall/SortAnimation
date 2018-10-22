package pri.weiqiang.sortanimation.common;

/**
 * Created by ukhanoff on 2/6/17.
 */

public interface AlgorithmStepsInterface {

    /**
     * Visualizes step, when elements should change their places with each other
     *
     * @param curPosition          position of the firs element, which should be changed
     * @param isBubbleOnFinalPlace set true, when element after swapping is on the right place and his position is final
     */
    void showSwapStep(int curPosition, int nextPosition, boolean isBubbleOnFinalPlace);

    /**
     * Visualizes step, when elements should stay on the same places;
     *
     * @param curPosition          position of the firs element
     * @param isBubbleOnFinalPlace set true, when element on position+1 is on the right place and his position is final
     */
    void showNonSwapStep(int curPosition, int nextPosition, boolean isBubbleOnFinalPlace);

    /**
     * Call when last item was sorted. Notifies user that sorting is finished.
     */
    void showFinish();

    /**
     * Cancel all current animations
     */
    void cancelAllVisualisations();
}
