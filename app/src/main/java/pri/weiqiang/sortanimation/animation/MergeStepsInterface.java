package pri.weiqiang.sortanimation.animation;

/**
 * Created by weiqiang
 */

public interface MergeStepsInterface {

    /**
     * 从原数组中选择元素组成新数组，顺序为从小到大
     *
     * @param originalPosition 在原数组中的位置
     * @param tempPosition     在新生成的数组中的位置
     * @param isMerge          是否是处于将新生成的数组放置回原数组的那个步骤
     */
    void createTempView(int originalPosition, int tempPosition, boolean isMerge);

    /**
     * 将新生成的从小到大的数组重新合并到原数组中去
     *
     * @param originalPosition 在原数组中的位置
     * @param tempPosition     在新生成的数组中的位置
     * @param isMerge          是否是处于将新生成的数组放置回原数组的那个步骤
     */
    void mergeOriginalView(int originalPosition, int tempPosition, boolean isMerge);

    /**
     * Call when last item was sorted. Notifies user that sorting is finished.
     */
    void showFinish();

    /**
     * Cancel all current animations
     */
    void cancelAllVisualisations();
}
